all: binaries

binaries:
	CLASSPATH=".:./AIMA.jar:./sources.jar" javac src/*.java -d bin

run-sa: binaries
	cd bin && CLASSPATH=".:../AIMA.jar:../sources.jar" java ExperimentsSimulatedAnnealing

run-hc: binaries
	cd bin && CLASSPATH=".:../AIMA.jar:../sources.jar" java ExperimentsHillClimbing

clean:
	rm -f bin/*.class

clean-files:
	rm -f resultats/*/*