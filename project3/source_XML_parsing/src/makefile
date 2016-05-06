SOURCES = \
		  Movie.java \
		  Star.java \
		  MovieParser.java \
		  CastParser.java \
		  StarParser.java \
		  InsertXMLData.java
LIB = ".:../lib/mysql-connector-java-5.1.28.jar"

default: all

all: $(SOURCES:.java=.class)

%.class: %.java
	javac -cp $(LIB) -d ../bin/ $<

clean:
	$(RM) ../bin/*.class
