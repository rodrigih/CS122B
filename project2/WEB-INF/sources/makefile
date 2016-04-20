CLASSPATH=.:../lib/mysql-connector-java-5.1.28.jar:../lib/javax.jar:../classes/
DEST=../classes/

DBConnection: DBConnection.java
	javac -cp $(CLASSPATH) DBConnection.java -d $(DEST)

MovieInfo: MovieInfo.java
	javac -cp $(CLASSPATH) MovieInfo.java -d $(DEST)

BrowseInfo: BrowseInfo.java
	javac -cp $(CLASSPATH) BrowseInfo.java -d $(DEST)

Movie: Movie.java
	javac -cp $(CLASSPATH) Movie.java -d $(DEST)

MovieList: MovieList.java
	javac -cp $(CLASSPATH) MovieList.java -d $(DEST)

test: test.java
	javac -cp $(CLASSPATH) test.java

all: DBConnection MovieInfo BrowseInfo Movie MovieList
