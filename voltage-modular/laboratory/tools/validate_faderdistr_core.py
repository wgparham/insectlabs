"""Compile and exercise the shared Panfade DSP prototype."""
from pathlib import Path
import subprocess

root = Path(__file__).resolve().parents[1]
module = root / 'faderdistr'
build = Path('C:/InsectLabs-Build') / 'faderdistr-core'
build.mkdir(parents=True, exist_ok=True)
source = module / 'prototype' / 'FaderDistrCore.java'
test = module / 'tests' / 'FaderDistrCoreTest.java'
subprocess.run([
    'javac', '--release', '17', '-Xlint:all', '-Werror',
    '-d', str(build), str(source), str(test)
], check=True)
subprocess.run([
    'java', '-cp', str(build), 'com.insectlabs.laboratory.faderdistr.FaderDistrCoreTest'
], check=True)
