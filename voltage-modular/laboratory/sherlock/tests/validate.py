from pathlib import Path
import runpy,sys
sys.dont_write_bytecode=True
target=Path(__file__).resolve().parents[1]/'versions/1.0.0/tests/validate.py'
sys.path.insert(0,str(target.parent))
runpy.run_path(str(target),run_name='__main__')
