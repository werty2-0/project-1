Frontend.class: Frontend.java
	javac Frontend.java
compileFDTest: FrontendDeveloperTests.java
	javac -cp ../junit5.jar:. FrontendDeveloperTests.java
runFDTests: Frontend.class compileFDTest
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests
runBDTests: Backend.java Song.java BackendDeveloperTests.java ISCPlaceholder.java
	javac -cp .:../junit5.jar Backend.java
	javac -cp .:../junit5.jar Song.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
	javac -cp .:../junit5.jar ISCPlaceholder.java	
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests


clean: 
	rm -rf *.class

