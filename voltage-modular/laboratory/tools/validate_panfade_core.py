"""Compile and exercise the shared Panfade DSP prototype."""
from pathlib import Path
import subprocess

root = Path(__file__).resolve().parents[1]
module = root / 'panfade'
build = root / 'build' / 'panfade-core'
build.mkdir(parents=True, exist_ok=True)
source = module / 'prototype' / 'PanfadeCore.java'
test = module / 'tests' / 'PanfadeCoreTest.java'
subprocess.run([
    'javac', '--release', '17', '-Xlint:all', '-Werror',
    '-d', str(build), str(source), str(test)
], check=True)
subprocess.run([
    'java', '-cp', str(build), 'com.insectlabs.laboratory.panfade.PanfadeCoreTest'
], check=True)
