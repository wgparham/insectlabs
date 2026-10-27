# swCXI v0.1.1 (from your v0.1.0 save)

Status: headless checks passed; not yet tested in Designer / Voltage Modular.

1. Removed `core.reset()` from Initialize (state is set by the core's own defaults and SetStateInformation).
2. Removed dead `sequenceLength` field.
3. Silent-sample fix: break-before-make phase 2 now runs in the same sample phase 1 finishes, so no all-zero sample between old and new route.
4. CPU:
   - Only stage inputs that are routed (non-zero mux gain or the exclusive stage) are read.
   - Outputs are written only when the value changes; a held value is refreshed every 480 samples (10 ms), and immediately on Jack_Connected.
5. Notes field: v0.1.1. (`tests/validate.py` asserts "v0.1.0" on line 96: update it.)

Unchanged by design: 1 ms mux fade, instant change in clocked mode when the knob drops below the stage, Select CV mapping (bin centering not applied; your call).

Checks: compiles against voltage.jar (--release 17 -Xlint:all -Werror); your SwitchCoreTest 107 assertions pass unchanged; extra core checks 30/30; module-level stub checks 12/12 (idle ~110 writes per 100 ms vs 52,800 before).
