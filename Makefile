
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
        $(JC) $(JFLAGS) $*.java

CLASSES = \
        /*.java \
        /*/*.java \
        /*/*/*.java \
        /*/*/*/*.java

default: classes

classes: $(CLASSES:.java=.class)

run: $(MAIN).class
    $(JVM) $(MAIN)

clean:
    $(RM) *.class