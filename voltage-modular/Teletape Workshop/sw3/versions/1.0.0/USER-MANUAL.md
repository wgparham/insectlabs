# SW3 Voltage Controlled Switch — User Manual

SW3 contains two independent signal paths.

- **X** is a 2-to-1 selector. In its primary state, X1 feeds X Out; in its secondary state, X2 feeds X Out.
- **Y** is a 1-to-2 distributor. In its primary state, Y In feeds Y Out 1; in its secondary state, Y In feeds Y Out 2.

Each side has a manual gate button, an external gate input, a LATCH/MOMENTARY switch, a state lamp, and a Gate output. The upper and lower LATCH switches are physically oriented in opposite directions; use their panel labels to identify the mode.

## Selecting a route

In **MOMENTARY** mode, the secondary route is selected while exactly one of the manual button or external gate is active. Releasing both returns to primary. In **LATCH** mode, each rising edge from the button or external gate toggles the route between primary and secondary. A continuously high gate does not repeatedly toggle the route.

The external gate switches high at 3.0 V and releases at 1.8 V, with hysteresis between those levels. Each Gate output is 10 V while its gate/button is held. A rising edge also guarantees a 5 ms minimum output pulse, so brief triggers remain usable. The white state lamp lights while the secondary route is selected or while the Gate output is active.

## FADE

FADE controls how long each route transition takes. Fully counterclockwise is 0 ms and switches immediately; clockwise increases the fade exponentially to 200 ms. Both X and Y share this control. Its tooltip shows the time in milliseconds, and a typed millisecond value is mapped back to the knob position.

## Reference trimmers and unpatched jacks

During normal operation, the X Reference trimmer supplies X1 when X1 is unpatched, and the Y Reference trimmer supplies Y In when Y In is unpatched. Both trimmers span −10 V to +10 V and default to 0 V. An unpatched X2 is 0 V. If host bypass is enabled, connected X1 passes directly to X Out and connected Y In passes directly to Y Out 1; unpatched primary inputs are 0 V while bypassed, and Y Out 2 and both Gate outputs are silent.

## Bypass and saved state

The two latched route states are saved with presets and restored when loaded. Bypass silences the secondary Y output and Gate outputs, turns off the state lamps, and freezes switching/fade behavior. Returning from bypass re-reads the current gate and button state without replaying an old trigger edge.
