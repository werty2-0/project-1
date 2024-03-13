runTests:
	javac -cp ../junit5.jar:. FrontendDeveloperTests.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

runFDTests:
	javac -cp ../junit5.jar:. FrontendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests

runBDTests:
	javac -cp .:../junit5.jar BackendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

clean:
	find . -type f -name "*.class" -delete
