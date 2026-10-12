# 21-24eq circuit research

These files preserve the circuit analysis and source references behind the Wardenclyffe 21-24eq Frequency Corrector. They are design-history material, not runtime DSP primitives; the model and plots are specific to this passive network and its component assumptions.

The approved module pair and final response charts are in the [canonical v1.0.1 archive](../../21-24eq/versions/1.0.1/README.md). This archive keeps the analysis scripts, the original circuit references, and one clearly marked superseded qualitative response sketch. The duplicate final plots and an outdated development brief were removed; the canonical brief remains with v1.0.1.

- `model_passive_network.py` computes the passive network's nodal response for the documented values and loads.
- `plot_frequency_ranges.py` and `plot_input_coupling.py` regenerate response analyses. They require NumPy and Pillow; their outputs belong to this research directory, while the approved plots remain in v1.0.1.
- `references/` contains the source PDFs consulted for this design.
- `superseded/response-candidates.png` is an early qualitative redraw, not a measurement or release response. Its script is retained beside it for historical context.

The model uses assumed component values, a 1 kΩ source and a 100 kΩ load. Treat its results as calculations for this design study, not measurements of the module or a general circuit simulator.
