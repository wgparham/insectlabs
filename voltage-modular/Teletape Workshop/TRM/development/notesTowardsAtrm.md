vThresh 

• Germanium (G): vThresh = 0.25;
	• DSP Implementation: Germanium has a very soft, exponential turn-on curve. Instead of a hard threshold, use an exponential or soft hyperbolic tangent (Math.tanh()) knee. This yields smooth, warm odd-order harmonics and lower overall volume loss.
• Schottky (S): vThresh = 0.35;
	• DSP Implementation: Schottky transitions quicker than Germanium but softer than Silicon. Use a slightly tighter tanh or polynomial curve. This gives a clean, punchy middle-ground modulation profile.
• Silicon (Si): vThresh = 0.70;
	• DSP Implementation: Silicon diodes exhibit a very sharp turn-on threshold. Implement this with a more abrupt piecewise function. Signals below vThresh will experience strong crossover distortion (clipping out quiet passages or low-level carrier/modulator interactions), resulting in a bright, aggressive, and highly abrasive lo-fi texture.

Modeling the "Leaky Transformer" Mode (T)

Real audio transformers introduce hysteresis, saturation, and frequency-dependent phase shifts. A "leaky" transformer setting implies significant core saturation (low-frequency distortion) and inter-winding capacitance leaks.
To achieve this in your DSP block when the slider is set to T:
• Low-End Saturation: Run the raw input signals through an asymmetric waveshaper to simulate magnetic core saturation before multiplication.
• High-Frequency Leakage: Implement a subtle high-pass filter or a static crosstalk leakage factor. Add a tiny fraction of the unmodulated carrier signal directly back into the output mix:

Output = RingModOut + (Carrier x 0.07)

This accurately recreates the classic hardware anomaly where the carrier signal "bleeds" through the output even when no modulation signal is present.


// Inside your main process block
double carrier = input1A.GetValue();
double modulator = input1B.GetValue();
double output = 0.0;

int mode1 = (int) switchDiode1.GetValue(); // 0 = T, 1 = G, 2 = S, 3 = Si

switch(mode1) {
    case 0: // Leaky Transformer
        output = computeLeakyTransformer(carrier, modulator);
        break;
    case 1: // Germanium (Soft Exponential)
        output = computeSoftDiode(carrier, modulator, 0.25, 1.5);
        break;
    case 2: // Schottky (Medium)
        output = computeSoftDiode(carrier, modulator, 0.35, 3.0);
        break;
    case 3: // Silicon (Sharp Hard-Knee)
        output = computeSharpDiode(carrier, modulator, 0.70);
        break;
}

output1.SetValue(output);


/**
 * Simulates a classic four-diode ring modulator core.
 * Uses a hyperbolic tangent approximation with scaled forward voltages.
 * 
 * @param c       Carrier signal (-5.0 to 5.0 Volts)
 * @param m       Modulator signal (-5.0 to 5.0 Volts)
 * @param vThresh Forward voltage drop (threshold) of the diode
 * @param k       Exponential steepness factor (softer vs. sharper transition)
 */
private double computeDiodeCore(double c, double m, double vThresh, double k) {
    // Basic ring mod multiplication structure: (c + m) and (c - m) pathways
    double posSum = c + m;
    double negSum = c - m;

    // Apply the diode forward voltage barrier and exponential conduction knee
    double d1 = Math.tanh(Math.max(0.0, posSum - vThresh) * k);
    double d2 = Math.tanh(Math.max(0.0, -posSum - vThresh) * k);
    double d3 = Math.tanh(Math.max(0.0, negSum - vThresh) * k);
    double d4 = Math.tanh(Math.max(0.0, -negSum - vThresh) * k);

    // Combine standard diode bridge balance math
    double rawOutput = (d1 + d2) - (d3 + d4);
    
    // Scale output to keep overall volume levels uniform across thresholds
    return rawOutput * (5.0 / (1.0 + vThresh));
}

/**
 * Models a Leaky Transformer ring mod core.
 * Simulates magnetic core saturation, high-frequency carrier bleed, and phase shifts.
 */
private double computeLeakyTransformer(double c, double m) {
    // 1. Core Saturation: Apply asymmetric saturation to signals using a soft sine shaper
    double satC = Math.sin(c * 0.2) * 4.5;
    double satM = Math.sin(m * 0.15) * 4.5;

    // 2. Ideal Ring Modulation on the saturated signals
    double modulated = satC * satM * 0.2; // 0.2 scale brings product back into +/-5V range

    // 3. Transformer Leakage: Blends 7% of raw carrier back into the output mix
    // This replicates the hardware imbalance where carrier bleeds through 
    // even if the modulator input is silent.
    double bleedAmount = 0.07;
    double finalOutput = modulated + (c * bleedAmount);

    // Hard clip protection at Voltage Modular's maximum rails (+/- 12V)
    return Math.max(-12.0, Math.min(12.0, finalOutput));
}



