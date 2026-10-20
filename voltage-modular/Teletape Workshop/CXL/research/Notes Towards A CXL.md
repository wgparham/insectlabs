Notes Towards A CXL


//AllPassFilter scratch code  

public class AllPassFilter {
    private double[] buffer;
    private int bufferSize;
    private int writeIndex = 0;
    private double coefficient;

    public AllPassFilter(int size, double coeff) {
        this.bufferSize = size;
        this.buffer = new double[bufferSize];
        this.coefficient = coeff;
    }

    public double process(double input, double modOffset) {
        // modOffset can be driven by a small LFO to create the Lexicon chorus effect
        double readIndex = (writeIndex - modOffset + bufferSize) % bufferSize;
        
        // Simple linear interpolation for fractional delay
        int base = (int)readIndex;
        double frac = readIndex - base;
        int next = (base + 1) % bufferSize;
        double delaySample = buffer[base] + frac * (buffer[next] - buffer[base]);

        double output = -coefficient * input + delaySample;
        buffer[writeIndex] = input + coefficient * output;

        writeIndex = (writeIndex + 1) % bufferSize;
        return output;
    }
}

//End Scratch Code



48 kHz Optimized Dattorro Delay Matrix:

1. Input Diffusion Stage (In Series)

These four cascaded all-pass filters smear the initial transient.
• APF 1: 229 samples (~4.77 ms)
• APF 2: 173 samples (~3.60 ms)
• APF 3: 613 samples (~12.77 ms)
• APF 4: 449 samples (~9.35 ms)

2. The Figure-8 Loop Tanks

These form the core recursive tank structure that creates the long plate decay tail.
• Left Tank Branch:
    • Loop APF (Left): 1,481 samples (~30.85 ms)
    • Main Delay Line (Left): 6,869 samples (~143.10 ms)
• Right Tank Branch:
    • Loop APF (Right): 2,927 samples (~60.98 ms)
    • Main Delay Line (Right): 5,939 samples (~123.73 ms)


// 2. Increase the maximum sample excursion slightly for 48kHz
// An excursion baseline of 12 samples +/- 6 samples delivers the signature Lexicon chorused drift
double modOffsetLeft = 12.0 + (lfoOut * 6.0); 


// Define globally
AllPassFilter diff1 = new AllPassFilter(142, 0.75);
AllPassFilter diff2 = new AllPassFilter(107, 0.75);
AllPassFilter diff3 = new AllPassFilter(379, 0.625);
AllPassFilter diff4 = new AllPassFilter(277, 0.625);

AllPassFilter loopApfLeft = new AllPassFilter(908, 0.7);
AllPassFilter loopApfRight = new AllPassFilter(1816, 0.7);

// Simple delay lines for the tank
double[] leftDelayLine = new double[4253];
double[] rightDelayLine = new double[3720];
int leftIdx = 0, rightIdx = 0;

// Reverb state variables
double lastLeftSample = 0.0;
double lastRightSample = 0.0;
double lfoPhase = 0.0;

@Override
public void processSample() {
    // 1. Read input voltage from jacks
    double inputL = inputLeft.GetValue();
    double inputR = inputRight.GetValue();
    double monoInput = (inputL + inputR) * 0.5;

    // 2. Compute LFO for Lexicon-style chorus/modulation (prevents ringing)
    // Run LFO very slowly (~0.5 Hz to 1.0 Hz)
    lfoPhase += (0.7 / 48000.0); 
    if (lfoPhase > 1.0) lfoPhase -= 1.0;
    double lfoOut = Math.sin(lfoPhase * 2.0 * Math.PI);
    // An excursion baseline of 12 samples +/- 6 samples delivers the signature Lexicon chorused drift
    double modOffsetLeft = 12.0 + (lfoOut * 6.0);

    // 3. The Diffusion Stage (Pre-conditioning the transient)
    double diffOut = diff1.process(monoInput, 0);
    diffOut = diff2.process(diffOut, 0);
    diffOut = diff3.process(diffOut, 0);
    diffOut = diff4.process(diffOut, 0);

    // 4. Read User Controls (Knobs)
    double knobDecay = decayTimeKnob.GetValue(); // Map this between 0.0 and 0.98
    double knobDamp = highDampKnob.GetValue(); // Simple low-pass filter coefficient (0.0 to 0.9)

    // 5. The Figure-8 Tank Loop
    // --- Left Tank Branch ---
    double leftTankInput = diffOut + (lastRightSample * knobDecay);
    double leftApfOut = loopApfLeft.process(leftTankInput, modOffsetLeft);
    
    // Read from Left long delay line
    double leftDelayOut = leftDelayLine[leftIdx];
    // Apply Damping (simple one-pole low pass filter)
    double leftDamped = leftDelayOut * (1.0 - knobDamp) + (lastLeftSample * knobDamp);
    lastLeftSample = leftDamped;
    
    // Write back into Left delay line
    leftDelayLine[leftIdx] = leftApfOut;
    leftIdx = (leftIdx + 1) % leftDelayLine.length;

    // --- Right Tank Branch ---
    double rightTankInput = diffOut + (leftDamped * knobDecay);
    double rightApfOut = loopApfRight.process(rightTankInput, 0); // Keep one side stable
    
    // Read from Right long delay line
    double rightDelayOut = rightDelayLine[rightIdx];
    double rightDamped = rightDelayOut * (1.0 - knobDamp) + (lastRightSample * knobDamp);
    lastRightSample = rightDamped;
    
    // Write back into Right delay line
    rightDelayLine[rightIdx] = rightApfOut;
    rightIdx = (rightIdx + 1) % rightDelayLine.length;

    // 6. Output Matrix (Mix taps from different points of the delay lines for true stereo)
    double wetL = leftApfOut * 0.6 + leftDamped * 0.6;
    double wetR = rightApfOut * 0.6 + rightDamped * 0.6;

    double mix = mixKnob.GetValue(); // 0.0 (Dry) to 1.0 (Wet)
    
    outputLeft.SetValue((inputL * (1.0 - mix)) + (wetL * mix));
    outputRight.SetValue((inputR * (1.0 - mix)) + (wetR * mix));
}



