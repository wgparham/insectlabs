# Software Slew Implementation Block
difference = input_voltage - previous_output

if difference > MaxChange:
    # Rising edge is clamped to maximum speed limit
    current_output = previous_output + MaxChange
elif difference < -MaxChange:
    # Falling edge is clamped to maximum speed limit
    current_output = previous_output - MaxChange
else:
    # If change is slower than the speed limit, track input perfectly (Unity Gain)
    current_output = input_voltage