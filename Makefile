
runBDTests: Backend.java Song.java ISCPlaceholder.java  BackendPlaceholderPartnerTests.java BackendDeveloperTests.java
	javac -cp .:../junit5.jar Backend.java
	javac -cp .:../junit5.jar Song.java
	javac -cp .:../junit5.jar ISCPlaceholder.java
	javac -cp .:../junit5.jar BackendPlaceholderPartnerTests.java
	javac -cp .:../junit5.jar BackendDeveloperTests.java
	java -jar ../junit5.jar -cp . -c BackendDeveloperTests

runFDTests: Frontend.class compileFDTest
	java -jar ../junit5.jar -cp . -c FrontendDeveloperTests

Frontend.class: Frontend.java
	javac Frontend.java

compileFDTest: FrontendDeveloperTests.java
	javac -cp ../junit5.jar:. FrontendDeveloperTests.java

runApp: App.java
	javac -cp .:../junit5.jar App.java
	java App

clean: 
	rm -rf *.class


