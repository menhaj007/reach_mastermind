package main;
import java.util.*;
/**
 * Core requirement of MasterMind is implemented in this class.
 *
 * @see ConnectToMySQL has the jdbc driver to establish connection with MySQL.
 * @see GameRunner includes the "main" method to run the application
 *
 * @see #start()
 * @see #play()
 * @see #readUserInput()
 * @see #calculate(int[] computerGeneratedNumbers, int[] userGuess)
 * @see #result(int counter)
 * @see #isPlayAgain(String response)
 * @see #resetHistory()
 * @see #getGuessHistory() ()
 * @see #getFeedbackFromArray() 
 *
 */
public class MasterMind {
    /**
     * No-argument constructor.
     */
    public MasterMind() {}
    /**
     * Accepts user input in command line.
     * @Field #userInput built-in library which accepts userInput.
     *
     * @see #start()
     * @see #play()
     */
    Scanner userInput = new Scanner(System.in);
    /** @Field #computerPoints at the beginning each has 10 */
    double computerPoints = 10;
    /** #Field #userPoints if user guesses wrong numbers points will be deducted.*/
    double userPoints = 10;
    /** @Field #difficultyLevelValue this variable defines the number of random generated values. Default is 4.*/
    private  int difficultyLevelValue = 4;
    /**@Field #numberOfAttemps if a user can't win in 10 attempts, game will stop.*/
    int numberOfAttempts = 10;
    /**
     * It generates a 2D array to store the computer's generated feedback for later access.
     *
     * @see #saveFeedbackToArray(int, String, String, String)
     * @see #getFeedbackFromArray()
     */
    String[][] computerFeedback = new String[10][3];
    /**
     * It generates a 2D array to store a user's entered guess as history of inputs.
     *
     * @see #saveGuessHistory(String)
     * @see #getGuessHistory()
     */
    String[][] guessHistory = new String[10][2];
    /**
     * @Field #numberOfSavedFeedback increments the index location to keep track of the inserted elements
     * to avoid unnecessary loop for empty/null elements in the array.
     */
    int numberOfSavedFeedback = 0;
    /** @Field #numberOfSavedGuessedHistory increments the index location to keep track of addition to the array */
    int numberOfSavedGuessedHistory = 0;
    /**
     * @Field #userName when the game starts, it records for the player's name to save it in the database with the result.
     * It only changes when console re-runs.
     */
    String userName = "";
    /**
     * @Field #userId the intention was to build a one to many relationship for mysql and use UserId to connect them.
     * But due to the time constrains, it is not implemented.
     */
    /** @Field #isWin is used to keep track of the game, if a player wins then it becomes false and loop stops. */
    boolean isWin = false;
    /**
     * Starts the game by asking the user's name and saves it at global scope, then calls play() method to start the actual logic.
     * @see #play()
     */
    public void start() {
        System.out.println("Please enter your name. ex: James");
        userName = userInput.next();
        play();
    }
    /**
     * Play starts the initial core logic of the game. It asks for user's input, generates random numbers, calls
     * the calculate(int[] a, int[] b) method and change the value of iSWin and numberOfAttempts.
     *
     * @see #calculate(int[], int[])
     * @see #isWin
     * @see #numberOfAttempts
     * @see #isPlayAgain(String)
     * @see #result(int)
     * @see #getGuessHistory()
     */
    public void play() {
        System.out.println("Please choose your difficulty level.");
        chooseDifficultyLevel();
        System.out.println("Computer generating a random number");
        int[] computerGeneratedNumbers = generateRandomNumbers();
        /** Below print is used for dev testing only */
        System.out.println("Computer generated " + Arrays.toString(computerGeneratedNumbers));
//        Countdown.startCountdown();
        int counter = 0;
        while (!isWin && numberOfAttempts > 0) {
            System.out.println("You have " + numberOfAttempts + " attempts left.");
            System.out.println("Please enter your solution to crack the code ex 1234 -> " );
            int[] userGuess = readUserInput();
            int size = calculate(computerGeneratedNumbers, userGuess);
            if (size == userGuess.length) {
                isWin = true;
                System.out.println(result(counter));
                numberOfAttempts = 10;
                System.out.println("Would like to play again? Yes, No, or type history.");
                String checkResponse = userInput.next();
                isPlayAgain(checkResponse);
            }
            getFeedbackFromArray();
            if (counter > 3) {
                hint(computerGeneratedNumbers);
            }
            numberOfAttempts--;
            counter++;
        }
        getGuessHistory();
    }
    /**
     * chooseDifficultyLevel method asks the user for difficulty level then it sets the RandomGenerator's API
     * to generate one of the followings. 1 will generate an array with 4 random values. 2 will generate 6 elements and
     * 3 will generate 8 elements. It also decreases the number attempts from 10 to 8, 6 and 5. It makes it harder.
     *
     * @see #generateRandomNumbers()
     * @see #difficultyLevelValue
     */
    public void chooseDifficultyLevel() {
        boolean valid = false;
        do {
            System.out.println("Please select the difficulty level. \n" +
                    "1 for easy (4 digits)\n" +
                    "2 for medium (6 digits)\n" +
                    "3 for difficult (8 digits).");
            int userChoice = userInput.nextInt();
            if (userChoice == 1) {
                difficultyLevelValue = 4;
                valid = true;
            } else if (userChoice == 2) {
                difficultyLevelValue = 6;
                numberOfAttempts = 8;
                valid = true;
            } else if (userChoice == 3) {
                difficultyLevelValue = 8;
                numberOfAttempts = 5;
                valid = true;
            } else {
                System.out.println("Invalid value, please try again");
            }
        } while (!valid);
    }
    /** It makes a get request to generate random numbers. The size of the request is impacted by
     * @see #chooseDifficultyLevel()
     * */
    public int[] generateRandomNumbers() {
        int n = 4, max = 7, min = 0;
        boolean replacement = false;
        String method = "method1";
        if ( difficultyLevelValue == 6) {
            n = 6;
            max = 8;
        } else if (difficultyLevelValue == 8) {
            n = 8;
            max = 9;
        }
        return RandomIntegerGenerator.getRandomNumber(n, max, min, replacement, method);
    }
    /**
     * ReadeUserInput accepts user's input then validates. If the number is wrong, the allows the user to re-enter.
     *
     * @return #convertStringToArrayOfInteger(String userGuess) first converts the user's input then returns an array of integers.
     * @see #convertStringToArrayOfInteger(String)
     */
    public int[] readUserInput() {
        String userGuess = userInput.next();
        while (!isValid(userGuess)) {
            System.out.println("Please enter a valid number");
            userGuess = userInput.next();
        }
        saveGuessHistory(userGuess);
        return convertStringToArrayOfInteger(userGuess);
    }
    /**
     * isValid accepts a string input then converts to integer, and it also validates the domain for the input values.
     * If digits are less 0 (negative) and greater than (9), return false, and if userInput's guess length doesn't match
     * the computer the chosen difficulty level, it returns false.
     * @param userGuess is a string value entered by the user as an input.
     * @return if length and values are not valid, it returns false.
     */
    private boolean isValid(String userGuess) {
        //if(userGuess.length() == difficultyLevelValue) return false;
        for (int i = 0; i < userGuess.length(); i++) {
            if (Character.getNumericValue(userGuess.charAt(i)) < 0 || Character.getNumericValue(userGuess.charAt(i)) > 9) {
                return false;
            }
        }
        return userGuess.length() == difficultyLevelValue;
    }
    /**
     * ConvertStringToArrayOfInteger accepts string and returns an array of integers.
     * @param data userInput
     * @return a new array.
     */
    private int[] convertStringToArrayOfInteger(String data) {
        int[] tmp = new int[data.length()];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = Character.getNumericValue(data.charAt(i));
        }
        return tmp;
    }
    /**
     * Calculate accepts two arrays, one randomNumbers generated by the computer and the converted userGuess. It then compares both by indices.
     * If exact value matches an exact index, then it increments @Field #guessedCorrectAndNumberLocation,
     * it only increments when two values at exact indices match in the two arrays.
     *
     * If the first case fails, then it tries to find a exact number in the randomGenerated list.
     * It increments @Field #guessedCorrectNumber, when a value doesn't have an exact position match but still exist in the list.
     *
     * Exact digit and index position has precedence over others. The first match has precedence over second math.
     * It is only match with one value, if there are two {1,1}, {1,2}, only the first correct match increments.
     *
     * @param #computerGeneratedNumbers
     * @param #userGuess
     * @return integer correct guess with correct index location which determines if a player wins/loses the game.
     * @see #play()
     * @see #getGuessHistory() ()
     * @see #getFeedbackFromArray() ()
     * @see #saveFeedbackToArray(int, String, String, String) ()
     * @Field #guessedCorrectAndNumberLocation keeps track of the correct number and its matched index location.
     * @Field #guessedCorrectNumber keep track of a number which exist both user's input and computer's random generated number
     * @Field #incorrectGuess if the first two conditions are false, then it is true. userGuess.length - (guessedCorrectAndNumberLocation+ guessedCorrectNumber)
     * @Field #exactMatchedFoundInBothArrays
     * @Field #mathed a user's input element matched with an element in the computer generated list.
     * @Field #tmpComputerGeneratedCode copies the data from the original computer generated to modified it when iterating.
     * @Field #tmpUserGuess copies the data from the original user's input to modified it when iterating.
     * @Field #objResult copies the digits into the database for later use.
     * @see #writeFeedbackIntoDB(HashMap)
     * @see #saveFeedbackToArray(int, String, String, String) () writes in the RAM memory.
     * @see #writeInputHistoryIntoDB(String, String) ()
     */
    public int calculate(int[] computerGeneratedNumbers, int[] userGuess) {
        int[] tmpArrayResult = new int[difficultyLevelValue];
        int guessedCorrectAndNumberLocation = 0;
        int guessedCorrectNumber = 0;
        int incorrectGuess = 0;
        int exactMatchedFoundInBothArrays = -1;
        int matched = -100;
        int[] tmpComputerGeneratedCode = new int[difficultyLevelValue];
        int[] tmpUserGuess = new int[difficultyLevelValue];
        for (int i = 0; i < tmpUserGuess.length; i++) {
            tmpComputerGeneratedCode[i] = computerGeneratedNumbers[i];
        }
        for (int i = 0; i < tmpUserGuess.length; i++) {
            tmpUserGuess[i] = userGuess[i];
        }
        for(int i = 0; i < userGuess.length; i++){
            for(int j = 0; j < userGuess.length; j++){
                if(tmpUserGuess[i] == tmpComputerGeneratedCode[j] && i == j){
                    guessedCorrectAndNumberLocation++;
                    tmpUserGuess[i] = exactMatchedFoundInBothArrays;
                    tmpComputerGeneratedCode[j] = exactMatchedFoundInBothArrays; // -1
                    exactMatchedFoundInBothArrays--;
                }
            }
        }
        for(int i = 0; i < userGuess.length; i++){ // 4
            for(int j = 0; j < userGuess.length; j++){
                if(tmpUserGuess[i] == tmpComputerGeneratedCode[j] && i != j){
                    guessedCorrectNumber++;
                    tmpUserGuess[i] = matched;
                    tmpComputerGeneratedCode[j] = matched;
                    matched--;
                }
            }
        }
        incorrectGuess = userGuess.length-(guessedCorrectAndNumberLocation+guessedCorrectNumber);
        String correctResult = "Correct number & location: " + guessedCorrectAndNumberLocation;
        String incorrectResult = "Correct number only: " + guessedCorrectNumber;
        String incorrectGuessResult = "Incorrect: " + incorrectGuess;
        HashMap<String, Integer> objResult = new HashMap<String, Integer>();
        objResult.put("correctNumberLocation", guessedCorrectAndNumberLocation);
        objResult.put("correctNumberOnly", guessedCorrectNumber);
        objResult.put("incorrectGuess", incorrectGuess);
        writeFeedbackIntoDB(objResult);
        saveFeedbackToArray(numberOfSavedFeedback,correctResult, incorrectResult, incorrectGuessResult);
        String tmpGuess = Arrays.toString(userGuess);
        String tmpFeedback = correctResult +" | " + incorrectGuessResult +" | " + incorrectGuessResult;
        writeFeedbackGuessToDB(userName, tmpFeedback, tmpGuess);
        System.out.println("Printing... You guessed -> " + Arrays.toString(userGuess));
        return guessedCorrectAndNumberLocation;
    }
    /**
     * Result displays the number of attempts it took the player to win.
     * @param counter just keep record of the while loop.
     * @return "You Won! in " + (counter) + " attempts";
     */
    public String result(int counter) {
        return "You Won! in " + (counter) + " attempts";
    }
    /**
     * IsPlayAgain asks the user to make a decision after winning or losing the game. The choices are:
     * Do you want to play again?
     * Do you want to check the history for input and feedback output?
     * It also retrieves the history from the database where it displays the result and feedback from other players.
     * @param checkResponse pass three strings 1. yes, 2. history, 3. no. Based on these the method decides where to lead
     *                      the user.
     * @see #play()
     */
    private void isPlayAgain(String checkResponse) {
        boolean answer = false;
        if (checkResponse.equalsIgnoreCase("yes")) {
            numberOfAttempts = 10;
            answer = true;
            resetHistory();
            play();
        } else if (checkResponse.equalsIgnoreCase("history")) {
            System.out.println("Below is your history of the players");
            readFeedbackFromDB();//database user_result
            getGuessHistory();
            //read from dB
            readFeedbackGuessFromDB();
            System.out.println("What would you like to do now? Yes -> play again. No -> exit.");
            String checkAgain = userInput.next();
            if (checkAgain.equalsIgnoreCase("yes"))
                isPlayAgain(checkAgain);
            else {
                System.out.println("Bye...");
                System.exit(0);
            }
        } else if (checkResponse.equalsIgnoreCase("no")) {
            System.out.println("Bye bye, thanks for playing.");
            System.out.println("Below is your game history and feedbacks.");
            getGuessHistory();
            getFeedbackFromArray();
            System.exit(0);
        } else {
            System.out.println("Unknown entry, shutting down...");
            System.exit(0);
        }
    }
    /**
     * Hint triggers when a play can't win in 3 guesses, then it gives a feedback for the minimum and maximum values
     * to help the user.
     * @param array accepts an array and returns the min and max values.
     * @return an array with two indices a[0] = min, a[1] = max. The only purpose for this return is to test the function.
     */
    private int[] hint(int[] array) {
        int[] tmpArr = new int[2];
        int maximum = array[0];
        int minimum = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < minimum)
                minimum = array[i];
        }
        for (int i = 0; i < array.length; i++) {
            if (array[i] > maximum)
                maximum = array[i];
        }
        System.out.println("The value is between " + minimum + " and " + maximum);
        tmpArr[0] = minimum;
        tmpArr[1] = maximum;
        return tmpArr;
    }
    /**
     * ResetHistory, resets the history when a player wins then tries to play again, therefore, the following variables are
     * affected with this method.
     * @Field #numberOfAttempts
     * @Field #numberOfSavedFeedback
     * @Field #numberOfSavedHistory
     * @Field #isWin
     * @Field #computerFeedback
     * @Field #guessHistory
     *
     * @see #play()
     * @see #isPlayAgain(String)
     */
    private void resetHistory() {
        numberOfAttempts = 10;
        numberOfSavedFeedback = 0;
        numberOfSavedGuessedHistory = 0;
        isWin = false;
        computerFeedback = new String[10][3];
        guessHistory = new String[10][2];
    }
    /**
     * SaveFeedbackToArray Saves feedback in the RAM to display it back to the user.
     * @param index
     * @param correctNumLoc
     * @param incorrectNum
     * @param incorrectEntry
     * @return returns a string of array for testing purpose.
     */
    private String[] saveFeedbackToArray(int index, String correctNumLoc, String incorrectNum, String incorrectEntry) {
        System.out.println("Saving the result.");
        computerFeedback[index][0] = correctNumLoc;
        computerFeedback[index][1] = incorrectNum;
        computerFeedback[index][2] = incorrectEntry;
        numberOfSavedFeedback++;
        String[] guessedResult = new String[3];
        guessedResult[0] = correctNumLoc;
        guessedResult[1] = incorrectNum;
        guessedResult[2] = incorrectEntry;
        return guessedResult;
    }
    /**
     * GetFeedbackFromArray reads feedback from RAM for display purpose only. If game stops, the data is lost.
     * @return
     */
    private int getFeedbackFromArray() {
        int numberOfElements = 0;
        for (String[] str: computerFeedback) {
            for(String value: str) {
                if(value == null) {
                    System.out.print("--");
                    System.out.print("\t");
                } else {
                    numberOfElements++;
                    System.out.print(value);
                    System.out.print("\t");
                }
            }
            if (str.length > 0)
                System.out.println();
        }
        return numberOfElements;
    }
    /**
     * SaveGuessHistory saves the accepted userInput to display it later. It saves in the 2D array.
     * @param guess accepts a string value of userInput to add it into 2D array.
     */
    private void saveGuessHistory(String guess) {
        guessHistory[numberOfSavedGuessedHistory][0] = userName;
        guessHistory[numberOfSavedGuessedHistory][1] = guess;
        numberOfSavedGuessedHistory++;
    }
    /**
     * GetGuessHistory reads the saved data from RAM.
     *
     * @see #saveGuessHistory(String)
     */
    public void getGuessHistory() {
        for (int i = 0; i < numberOfSavedGuessedHistory; i++) {
            System.out.println(guessHistory[i][0] + ", your guessed history: " + (i+1) + "  "  + guessHistory[i][1]);
        }
    }
    /**
     * The following methods are used to communicate with an installed instance of MySQL.
     * The main class for MySQL is created in a separate java file called ConnectToMySQL, but it is located in the same
     * package. This class establishes connection with mysql database then creates/reads the data.
     *
     * @see #Class#ConnecToMySQL
     *
     */
    /**
     * WriteInputHistoryIntoDB saves @Field #userName and @Field #userGuess into mysql's database.
     * @param userName accepts userName as a string value
     * @param guess accepts userInput guess as a string value
     *
     * Ref: ConnectToMySQL#writinputHistoryIntoDB
     */
    private void writeInputHistoryIntoDB(String userName, String guess) {
        ConnectToMySQL.writeInputHistoryIntoDB(userName, guess);
    }
    /**
     * ReadUserInputHistoryFromDB read data from mysql database.
     * @see #writeInputHistoryIntoDB(String, String) ()
     */
    private void readUserInputHistoryFromDB(){
        ConnectToMySQL.readUserInputHistoryFromDB();
    }
    /**
     * Reads saved feedbacks from MySQL. Check class ConnectToMySQL for the implementation.
     */
    private void readFeedbackFromDB() {
        ConnectToMySQL.readFeedback();
    }
    /**
     * Writes Feedback into the table as the integer values only, so a play can compute it later.
     * @param feedback consist of the 3 variables and userName.
     *
     * @see #play()
     */
    public void writeFeedbackIntoDB(HashMap<String, Integer> feedback) {
        ConnectToMySQL.writeFeedbackIntoDB(feedback, userName);
    }
    /**
     * WriteFeedbackGuessToDB writes userName, feedback and guess as string values for easy read.
     * @param userName
     * @param feedback
     * @param guess
     */
    private void writeFeedbackGuessToDB(String userName, String feedback, String guess) {
        ConnectToMySQL.writeFeedbackGuessToDB(userName, feedback, guess);
    }
    /**
     * Prints username, feedback and guess values form the table.
     * Ref: ConnectToMySQL#commentedSQLCommands for table names and structure.
     */
    private void readFeedbackGuessFromDB() {
        ConnectToMySQL.readFeedbackGuessFromDB();
    }
}