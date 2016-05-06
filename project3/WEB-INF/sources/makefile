CLASSPATH=.:../lib/mysql-connector-java-5.1.28.jar:../lib/javax.jar:../classes/:../lib/javax.json-1.0.jar:../lib/servlet-api.jar
DEST=../classes/

SOURCES = DBConnection.java \
		  MovieInfo.java \
		  StarInfo.java \
		  BrowseInfo.java \
		  Movie.java \
		  Search.java \
		  MovieList.java \
		  getMovies.java \
		  getSingleMovie.java \
		  Star.java \
		  getStar.java \
		  AdvSearchServlet.java \
		  AddToCart.java \
		  Cart.java \
		  CartItem.java \
		  RemoveItem.java \
		  MyConstants.java \
		  VerifyUtils.java \
		  LoginReCaptcha.java \
		  Checkout.java \
	      InsertStar.java \
		  InsertMovie.java \
		  EmployeeLoginServlet.java

default: all

all: $(SOURCES:.java=.class)

%.class: %.java
	javac -Xlint:unchecked -cp $(CLASSPATH) -d $(DEST) $<

clean:
	$(RM) ../bin/*.class
