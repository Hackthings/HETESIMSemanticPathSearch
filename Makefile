all:
	mkdir -p classes
	javac -d classes/  -classpath classes/ src/main/java/ownClasses/domain/utils/Pair.java
	javac -d classes/  -classpath classes/ src/main/java/sharedClasses/utils/Vertex.java
	javac -d classes/  -classpath classes/ src/main/java/sharedClasses/utils/Matrix.java
	javac -d classes/  -classpath classes/ src/main/java/sharedClasses/domain/nodes/*.java
	javac -d classes/  -classpath classes/ src/main/java/ownClasses/domain/queries/*.java
	javac -d classes/  -classpath classes/ src/main/java/ownClasses/presentation/ConsolePrinter.java
	javac -d classes/ -classpath classes/ src/main/java/ownClasses/domain/domainControllers/Persistance/*.java
	javac -d classes/ -classpath classes/ src/main/java/ownClasses/domain/domainControllers/*.java
	javac -d classes/ -classpath .:classes/:libs/gs-core-1.3-SNAPSHOT-last.jar:libs/gs-ui-1.3-SNAPSHOT-last.jar src/main/java/ownClasses/presentation/*.java
	javac -d classes/ -classpath classes/ src/main/java/Main.java
	javac -d classes/ -classpath classes/ src/main/java/ownClasses/domain/domainControllers/Drivers/*.java

run:
	java -cp classes/ main.java.Main

clean:
	rm -rf classes/*