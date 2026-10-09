# XL-35 filter response tests

`FilterCoreTest.java` reflectively exercises the actual private filter cores. It checks all 16 cutoff positions against the -3 dB target, high-pass DC rejection, low-pass DC unity, out-of-band response, and upper-stage polarity/drive, and bypass-resume history reset. It does not test native panel rendering, patch save/reload, or host CPU usage.

From the version folder, compile both module sources against the installed SDK and run:

```powershell
$jar = 'C:\ProgramData\Voltage\voltage.jar'
New-Item -ItemType Directory -Force -Path out | Out-Null
javac --release 17 -Xlint:all -Werror -cp $jar -d out XL35h.java XL35c.java
javac --release 17 -Xlint:all -Werror -cp "out;$jar" -d out tests\FilterCoreTest.java
java -cp "out;$jar" FilterCoreTest
```
