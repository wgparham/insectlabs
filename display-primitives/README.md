# Seven-segment canvas display primitive

Reusable renderer extracted from the tested sn16u six-canvas implementation, 2026-10-11. Supports signed decimals, five numeric digits, unit/mode captions, Hz/kHz or V/kV scaling with 1100/900 hysteresis, OL/Err glyphs, high-density scaling and cached redraws. Colors and dimensions are defined in the renderer. Formatting allocations are GUI-only; do not call it from an audio callback.

Java 17, dependency: Voltage Modular's VoltageCanvas API. Create one SevenSegmentCanvas object per display, then call update(canvas, value, baseUnit, context, preferredDecimals, allowScale) on the GUI timer. In Designer's single-source format, embed the class as a static helper and use the name BenchDisplay, as sn16u does; the standalone file is a reusable source reference, not an additional runtime dependency of sn16u.

Validation: strict compilation against installed SDK. Formatting/rendering tests and visual preview are retained with sn16u's current 2.0.1 pair. This is a GUI resource, with no DSP or measurement algorithm. No native-host CPU benchmark is claimed. Saved locally for inclusion in the next repository sync.

## WireTransportCanvas

`WireTransportCanvas.java` is a GUI-only Java2D reel/wire renderer extracted from Model 62 v2.0.0rc. It has no Voltage SDK dependency. Call `paint` on the GUI thread with a Graphics2D target, actual bitmap dimensions, core phase in radians, signed speed, normalized position, spool duration, transport flags, physical repair count, scar strength and monotonic nanoseconds. It caches its images, presents a completed frame, and handles reverse phase wrapping and broken-wire settling. The renderer reads state only; it does not simulate, record, repair or save a medium. The artwork depicts a cyclic wire loop rather than physical reel-fill accounting. Validation is kept with the Model 62 development candidate; native approval is pending.
