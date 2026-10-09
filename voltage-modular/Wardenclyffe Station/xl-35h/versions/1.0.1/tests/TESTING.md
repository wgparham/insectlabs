# XL-35 High-Pass filter response tests

`FilterCoreTest.java` reflectively tests the actual private filter core in `XL35h.java`. It checks all 16 cutoff positions against the -3 dB target, DC behavior, out-of-band rejection, upper-stage polarity/drive, filter-core reset behavior, and typed frequency selection at all labels and both clamp limits. It does not test native panel rendering, patch save/reload, or host CPU usage.

From this module’s version folder, compile against the installed SDK and run:

```powershell
$jar = 'C:\ProgramData\Voltage\voltage.jar'
New-Item -ItemType Directory -Force -Path out | Out-Null
javac --release 17 -Xlint:all -Werror -cp $jar -d out XL35h.java
javac --release 17 -Xlint:all -Werror -cp "out;$jar" -d out tests\FilterCoreTest.java
java -cp "out;$jar" FilterCoreTest
```
