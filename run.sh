echo 'Usage: ./run.sh PageID n'
java --add-opens java.base/java.lang=ALL-UNNAMED  -jar ./target/words-1.0-jar-with-dependencies.jar $1 $2
