# TSL Triple Slew Limiter — 1.0.0

TSL provides three independent slew channels. Patch signal or CV to each IN and take the result from the corresponding OUT. There is no inter-channel normalization. Unpatched signal inputs target zero.

VOLTAGE 1–3 set maximum voltage movement, not output level. Fully CCW gives 0.05 V/s; fully CW gives 1000 V/s, with exponential travel. The knob has no off position. Typed knob values are V/s. At the slowest rate, a 1 V change takes 20 seconds, 5 V takes 100 seconds, and 10 V takes 200 seconds. Steady signals eventually reach unity gain. Audio can lose amplitude when its slope exceeds the selected rate; for pitch glide, insert TSL in the pitch CV path.

V.C. inputs 1/2 add to the manual setting. +5 V equals one full knob sweep toward faster response; negative CV slows it. Total rate is bounded to 0.05–1000 V/s.

Channel 3 T&H is a gate input. Unpatched, channel 3 slews continuously. With a cable connected, voltages strictly above 3 V track through the selected slew; 3 V or less holds the last output. Use a +5 V gate for tracking. There is no hold droop or hysteresis.

The power lamp stays lit. Host bypass sends each input directly to its corresponding output. On return, the output histories adopt the current inputs, including channel 3 while holding. Reset and preset/variation recall also adopt current inputs on the next active sample. Held voltages are not saved; initial histories start at zero.

Inspired by EMS workflows; not a verified circuit reproduction. Publication status is unconfirmed.