@Override
public void ProcessSample() {
    // --- CHANNEL 1 PROCESSING EXAMPLE ---
    
    double carrier1   = input1A.GetValue();
    double modulator1 = input1B.GetValue();
    
    // Read the value of the 4-position vertical selector slider
    // Quantize or cast it directly to an integer depending on control setup
    int mode1 = (int) Math.round(switchDiode1.GetValue()); 
    double output1 = 0.0;

    switch(mode1) {
        case 0: // T: Leaky Transformer
            output1 = computeLeakyTransformer(carrier1, modulator1);
            break;
            
        case 1: // G: Germanium (Low 0.25V drop, softest exponential knee)
            output1 = computeDiodeCore(carrier1, modulator1, 0.25, 0.85);
            break;
            
        case 2: // S: Schottky (Medium 0.35V drop, balanced crisp transition)
            output1 = computeDiodeCore(carrier1, modulator1, 0.35, 1.75);
            break;
            
        case 3: // Si: Silicon (High 0.70V drop, hard-knee abrasive crossover distortion)
            output1 = computeDiodeCore(carrier1, modulator1, 0.70, 4.0);
            break;
            
        default:
            output1 = carrier1 * modulator1 * 0.2; // Fallback to linear mathematical multiplication
            break;
    }

    // Set voltage to module output jack
    out1.SetValue(output1);
    
    // Repeat the exact same pattern block above for Channels 2 and 3!
}




Diode rings depend on input levels: 
driving them hard forces the diodes to square off and compress, while low inputs create heavy crossover artifacts (especially Silicon).



@Override
public void ProcessSample() {
    // --- CHANNEL 1 PROCESSING WITH GAIN STAGING ---
    
    // 1. Fetch raw jack inputs
    double rawCarrier1   = input1A.GetValue();
    double rawModulator1 = input1B.GetValue();
    
    // 2. Read the silver drive knob
    // 0.0 = Muted, 1.0 = Unity Gain (nominal hardware level), 2.0 = Overdriven (+6dB)
    double drive1 = knob1.GetValue(); 
    
    // Apply gain staging to inputs
    double carrier1   = rawCarrier1 * drive1;
    double modulator1 = rawModulator1 * drive1;
    
    // 3. Read the 4-position component selection slider
    int mode1 = (int) Math.round(switchDiode1.GetValue()); 
    double output1 = 0.0;

    // 4. Send the gain-staged signals to the calculation engines
    switch(mode1) {
        case 0: // T: Leaky Transformer
            output1 = computeLeakyTransformer(carrier1, modulator1);
            break;
            
        case 1: // G: Germanium (Low threshold, soft curve)
            output1 = computeDiodeCore(carrier1, modulator1, 0.25, 0.85);
            break;
            
        case 2: // S: Schottky (Medium threshold, balanced curve)
            output1 = computeDiodeCore(carrier1, modulator1, 0.35, 1.75);
            break;
            
        case 3: // Si: Silicon (High threshold, hard knee)
            output1 = computeDiodeCore(carrier1, modulator1, 0.70, 4.0);
            break;
    }

    // 5. Automatic Makeup Gain 
    // Prevents extreme volume jumps or drops when twisting the drive knob
    if (drive1 > 0.05) {
        // Divide by square root of drive to compress the dynamic output variance smoothly
        output1 = output1 / Math.sqrt(drive1); 
    } else {
        output1 = 0.0; // Silence if knob is turned fully counter-clockwise
    }

    // 6. Safety Limiter (Voltage Modular limits output rails to +/- 12V)
    output1 = Math.max(-12.0, Math.min(12.0, output1));

    // Send final sound to output jack
    out1.SetValue(output1);
    
    // --- Repeat same logic block for Channel 2 and Channel 3 ---
}



Tweaking Component Behaviors with Input Gain

With input gain staging active, your selection modes will now exhibit distinct tonal dynamics based on how hard they are pushed by the knob:
• Low Gain (< 0.7): Silicon mode will sound choked, gating low signals due to its high 0.65V forward barrier. Germanium will remain smooth, clean, and audible even at very quiet levels.
• Nominal Gain (1.0): Standard mathematical ring modulation balance.
• High Gain (> 1.5): The Math.tanh core functions hard-saturate. Transformer mode will yield heavy, saturated lower frequencies due to asymmetrical sine shaping, while the diode modes will compress and spit out aggressive, harmonically dense clipping.




// Smooth tracker variable for the modulator's envelope state (Initialize to zero)
private double modEnvelope1 = 0.0;



