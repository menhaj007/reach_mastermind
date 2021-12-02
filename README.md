# Mastermind
#### A terminal based game.
This documentation is a guide on how to install and use the application. The following topics are covered:

- Installation
- Technologies used
- Challenge Requirements
- Attempted extra features.

## Installation
This application is built with Java 11 and MySQL server Version 8. Furthermore, JUNIT 5 is used to test the core functions. This guide will demonstrate how to use install the required software to test and rung this Application. IntelliJ IDEA IDE is used to implement this project. If you are using Eclipse or VS-Code this instruction may not work. Since random.org provides an API for ease access to use their Random Integer generate application, you will need an API key to connect to random.org API. There are two JAR files one for MySQL dependency and another for Random Generator API inside the repository which need to be added into the project module in order to connect to MySQL and random.org’s website.

- To Download Java 11, please visit the following links->
  https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html
- To review Java 11 installation process, please refer to this link https://docs.oracle.com/en/java/javase/11/install/
- To install MySQL Server community edition, please follow the following link https://dev.mysql.com/downloads/mysql/
- For help on how to use MySQL, click on the following link https://dev.mysql.com/doc/
- This link shows how to add a dependency/jar file into a java project in IntelliJ https://stackoverflow.com/questions/30651830/use-jdbc-mysql-connector-in-intellij-idea
- For instruction on how to use the random.org API, please visit this https://github.com/iarks/random_org-api-example
- Note: you don't need mysql server, if ou prefer to read and write guess history in the RAM. MySQL is used for resistance purposes.
- To download the jar files, please visit the GitHub link -> https://github.com/menhaj007/jarsFiles

## Core requirements of this project are list below
### Developing environment for running this project.
1. To make sure you have Java 11 installed, type in the terminal
```
$: java -version
=>: Similiar Output:
 openjdk version "11.0.11" 2021-04-20
=>: openJDK runtime Environment OS…
=>: openJDK 64bit Server VM…
```
2. Please make sure you have IntelliJ IDEA installed. You can also download it from here ->
   -- https://www.jetbrains.com/idea/
3. Random API generator.
   --  You can create your own API, or can you use the temporary one available inside the project.
   -- Download the Random Generator jar dependency, and review the installation provided on the link here ->
   https://github.com/menhaj007/jarsFiles

This meets all the requirements to test the core feature of the application such as logic, feedback, history and output. However, if you do want to use just the core requirements, please comment out all methods in Mastermind.java where the signature includes the word “write/read from DB/IntoDB”.
If you want to test the project with MySQL and JUNIT features, please follow the next couple steps too.

4. Install MySQL Database from the above link. Once installed either use workbench or command line, to issue the following commands in order to create a database and make it active:
   -- change the path location for jdbc, so it matches your computer's information.

```
    DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", "your_user_name", "your_mysql's_user_password");
```

```
public static void readFeedbackFromDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", "your_user_name", "your_mysql's_user_password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM computer_feedback");
            while (resultSet.next()) {
                String userData = resultSet.getString("id") +  "\t" + resultSet.getString("userName") + "\t" + resultSet.getString("correctNumberLocation") + "\t" + resultSet.getString("correctNumberOnly") + "\t" + resultSet.getString("incorrectGuess")+ "." ;
                System.out.println(userData);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
- Visit this link for more information =>
  -- https://zetcode.com/db/mysqljava/#:~:text=To%20connect%20to%20MySQL%20in%20Java%2C%20MySQL%20provides,does%20not%20rely%20on%20the%20MySQL%20client%20libraries.
```
CREATE database mastermind; 
use mastermind;
```
5. Create the following tables;
```
CREATE TABLE computer_feedback(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userName VARCHAR(30) NULL,
    correctNumberLocation INT NULL,
    correctNumberOnly INT NULL,
    incorrectGuess INT NULL
);
CREATE TABLE user_input_history(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userName VARCHAR(30) NULL,
    userInput VARCHAR(250) NULL
);

CREATE TABLE user_name_feedback_guess(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    userName VARCHAR(30) NULL,
    feedback VARCHAR(250) NULL,
    guess VARCHAR(250) NULL
);
```
Use the following commands to delete a table, check the structure of a table.
```
show tables;
desc table_name;
drop table table_name;

