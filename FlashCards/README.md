Just run client from IDE like IntelliJ

Or if you want to run from a terminal do the following
1. Build your project in IDE
2. In the `out` directory, find the root directory of the program
3. Create a `jar` file with cmd ```jar cmf ../FlashCards/META-INF/MANIFEST.MF flashcard.jar 
   flashcard```
4. Now with the jar file `flashcard.jar`, you can run the program from a terminal like this ```java -jar flashcard.jar```
