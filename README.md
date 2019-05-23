# word counter
# by: Marcelo Flores Manrique

#PRERREQUISITES
Maven 3.6.0
Java 11.0.2

#BUILD
#Builds and runs the tests.
Run script in source folder:
build_with_dependencies.sh

#RUN TESTS
mvn test

#RUN EXAMPLE
#Go to the root folder of the zip file and execute:
./run.sh 2100 5

Where:
     2100 <- PageId
     5    <- n

#RUN TESTS
./run_tests.sh

#ARCHITECTURE
Stream Reader -> Splitter -> Cleaner -> Counter -> TopHitsFinder
The processing units (cleaner, counter and topHitsFinder) are stateless and use java parallel streams to process using a thread pool