Select * from table_name;
SELECT * FROM user_input_history;
```
6. Make sure that MySQL dependency is added to your project. Please refer to the following link to download and install the dependency. Installing MySQL dependency jar file is similar to Random Generator Dependency.
```
https://github.com/menhaj007/jarsFiles
```

Now, your IDE should be able to run the application. For more information about using MySQL with Java, please visit the following link.
-- https://zetcode.com/db/mysqljava/#:~:text=To%20connect%20to%20MySQL%20in%20Java%2C%20MySQL%20provides,does%20not%20rely%20on%20the%20MySQL%20client%20libraries.
7. JUnit is import into the project but haven't been used in its full effect. There are links provided, if you want to use a JUnit library. Version 5 will work.
- https://it-qa.com/how-do-i-get-junit-5-in-intellij/
- https://www.jetbrains.com/help/idea/junit.html

## Technology
Familiarity with the following technologies can help.
- Java, C++
- SQL, PL/SQL, T-SQL
- GitHub, for access repository
- MySQL Workbench, MySQL terminal App.
## Dependencies
Instructions on how to use them in your own application are linked below.
```
    stackoverflow.com/questions/30651830/use-jdbc-mysql-connector-in-intellij-idea
```
| Dependency | README |
| ------ | ------ |
| MySQL DBC Driver  |[https://github.com/menhaj007/jarsFiles/blob/main/mysql-connector-java-8.0.27.jar] |
| Random.org API | [https://github.com/menhaj007/jarsFiles/blob/main/mysql-connector-java-8.0.27.jar] |
| JUNIT | [https://github.com/menhaj007/jarsFiles/blob/main/org_junit_platform_junit-platform-console-standalone_1.6.0_junit-platform-console-standalone-1.6.0.jar] |

There is a folder inside the project directory named jarFiles (https://github.com/menhaj007/reach_mastermind/tree/main/src/jarFiles) that includes all the required dependencies for this project. If you clone this repository, you will have them inside the project.

## Link for Java Documentation
- Please open java-documentation link on this repository and open index.html then select preview or open in a browser tab to read the documentation for this application
  -- https://github.com/menhaj007/reach_mastermind/blob/main/src/JavaDocsReadMe/index.html

## Basic Diagram
![Tux, A basic diagram of the app](https://github.com/menhaj007/reach_mastermind/blob/main/MastermindDiagram.PNG)

8. How to run the application?
- First install Java 11, MySQL, MySQL-workbench
- Run the provided SQL commands
- Download the application from this repository
- Obtain an API key from Random.org and then add the key inside the RandomGenerator.java class where it says API key
- Add dependencies which are provided inside this repository/project in the dependency module
- Finally, using IntelliJ IDEA, open the GameRunner.java and click run right top-right side of the IDE.
  -- If you see red errors from the database, that means the database connection is not working properly or the SQL commands were not issued, jdbc driver path is not correct. However, without the database function, you will be able to test and run the application, but won't able to save the feedbacks and guessed history in the database.

## Variables and Methods in the application
#### There is a java-documentation as mentioned above this paragraph that has comments regarding the application and its variables and methods and their functions. Below a list of the variables and methods just for quick reference.
The following variables are used at global scope.
- computerPoints, initialized with 10. On Each correct guess, these points are deducted
- userPoints, initialized with the value of 10. On each wrong guess, user loses points.
- difficultyLevelValue, there are 3 levels in this game. If user enters 1, the value of this variable changes to 4. Which means, the random number will consist of an array of integers with length of 4. If user's choice is 2, the array length will be 6, and 3 will consist of 8 elements. Finally, as the level of difficulty increases, the number of attempts decreases from 10, 8, to 5 tries respectively.
- numberOfAttempts, the default value is 10. But it will change when user chooses a difficult level and if user keep guessing the number. It will reset when user agrees to play again.
- computerFeedback, type String, this is a 2 Dimension array. A player's feedback is copied here to the player can review either after failing the game or winning the game. This method doesn't automatically push the data to MySQL. It is just in memory access data.
- guessHistory, type String, will hold the information about the users guesses only to keep the record cleaner for review.
- numberOfSavedFeedback
- numberSavedHistory
- The last two variables are used to keep track of the saved results and feedbacks to avoid unnecessary loops for null values.
- userName, This will record a player's name on the initial start and will be saved with each player's game cycle.
- isWin, keep status of the while loop to avoid unnecessary loops. It is declared at global scope to provide flexibility for the other methods.

The following Methods are designed to either communicate with the user or serve as a helper method.

- chooseDifficultyLevel(), asks the user for an integer input, then changes the numberOfRandomNumber, max, min limit for the random.org/api generator.
- generateRandomNumbers(), makes a get request to random.org/api to generate a random array of integers. Then it returns the array for future use.
- isValid(String userGuess), will accept a userValue, then checks if the input has numeric values between 0 and 9, and also check if the userInput value's length is equal to difficulty level. if userInput is size 4, then the random array length must 4 too. Otherwise, it fails. It returns truthy/falsy.
- convertStringToArrayOfInteger(), accepts a string, in this case user's input, then converts the characters to integer, so it can be compared.
- readUserInput(), in the terminal, it asks for the user to enter values.
- start(), this function job is to record user's name at global scope. Then starts the game.
- play(), the engine for this application. This method/function triggers other functions such as chooseDifficultyLevel(), generateRandomNumbers(), wile loop, and calculate. ...
- calculate(arrayA, arrayB), this method takes two arrays, then compares them to determine if their values match. For instance, arrayA = {1,2,3,1}, arrayB = {1,2,3,4}. There will be three correct number on the correct location, One value 4, is incorrect because is not in the list. Since arrayA has two 1s, the exact location takes precedence over the wrong location. If arrayA = {1,2,4,3}, an arrayB = {2,1,3,4}, this will produce 4 correct numbers in the wrong positions. There is JUnit test written for this method, you can run your test. The difficult part in this challenge was to find the result for (true, false, false), if all matched, other two conditions will fail, if the second matched the first and last will fail, if the was true, the first two would fail. Perhaps, there many ways to solve this, but I ended up using multiple arrays then comparing the index values in both. if a match found, then replaced with a value out of the domain range in this case {x>=0}, since negative values are not part of the domain, I keep replacing the tmpArray to keep testing. The other way, I thought to work on it was to use a string, if a number found the first match in the list, then increment the counter, if not, added to the string, so at the end, the string will consist of not matched? true: false,
- guessedCorrectAndNumberLocation, it increments when two values at exact indices match in the two arrays.
- guessedCorrectNumber, it increments when a value doesn't have an exact position match but still exist in the list. It is only match with one value, if there are two {1,1}, {1,2}, only the first correct match increments.
- incorrectGuess, after find the correct and incorrectValueAtWrongPost, then add them then subtract from the array.length;
- writeFeedbackIntoDB, this method takes an object with the result of numbers and saves in the database.
- saveFeedbackToArray(), saves the feedback in the RAM.
- getFeedbackFromArray() reads from RAM. If the program stop, it loses all of its data.
- result(counter), just prints you won!
- saveGuessHistoryToArray(String guessed), saves entered values into RAM
- getGuessHistoryFromArray prints them.

The following parts are optionals. It is user's choice to install with the application or not. This app can work with mysql. To work without MySQL connection delete all methods with "DB" letters in their signature methods.
- writeInputHistoryINtoDB(String userName, String guess), write the user input with its in the database for future access.
- I was tempted to use one -> many relationship. A user can have many guesses, but due to the time constraints, I left it behind.
- There is no relationship model in this app. It just saves data and if need can delete them too.
- hint(int[] array), if a user guess 3 times, and still can find the number, it gives them a range of the minimum value and the maximum value in the array.
- resetHistory(), it sets all variables to initial state. If a player wants to play again, then these methods wipes out the told numberOfAttempts, history and feedback to start new.


## Planning and implantation
After reviewing the challenge, it was clear that the core of the assignment was finding the correct number and exact location. The steps required to solve this challenge is to draw two arrays and compare them using pen and paper or a whiteboard using a T-Table.

<img src="https://github.com/menhaj007/reach_mastermind/blob/main/board1.jpg" alt="initial planing" width="350"/>

<img src="https://github.com/menhaj007/reach_mastermind/blob/main/board2.jpg" alt="initial planing" width="350"/>

<img src="https://github.com/menhaj007/reach_mastermind/blob/main/board3.jpg" alt="initial planing" width="350"/>


|  randomArray       |userInput                          |result                         |
|----------------|-------------------------------|-----------------------------|
|[1,2,3,4] |[1,3,2,4]            | Correct: 2, Incorrect: 2, Wrong: 0            |

Since each element has to have one of the following results =>
- Correct number and location
- Correct number but wrong location (incorrect)
- incorrect number which doesn't exist in the computer's generated list.

As a result each element will require one of the tree responses of being true/false.

```
if (correctNumberAtCorrectLocation exist)
    then (!correctNumberInWrongLocation) 
      then (!wrongNumber)
