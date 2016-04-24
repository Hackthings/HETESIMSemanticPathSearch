
all:
	javac src/main/java/Main.java
	javac src/main/java/sharedClasses/utils/Pair.java
	javac src/main/java/sharedClasses/utils/Matrix.java
	javac src/main/java/sharedClasses/DomainControllers/DomainPersistanceController.java
	javac src/main/java/sharedClasses/domain/nodes/Author.java
	javac src/main/java/sharedClasses/domain/nodes/Paper.java
	javac src/main/java/sharedClasses/domain/nodes/Term.java
	javac src/main/java/sharedClasses/domain/nodes/Conference.java
	javac src/main/java/ownClasses/presentation/ConsolePrinter.java
	javac src/main/java/ownClasses/domain/queries/IntervaledQuery.java
	javac src/main/java/ownClasses/domain/queries/LimitedQuery.java
	javac src/main/java/ownClasses/domain/queries/OrderedQuery.java
	javac src/main/java/ownClasses/domain/queries/Query.java
	javac src/main/java/ownClasses/domain/queries/SubSetQuery.java
	javac src/main/java/ownClasses/domain/domainControllers/DomainHetesimController.java
	javac src/main/java/ownClasses/domain/domainControllers/DomainMainController.java

run:
	java src.main.java.Main

clean:
	rm src/main/java/Main.class
	rm src/main/java/sharedClasses/utils/Pair.class
	rm src/main/java/sharedClasses/utils/Matrix.class
	rm src/main/java/sharedClasses/DomainControllers/DomainPersistanceController.class
	rm src/main/java/sharedClasses/domain/nodes/Author.class
	rm src/main/java/sharedClasses/domain/nodes/Paper.class
	rm src/main/java/sharedClasses/domain/nodes/Term.class
	rm src/main/java/sharedClasses/domain/nodes/Conference.class
	rm src/main/java/ownClasses/presentation/ConsolePrinter.class
	rm src/main/java/ownClasses/domain/queries/IntervaledQuery.class
	rm src/main/java/ownClasses/domain/queries/LimitedQuery.class
	rm src/main/java/ownClasses/domain/queries/OrderedQuery.class
	rm src/main/java/ownClasses/domain/queries/Query.class
	rm src/main/java/ownClasses/domain/queries/SubSetQuery.class
	rm src/main/java/ownClasses/domain/domainControllers/DomainHetesimController.class
	rm src/main/java/ownClasses/domain/domainControllers/DomainMainController.class
