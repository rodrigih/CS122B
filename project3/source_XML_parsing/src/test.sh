tests="../stanford-movies/"
echo 'Compiling test files'

javac testParsers.java

echo 
echo 'Running test files'
echo

java -cp .:../bin/ testParsers "../stanford-movies/testMovieParser.xml" "../stanford-movies/testStarParser.xml" "../stanford-movies/testCastParser.xml"

echo
echo 'Deleting .class files'

rm -f testParser.class
