# RVG 2.0.0 research notes

User relayed a conversation with a former rackmount RVG owner: central voltages seemed more common than extremes, with compression/clipping or circuit/transistor limitations proposed as a cause. This is useful owner testimony, not a verified schematic mechanism or measured distribution.

The user chose to audition tanh, then approved normalization to restore +/-2.5 V. The chosen software model samples uniform bipolar values, applies normalized tanh, then linear Level. Within the shaped range, tanh does not create a center-heavy density; original compression reduced extrema by reducing the range. Do not conflate the approved musical result with a proven original circuit distribution.

The third held value directly modulates the clock. Available Synthi circuit material informs a plausible structure; exact standalone correspondence and component choices remain unverified. See the module's references and shared series archive. Tuned hold drift is a musical choice, not an EMS measurement.
