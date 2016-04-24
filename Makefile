
all:
    mkdir -p classes
	javac -d classes/ src/main/java/sharedClasses/utils/Pair.java
	javac -d classes/ src/main/java/sharedClasses/utils/Matrix.java
	javac -d classes/ src/main/java/sharedClasses/domain/nodes/*.java
	javac -d classes/ src/main/java/ownClasses/domain/queries/*.java
	javac -d classes/ src/main/java/ownClasses/presentation/ConsolePrinter.java
	javac -d classes/ -classpath classes/ src/main/java/sharedClasses/domain/domainControllers/DomainPersistanceController.java
	javac -d classes/ -classpath classes/ src/main/java/ownClasses/domain/domainControllers/*.java
	javac -d classes/ -classpath classes/ src/main/java/Main.java

run:
	java -cp classes/ main.java.Main

clean:
	rm -rf classes/*