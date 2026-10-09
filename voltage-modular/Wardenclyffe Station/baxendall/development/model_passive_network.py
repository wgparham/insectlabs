from __future__ import annotations

import numpy as np


R1, R2, R3 = 10_000.0, 1_000.0, 10_000.0
POT = 100_000.0
RS, RL = 1_000.0, 100_000.0
BASE = (22e-9, 220e-9, 2.2e-9, 22e-9)
DARK = (47e-9, 470e-9, 4.7e-9, 47e-9)
SHIFT_TEST = (100e-9, 1e-6, 10e-9, 100e-9)


def response(freq: np.ndarray, caps: tuple[float, float, float, float],
             bass: float, treble: float, input_coupling: float = 0.0) -> np.ndarray:
    """Complex Vout/Vin for the supplied passive James/Baxandall network.

    bass/treble are wiper positions from the top pot terminal (0..1).
    Nodes: source-side input, bass top/wiper/bottom, treble top/wiper/bottom.
    The input has 1 kohm Thevenin resistance and the output a 100 kohm load.
    """
    c1, c2, c3, c4 = caps
    out = np.empty(freq.size, dtype=complex)
    for index, hz in enumerate(freq):
        s = 2j * np.pi * hz
        node_count = 8 if input_coupling > 0 else 7
        y = np.zeros((node_count, node_count), dtype=complex)
        b = np.zeros(node_count, dtype=complex)

        def branch(a: int, z: int | None, admittance: complex) -> None:
            y[a, a] += admittance
            if z is None:
                return
            y[z, z] += admittance
            y[a, z] -= admittance
            y[z, a] -= admittance

        # Node 0 is the circuit input. An optional coupling capacitor sits
        # between it and the source's Thevenin resistance.
        if input_coupling > 0:
            branch(0, 7, s * input_coupling)
            branch(7, None, 1 / RS)
            b[7] += 1 / RS
        else:
            branch(0, None, 1 / RS)
            b[0] += 1 / RS
        # Nodes 1..6: bass top, bass wiper, bass bottom,
        # treble top, treble wiper/output, treble bottom.
        branch(0, 1, 1 / R1)
        branch(3, None, 1 / R2)
        branch(1, 2, 1 / max(bass * POT, 1e-6))
        branch(2, 3, 1 / max((1 - bass) * POT, 1e-6))
        branch(2, 5, 1 / R3)
        branch(4, 5, 1 / max(treble * POT, 1e-6))
        branch(5, 6, 1 / max((1 - treble) * POT, 1e-6))
        branch(1, 2, s * c1)
        branch(2, 3, s * c2)
        branch(0, 4, s * c3)
        branch(6, None, s * c4)
        branch(5, None, 1 / RL)
        out[index] = np.linalg.solve(y, b)[5]
    return out


def db(values: np.ndarray) -> np.ndarray:
    return 20 * np.log10(np.maximum(np.abs(values), 1e-15))


if __name__ == "__main__":
    f = np.geomspace(10, 20_000, 1400)
    for label, caps in (("Reference", BASE), ("Dark candidate", DARK), ("SHIFT test only", SHIFT_TEST)):
        values = db(response(f, caps, 0.5, 0.5))
        mid = float(np.mean(values[(f >= 500) & (f <= 2000)]))
        print(f"{label:20s} centered mid-band={mid:6.1f} dB")
    configs = {
        "bass top / treble top": (0.0, 0.0),
        "bass top / treble bottom": (0.0, 1.0),
        "bass bottom / treble top": (1.0, 0.0),
        "bass bottom / treble bottom": (1.0, 1.0),
        "both center": (0.5, 0.5),
    }
    for label, (bass, treble) in configs.items():
        values = db(response(f, BASE, bass, treble))
        lo = float(np.mean(values[(f >= 500) & (f <= 2000)]))
        print(f"{label:29s} mid={lo:6.1f} dB  20Hz={values[np.argmin(abs(f-20))]:6.1f} dB  100Hz={values[np.argmin(abs(f-100))]:6.1f} dB  10kHz={values[np.argmin(abs(f-10000))]:6.1f} dB")
