CLASSPATH=.:../lib/mysql-connector-java-5.1.28.jar:../lib/javax.jar:../classes/
DEST=../classes/

DBConnection: DBConnection.java
	javac -cp $(CLASSPATH) DBConnection.java -d $(DEST)

MovieInfo: MovieInfo.java
	javac -cp $(CLASSPATH) MovieInfo.java -d $(DEST)

StarInfo: StarInfo.java
	javac -cp $(CLASSPATH) StarInfo.java -d $(DEST)

BrowseInfo: BrowseInfo.java
	javac -cp $(CLASSPATH) BrowseInfo.java -d $(DEST)

Movie: Movie.java
	javac -cp $(CLASSPATH) Movie.java -d $(DEST)

Search: Search.java
	javac -cp $(CLASSPATH) Search.java -d $(DEST)

MovieList: MovieList.java
	javac -cp $(CLASSPATH) MovieList.java -d $(DEST)

getMovies: getMovies.java
	javac -Xlint:unchecked -cp $(CLASSPATH) getMovies.java -d $(DEST)

getSingleMovie: getSingleMovie.java
	javac -Xlint:unchecked -cp $(CLASSPATH) getSingleMovie.java -d $(DEST)

Star: Star.java
	javac -cp $(CLASSPATH) Star.java -d $(DEST)

getStar: getStar.java
	javac -cp $(CLASSPATH) getStar.java -d $(DEST)

AdvSearchServlet: AdvSearchServlet.java
	javac -cp $(CLASSPATH) AdvSearchServlet.java -d $(DEST)

test: test.java
	javac -cp $(CLASSPATH) test.java

runTest: test.class
	java -cp $(CLASSPATH) test
all: DBConnection MovieInfo StarInfo BrowseInfo Movie Search MovieList getMovies getSingleMovie Star getStar AdvSearchServlet