else if (correctNumberinWrongLocation)
    then (!correctNumberAtCorrectLocation)
      then(!wrongNumber)
else
    wrongNumber = userInput.lengh - (correctNumberAtCorrectLocation+correctNumberinWrongLocation)
```
There is no need to test for the third case because the first two cases will determine whether the third case is true or false.

This part of the challenge consumed lots of time to solve it in a better way, but my solution is not good at time and space complexity. The reason is the existence of duplicate values in both randomGenerate integers and in user's input. For instance ->

```
  computerGenerated = [1,1,3,4];
  userGuess = [1,3,2,4];
  for (i = 0; i < userGuess.length; i++) {
    if (userGuess[i] == computerGenerated[i]) {
      userGuess[0] == computerGenerated[0] is true, so 1 exist in both lists.
      userGuess[3] == computerGenerated[3] is also true, so 4 exists in both lists.
      
      - userGuess[1] != computerGenerated[1] doesn't exist at the correct location.
      - userGuess[2] != computerGenerated[2] doesn't exist at the correct location.
    } else {
      - The main challenge is this else condition. If each element must have on output then the two array indices 0 and 1 should not be compared with the remaing two element in order to find the missplaced digits. 
      - if the two indices are not ommited from the next for-loops then the out correctNumberInWrongLocation will be true 1 at the index 1. Digit 3 will produce 1 correctNumberInWrongLocation and 4 will produce two results.If we added the numbers of correct and incorrect, it will exceed the total number of elements insert/exits in the array. 1+1 + 1 + 0 + 1+1 = 5. But there are not 5 elements in the input.
      
      for (j = 0; j < userGuess.length; j++) {
        - Below line compares the element which didn't have an exact number and location match in the previous condition, compare it against each element of the computerGenerated random list to find a number which is missplaced. However, the issue is that it will run the comparison against every element which means if the previous condition, in this case index[0], will be compared again, even though, that already had a match.

      if (userGuess[i] == computerGenerated[j]) {
        - to prevent this issue, we can added another condition such as index of i != index of j.
        if (i != j) {
          [1,1,3,4]
          [1,3,2,4]
  index -> 0,1,2,3
          if (i=0) != (j=0)
          - focus here, indice in these two for-loops don't change. i start at 0, and j starts = 0, so the locations the same.
          correctNumberInWrongLocation++, is true. 
        }
      }
     }
    }
  }

