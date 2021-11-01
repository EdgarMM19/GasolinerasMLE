all: binaris

binaris:
	CLASSPATH=".:./AIMA.jar:./Gasolina.jar" javac src/*.java -d bin

experiments: binaris
	cd bin && CLASSPATH=".:../AIMA.jar:../Gasolina.jar" java Main

clean:
	rm -f bin/*.class

clean-resultats:
	rm -f resultatsExperiments/*/*