@Override
public void ProcessSample() {
    // --- CHANNEL 1 WITH GAIN STAGING & ENVELOPE FOLLOWER ---
    
    // 1. Fetch raw inputs and apply drive
    double rawCarrier1   = input1A.GetValue();
    double rawModulator1 = input1B.GetValue();
    
    double drive1 = knob1.GetValue(); 
    double carrier1   = rawCarrier1 * drive1;
    double modulator1 = rawModulator1 * drive1;
    
    // 2. ENVELOPE FOLLOWER CALCULATION
    // Rectify the signal (absolute value) to get its instant magnitude
    double instantAmplitude = Math.abs(modulator1);
    
    // Smooth the tracking (Attack/Release coefficients)
    double smoothFactor = 0.998; 
    modEnvelope1 = (smoothFactor * modEnvelope1) + ((1.0 - smoothFactor) * instantAmplitude);
    
    // Normalize or scale the envelope level to fit a standard 0.0 to 1.0 range
    double normalizedEnv = Math.min(1.0, modEnvelope1 / 5.0);

    // 3. Read the 4-position component selection switch
    int mode1 = (int) Math.round(switchDiode1.GetValue()); 
    double output1 = 0.0;

    // 4. Send signals to calculations, incorporating the envelope
    switch(mode1) {
        case 0: // T: Leaky Transformer
            // The envelope dynamically pushes the core transformer harder into saturation 
            // on loud hits, while keeping low-level signals cleanly modulated
            double dynamicSatC = Math.sin(carrier1 * (0.2 + (normalizedEnv * 0.15))) * 4.5;
            double dynamicSatM = Math.sin(modulator1 * (0.15 + (normalizedEnv * 0.2))) * 4.5;
            double modulated = dynamicSatC * dynamicSatM * 0.2;
            output1 = modulated + (carrier1 * 0.05);
            break;
            
        case 1: // G: Germanium (Low threshold, soft curve)
            // Dynamic threshold: Loud hits transiently increase threshold up to 0.4V
            double dynamicVThreshG = 0.25 + (normalizedEnv * 0.15);
            output1 = computeDiodeCore(carrier1, modulator1, dynamicVThreshG, 0.85);
            break;
            
        case 2: // S: Schottky (Medium threshold, balanced curve)
            // Dynamic steepness: Loud signals cause the curve to tighten up (sharper transition)
            double dynamicKG = 1.75 + (normalizedEnv * 2.0);
            output1 = computeDiodeCore(carrier1, modulator1, 0.35, dynamicKG);
            break;
            
        case 3: // Si: Silicon (High threshold, hard knee)
            // Dynamic crossover: Quieter tails gate harshly, but loud hits punch straight through
            double dynamicVThreshSi = 0.70 * (1.0 - (normalizedEnv * 0.4)); // Lowers threshold when loud
            output1 = computeDiodeCore(carrier1, modulator1, dynamicVThreshSi, 4.0);
            break;
    }

    // 5. Automatic Makeup Gain 
    if (drive1 > 0.05) {
        output1 = output1 / Math.sqrt(drive1); 
    } else {
        output1 = 0.0;
    }

    // 6. Safety Limiter 
    output1 = Math.max(-12.0, Math.min(12.0, output1));

    // Send final sound to output jack
    outputJack1.SetValue(output1);
}


public void ProcessSample() {
    // --- LAMP FEEDBACK ENGINE ---
    // square root function for perceptual scaling
    double ledBrightness1 = Math.sqrt(normalizedEnv);
    
    // Set the value directly to your VMD LED component
    lamp1.SetValue(ledBrightness1);

    // --- FINAL VOLTAGE OUTPUT ---
    // Safety Limiter 
    output1 = Math.max(-12.0, Math.min(12.0, output1));

    // Send final sound to output jack
    out1.SetValue(output1);
}


## Agreed Prototype Direction — 2026-10-15

The TRM is an EMS-inspired instrument, not a component-level reproduction of the Synthi 100 ring modulator. Use the Synthi 100 drawing as inspiration for staged signal flow and balanced modulation; treat the four-position switch as an intentionally fictional component-swap palette.

- Three independent lanes each have Carrier and Modulator input gain controls, a four-position selector, an input-activity lamp, and an unattenuated output.
- Carrier and Modulator gain range is 0–2×, with 1× at the default. Do not apply automatic makeup gain or a final output attenuator/normalizer. Let each mode's actual output level and driven response remain audible.
- Preserve distinct selector defaults: lane 1 = Schottky (state 2), lane 2 = Germanium (state 1), lane 3 = Silicon (state 3). This gives three colors immediately at patch load.
- The initial mode interpretations are deliberately creative: T = transformer-like asymmetric saturation plus a small carrier-leakage consequence; G = soft conduction knee; S = low threshold with a tighter driven shoulder; Si = firmer low-level cutoff that opens with drive. These are listening prototypes, not historical claims about EMS component options.
- A per-lane envelope follower tracks the larger absolute value of the two gain-staged inputs, with 8 ms attack and 120 ms release. It modulates each selected mode's behavior and drives that lane's lamp. Lamp scaling references 5 V.
- Nonlinear ring processing uses the collection's 2× HalfBand19 interpolation/decimation path. Carrier/modulator gains and mode changes are smoothed; mode changes crossfade over approximately 5 ms.
- The module has no dry-through path. Host bypass outputs silence, freezes DSP history, and resumes with fresh input/envelope state.

The mode thresholds, saturation levels, transformer leakage amount, envelope response, and overall output levels were first-pass listening values. The user auditioned the implemented voices and approved them for TRM 1.0.0 on 2026-10-15. Treat them as the approved starting sound for future work; audible changes require a new test build and listening approval.