```

Even though the last else condition and loop looks promising there are some test cases that it fails. Let's look at the following arrays.

```
    computerGenerated = [6,6,1,1]
    userGuess = [1,2,3,4]
    outer-for-loop: -> false, false, false, false. No number found at the correct address and location.
    else
    inner-for-loop: -> false, false, true, true. 1 != 6, 1 != 6, 1 == 1 and i != j, 1 == 1 and i != j.
                  : -> false, false, false, false, 2 != elementsInList. Or list.doesn'tContain(2);
                  : -> false, false, false, false, 3 != elementsInList. Or list.doesn'tContain(3);
                  : -> false, false, false, false, 4 != elementsInList. Or list.doesn'tContain(4);

      result => correctNumberInWrongLocation: 2, wrongNumber: 2. 

      In fact, the result should have been 1 correctNumberInWrongLocation, and 3 wrongNumbers. Because 2,3, and 4 doesn't exist in the computerGenerated numbers.


    computerGenerated = [2,1,1,0]
    userGues = [2,0,1,0]
    
    Output: correctNumberAtCorrectLocation: 2, 1, 0.
          : correctNumberInWrongLocation: ,
          : wrongNumber: 0
    - The extra 0 in the user's input no longer exist because exact value and index location had precedence over others. Therefore, the result = 3 correct, one wrong, zero correctNumberInWrongLocation
  
