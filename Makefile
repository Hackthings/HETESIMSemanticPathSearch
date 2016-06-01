all:
	mkdir -p classes
	javac -d classes/  -classpath classes/ src/ownClasses/domain/utils/Pair.java
	javac -d classes/  -classpath classes/ src/sharedClasses/utils/Vertex.java
	javac -d classes/  -classpath classes/ src/sharedClasses/utils/Matrix.java
	javac -d classes/  -classpath classes/ src/sharedClasses/domain/nodes/*.java
	javac -d classes/  -classpath classes/ src/ownClasses/domain/queries/*.java
	javac -d classes/  -classpath classes/ src/ownClasses/presentation/ConsolePrinter.java
	javac -d classes/ -classpath classes/ src/ownClasses/domain/domainControllers/Persistance/*.java
	javac -d classes/ -classpath classes/ src/ownClasses/domain/domainControllers/*.java
	javac -d classes/ -classpath .:classes/:libs/gs-core-1.3-SNAPSHOT-last.jar:libs/gs-ui-1.3-SNAPSHOT-last.jar src/ownClasses/presentation/*.java
	javac -d classes/ -classpath classes/ src/Main.java
	javac -d classes/ -classpath classes/ src/ownClasses/domain/domainControllers/Drivers/*.java

run:
	java -cp classes/ Main

clean:
	rm -rf classes/*