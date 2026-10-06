"""Validate the canonical Following archive."""
from pathlib import Path
import runpy,sys
tests=Path(__file__).resolve().parents[1]/'versions/1.0.0/tests'
sys.path.insert(0,str(tests))
runpy.run_path(str(tests/'validate.py'),run_name='__main__')