```

As it can be seen the duplicates create lots of issue. To understand this game, I bought a Mastermind game with 4 pegs. I played with my kids and kept thinking about solving the duplicates. My current solution is not good, but it does the job. Here is the patter, After finding the matched numbers, store their indices and replace the value with something out of the domain range. To void modifying the array, copy both elements in a new array and keep replacing the matched values with a new value. My other thinking was to create a new string, and concatenate the unwatched values and their indices and keep remove the matched values until no match is left. It looked pretty ugly when I did on the whiteboard. I ultimately settled with a solution which will make me happy that it does the job, but makes me feel bad, because the time and space complexity looks horrible.

```
        int[] tmpArrayResult = new int[difficultyLevelValue];
        int guessedCorrectAndNumberLocation = 0;
        int guessedCorrectNumber = 0;
        int incorrectGuess = 0;
        - difficultyLevelValue, determins the size of the array at the initial instantiantion.

-> Below two lines are holding values which are not the domain of the assignment. Therefore, when there is a value that matches both arrays, that index's value changes to either -1, or -100 at start, then keeps decrementing to avoid matches. 
        
        int exactMatchedFoundInBothArrays = -1;
        int matched = -100;
      
-> To avoid modifying the arrays, below code copies them into two new arrays.

        int[] tmpComputerGeneratedCode = new int[difficultyLevelValue];
        int[] tmpUserGuess = new int[difficultyLevelValue];
-> Even though one for-loop does the job, I kept two, but only one suffice.

        for (int i = 0; i < difficultyLevelValue; i++) {
            tmpComputerGeneratedCode[i] = computerGeneratedNumbers[i];
        }
        for (int i = 0; i < difficultyLevelValue.length; i++) {
            tmpUserGuess[i] = userGuess[i];
        }
-> The first double for-loop compares two indices and the value of i and j. then increments the correctNumberAtCorrectLocation and modifies the values in the temporaryArray and repalces the match value with -1. On each match it decrements to avoid comparison with the same value. It will look like this -> [-1, -2, -3, 2].
        for(int i = 0; i < difficultyLevelValue; i++){
            for(int j = 0; j < difficultyLevelValue; j++){
                if(tmpUserGuess[i] == tmpComputerGeneratedCode[j] && i == j){
                    guessedCorrectAndNumberLocation++;
                    tmpUserGuess[i] = exactMatchedFoundInBothArrays;
                    tmpComputerGeneratedCode[j] = exactMatchedFoundInBothArrays; // -1
                    exactMatchedFoundInBothArrays--;
                }
            }
        }
-> The double for-loop looks for a value match with different indices. Once it finds then it modifies the temporary array and starts replacing it with -100. The reason for -100, that a user can only attemp 10 times, if it was -10, on the tenth attemp there will be a -10, duplicates from the previous case. To void overlaping, the correctNumberInWrongLocation will start replacing with -100. for example -> [-1, -100, -101...]
        
        for(int i = 0; i < difficultyLevelValue; i++){ // 4
            for(int j = 0; j < difficultyLevelValue; j++){
                if(tmpUserGuess[i] == tmpComputerGeneratedCode[j] && i != j){

                    guessedCorrectNumber++;
                    tmpUserGuess[i] = matched;
                    tmpComputerGeneratedCode[j] = matched; //-100
                    matched--;
                }
            }
        }

-> I found no need to look for wrongNumber because adding guessedCorrectAndNumberLocation and guessedCorrectNumber then subtracting the userGues.length will give the wrong numbers.  

        incorrectGuess = userGuess.length-(guessedCorrectAndNumberLocation+guessedCorrectNumber);

