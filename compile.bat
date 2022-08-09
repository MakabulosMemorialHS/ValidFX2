

set JAVA_HOME="C:\Users\Robert\Desktop\javafx-sdk-18.0.1\bin"
set PATH_TO_FX="C:\Users\Robert\Desktop\javafx-sdk-18.0.1\lib"
set PATH_TO_FX_MODS="C:\Users\Robert\Desktop\jdk-18.0.1.1\jmods"
set JFX_MODULES="javafx.controls,javafx.fxml"

set OUTPUTDIR="classes"
set CLASSPATH="classes"

javac -d %OUTPUTDIR% vSplit.java
javac -d %OUTPUTDIR% PrintData.java
javac -d %OUTPUTDIR% RunEditor.java
javac -d %OUTPUTDIR% -cp %CLASSPATH% --module-path %PATH_TO_FX% --add-modules %JFX_MODULES% ValidFX.java

jar --create --file=validfx.jar --main-class=validfx.ValidFX -C %OUTPUTDIR% validfx/

