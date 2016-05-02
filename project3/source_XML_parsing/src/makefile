SOURCES = \
		  Movie.java \
		  Star.java \
		  MovieParser.java \
		  CastParser.java \
		  StarParser.java

default: all

all: $(SOURCES:.java=.class)

%.class: %.java
	javac -d ../bin/ $<

clean:
	$(RM) ../bin/*.class
