Frontend.class: Frontend.java
	javac Frontend.java
compileFDTest: FrontendDeveloperTests.java
	javac -cp ../junit5.jar:. FrontendDeveloperTests.java
runFDTests: FrontendDeveloperTests.class Frontend.class
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
clean:
	find . -type f -name "*.class" -delete