-> One other way of solving this will inconvient for the player but easier for coder is to ask the user for each element one at a time. 
computer: -> "Please enter a digit to location/index 1."
user:-> 6
computer: -> "Please enter a digit to location/index 2."
user:-> 3
....
if this method, provides the opportunity compare the correct values, but still the challenge of duplicates will be there at some extent.
```

I believe this part of the challenge is the hardest part. Other requirements are not too challenging.

### Planing for allowing user to see their guesses and feedbacks.
Available options:
> - Write user's communication to a text file. At th end read from it.
> - Write it in RAM memory then display the result.
> - Write/read to a database, so it stays there.

Easiest choice is to write in RAM and read it. The following object can be used to achieve this gaol.
Two Dimension Array:

  ```
    String[][] result = [{"john"},{"input"},{"computerGenerated"}];
  ```
The good thing about 2D array is that saving is at a constant log(1) time. Here is why:

  ```
    result[0][0] = userGuess;
    result[0][1] = userGuess;
    result[0][2] = userGuess;

    result[1][0] = userGuess; 
    result[1][1] = userGuess;
    result[1][2] = userGuess;

  ```

Since a counter/numberOfAttempts is used here to keep track of the attempts, it can also serve as an index location.
counter = 0, and ...10;

The other option is to create a HashMap<Integer, String> and store it like this ->

  ```
  {1: "John, you guess, [1,2,3,4] and feedback was 1 correct, 2 incorect and 1 wrong." }
  ```  
Perhaps the better choice is to use a database. Thus, I used both a database and the array. The array will show the result for the current player until the players decides to replay() which will clear the arrays and start from 0. However, the database keep a record of all players who played the game. Due to lack of initial planing, I didn't use relationship which could have been better. For instance, A player can have many games (result, feedbacks) or many games belongs to a player. In this model, whenever a player enters his/her name, the application can find and present the history.
The current implementation only stores the guessedValues, winHistory and userName. Duplicate names and null values are allowed.
A player must play first then will get his result. A function to allow a player to display all the records hasn't been implemented, but all it needs to add a function at the start of the game which asks "Enter play to play the game, or history to see yours and others record".

### Random.org, API vs get(url)
random.org has upgraded their system and provides free APIs up 1000/month request for certain services. Therefore, using their API is a better choice rather than submitting a get-request to

  ```
  https://www.random.org/integers/?num=8&min=0&max=7&col=1&base=10&format=plain&rnd=new
  ```
The reason for not choosing this method is the amount of work required to convert the return text with lines to an integer array. For example,

  ```
  6
  6
  7
  6
  4
  0
  1
  1
  ```
With this result, the choices are to use regex in order to create an array without the "\n" or use a HashMap<number, "\n">  then push the keys into a new array. Thus, API is a better choice for this operation.

If you do want to use the above link directly, below is the code ->

  ```
  for (...)
    Character.isDigit(randomIntegers.charAt(i)))
  
  Link: => [ https://docs.oracle.com/javase/7/docs/api/java/lang/Character.html ]

- Use regex
Link: => [https://www.oracle.com/technical-resources/articles/java/regex.html]

  ```

# Attempted extra features.

The following features were attempted but not built:

- Frontend REACT, backend Nodejs+express+MySQL
- Inquirer plus Nodejs+express+MySQL
- Basic CSS, HTML and JavaScript
- One to many relationship

# Conclusion
There are couple lessons I learned by doing this take home challenge:

- I should have paraphrased the challenge and asked the recruiter to confirm my understandings. This would have helped to focus on the challenge rather than assumptions.
- I should have decided to build the core features first in one of the languages such as Java or JavaScript that I feel comfortable.
- I should have avoided trying to build a full stack App such as using Ruby on Rails as a backend or using Nodejs + express + MySQL, and REACT as a frontend.
- I should have analyzed my hours because I spend 9 hours at work, and other couple hours for taking care of my children and family needs.
- Documentation took longer than I expected. It is crucial to plan and reserve enough hours for documenting your work.
- I shouldn't have hesitated to contact my recruiter when I needed clarification.

Since this was my first take home challenge, I sometimes spent lots of time on deciding what I should use in terms of technology. Will just a web-page with css, html, and JavaScript suffice? Will Nodejs + MySQL + React will be a good choice? I started doing some of each, then revisited my notes and saw that the recruiter had emphasized to use a technology based on the job you applied. At that moment, I realized that I wasted sometime. Immediately, I started planing on using Java. The first thing I thought was to build a spring-boot API, but I noticed that a player can't interact directly with the API without a prior knowledge of Postman, Insomnia and other API testing applications. At the end, I thought a CLI application should be enough, if I have time left then will take it further. Once the application was built, then wondered should I save the history into a file or a database, and finally decided to use MySQL because it runs on MacOS, Microsoft Windows and Linux. This challenge thought me that best way to learn is to build. It only requires patience and guidance.