// Biquad Scratch Code
public class BiquadFilter {
    // Coefficients
    private double a0, a1, a2, b1, b2;
    // Delay registers (state variables)
    private double x1 = 0, x2 = 0, y1 = 0, y2 = 0;

    public BiquadFilter() {}

    // Calculate coefficients for a Low-Pass Filter (LPF)
    public void setLowPass(double frequency, double sampleRate, double Q) {
        double omega = 2.0 * Math.PI * frequency / sampleRate;
        double sn = Math.sin(omega);
        double cs = Math.cos(omega);
        double alpha = sn / (2.0 * Q);

        double b0 = (1.0 - cs) / 2.0;
        a0 = 1.0 + alpha;
        a1 = -2.0 * cs;
        a2 = 1.0 - alpha;
        
        // Normalize by a0
        this.b1 = (-2.0 * cs) / a0;
        this.b2 = (1.0 - alpha) / a0;
        this.a0 = b0 / a0;
        this.a1 = ((1.0 - cs)) / a0; // b1 coefficient in standard textbook formulas
        this.a2 = b0 / a0;           // b2 coefficient in standard textbook formulas
    }

    // Calculate coefficients for a High-Pass Filter (HPF)
    public void setHighPass(double frequency, double sampleRate, double Q) {
        double omega = 2.0 * Math.PI * frequency / sampleRate;
        double sn = Math.sin(omega);
        double cs = Math.cos(omega);
        double alpha = sn / (2.0 * Q);

        double b0 = (1.0 + cs) / 2.0;
        a0 = 1.0 + alpha;
        
        this.b1 = (-2.0 * cs) / a0;
        this.b2 = (1.0 - alpha) / a0;
        this.a0 = b0 / a0;
        this.a1 = (-(1.0 + cs)) / a0;
        this.a2 = b0 / a0;
    }

    // Process a single sample through Direct Form I
    public double process(double inSample) {
        double outSample = (a0 * inSample) + (a1 * x1) + (a2 * x2) - (b1 * y1) - (b2 * y2);
        
        // Shift delay registers
        x2 = x1;
        x1 = inSample;
        y2 = y1;
        y1 = outSample;

        return outSample;
    }
}


// --- Global Declarations ---
BiquadFilter preLowPass = new BiquadFilter();
BiquadFilter preHighPass = new BiquadFilter();

AllPassFilter diff1, diff2, diff3, diff4;
AllPassFilter loopApfLeft, loopApfRight;
double[] leftDelayLine;
double[] rightDelayLine;

// Helper to find the next nearest prime number to prevent ringing structures
private int nextPrime(int n) {
    if (n <= 1) return 2;
    int prime = n;
    boolean found = false;
    while (!found) {
        prime++;
        if (isPrime(prime)) found = true;
    }
    return prime;
}

private boolean isPrime(int n) {
    if (n <= 1) return false;
    if (n <= 3) return true;
    if (n % 2 == 0 || n % 3 == 0) return false;
    for (int i = 5; i * i <= n; i += 6) {
        if (n % i == 0 || n % (i + 2) == 0) return false;
    }
    return true;
}

// --- Trigger this in Initialize() or whenever Sample Rate Changes ---
public void setupDSP() {
    double sr = GetSampleRate(); // Fetches current host rate (e.g., 48000.0)
    
    // 1. Initialize Biquad Filters (Vintage 12-bit emulated bandwidth)
    // Q factor of 0.7071 provides a smooth Butterworth response curve
    preLowPass.setLowPass(10000.0, sr, 0.7071);
    preHighPass.setHighPass(120.0, sr, 0.7071);

    // 2. Dynamically Scale Target Millisecond Delays to Prime Buffer Sizes
    double scale = sr / 29761.0; // Scale relative to Dattorro's reference rate

    diff1 = new AllPassFilter(nextPrime((int)(142 * scale)), 0.75);
    diff2 = new AllPassFilter(nextPrime((int)(107 * scale)), 0.75);
    diff3 = new AllPassFilter(nextPrime((int)(379 * scale)), 0.625);
    diff4 = new AllPassFilter(nextPrime((int)(277 * scale)), 0.625);

    loopApfLeft = new AllPassFilter(nextPrime((int)(908 * scale)), 0.7);
    loopApfRight = new AllPassFilter(nextPrime((int)(1816 * scale)), 0.7);

    leftDelayLine = new double[nextPrime((int)(4253 * scale))];
    rightDelayLine = new double[nextPrime((int)(3720 * scale))];
}