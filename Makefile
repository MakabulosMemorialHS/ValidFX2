.PHONY : build run clean archive jar


PATH_TO_FX="/usr/share/openjfx/lib"
JFX_MODULES="javafx.controls,javafx.fxml"
OUTPUTDIR="classes"
CLASSPATH="classes"

clean :
	touch scratch.class yonwah.txt valid-tickets.txt
	touch INSTRUCTIONS.pdf INSTRUCTIONS.log INSTRUCTIONS.aux
	rm *.class
	rm INSTRUCTIONS.pdf INSTRUCTIONS.log INSTRUCTIONS.aux
	rm -Rf classes/
	rm yonwah.txt
	rm valid-tickets.txt


build : vSplit.java ValidFX.java RunEditor.java PrintTickets.java
	javac -d ${OUTPUTDIR} vSplit.java
	javac -d ${OUTPUTDIR} PrintTickets.java
	javac -d ${OUTPUTDIR} RunEditor.java
	javac -d ${OUTPUTDIR} -cp ${CLASSPATH} --module-path ${PATH_TO_FX} --add-modules ${JFX_MODULES} ValidFX.java


## Note how to run classes that are in a package. Study this well.
## Note how  the name of the Main Class is appended to its package name.

run :
	java -cp ${CLASSPATH} --module-path ${PATH_TO_FX} --add-modules ${JFX_MODULES} validfx.ValidFX

archive :
	(cd .. && tar -cf validfx2.tar ValidFX2/*)

jar :
	(cd ${OUTPUTDIR} && jar -cfe validfx2.jar validfx.ValidFX ./validfx/*.class)


run-jar :
	java --module-path ${PATH_TO_FX} --add-modules ${JFX_MODULES} -jar ${OUTPUTDIR}/validfx2.jar


