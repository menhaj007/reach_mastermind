package main;

import java.util.*;

/**
 * Core requirement of MasterMind is implemented in this class.
 *
 * @see #start()
 * @See #play()
 * @See #userInput()
 * @See #calculate()
 * @See #calulate()
 * @See #result()
 * @See #isPlayAgain()
 * @See #restHistory()
 * @See #getFeedback()
 * @See #getHistory()
 *
 */
public class MasterMind {

    /**
     * No-argument constructor.
     */
    public MasterMind() {}

    /**
     * Accepts user input in command line.
     *
     * @See #start()
     * @See #play()
     */
    Scanner userInput = new Scanner(System.in);

    double computerPoints = 10;
    double userPoints = 10; //need think about calculating this.
    private  int difficultyLevelValue = 4;

    /**@Field #numberOfAttemps if a user can't win in 10 attempts, game will stop.*/
    int numberOfAttempts = 10;

    /**
     * It generates a 2D array to store the computer's generated feedback for later access.
     *
     * @See #saveFeedback()
     * @See #getFeedback()
     */
    String[][] computerFeedback = new String[10][3]; // add another table in mysql to save this.
    /**
     * It generates a 2D array to store a user's entered guess as history of inputs.
     *
     * @See #saveGuessHistory()
     * @See #getHistory()
     */
    String[][] guessHistory = new String[10][2]; //edit this line to add userName and History.
    /**
     * @Field @numberOfSavedBack increments the index location to keep track of the inserted elements
     * to avoid unnecessary loop for empty/null elements in the array.
     */
    int numberOfSavedFeedback = 0;
    int numberOfSavedHistory = 0;
    /**
     * @Field #userName when the game starts, it records for the player's name to save it in the database with the result.
     * It only changes when console re-runs.
     */
    String userName = "";
    /**
     * @Field #userId the intention was to build a one to many relationship for mysql and use UserId to connect them.
     * But due to the time constrains, it is not implemented.
     */
    //int userId = 0;
    /**@Field #isWin is used to keep track of the game, if a player wins then it becomes false and loop stops. */
    boolean isWin = false;
    //generate random numbers and store.
    /**
     * Start asks the user's name and saves it at global scope, then call play() method to start the actual logic.
     * @See #play()
     */
    public void start() {
        System.out.println("Please enter your name. ex: James");
        userName = userInput.next();
        play();
    }

    /**
     * Play starts to initial the core logic of the game. It asks for user's input, generates random numbers, call the
     * calculate method and change the value of iSWin and numberOfAttempts.
     *
     * @See #calculate()
     * @See #isWin
     * @See #numberOfAttempts
     * @See #isPlayAgain()
     * @See #result()
     * @See #getHistory()
     */
    public void play() {
        System.out.println("Please choose your difficulty level.");
        chooseDifficultyLevel();
        System.out.println("Computer generating a random number");
        int[] computerGeneratedNumbers = generateRandomNumbers();
        System.out.println("Computer generated " + Arrays.toString(computerGeneratedNumbers)); //remove it before final
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

            getFeedback();
            if (counter > 3) {
                hint(computerGeneratedNumbers);
            }
            numberOfAttempts--;
            counter++;
        }
        getHistory();
    }

    /**
     * chooseDifficultyLevel method asks the user for difficulty level then it sets the RandomGenerator's API
     * to generate one of the followings. 1 will generate an array with 4 random values. 2 will generate 6 elements and
     * 3 will generate 8 elements. It also decreses the number attempts from 10 to 8, 6 and 5. It makes it harder.
     *
     * @See #generateRandomNumbers()
     * @See #difficultyLevelValue
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
     * @See #chooseDifficultyLevel()
     * */
    public int[] generateRandomNumbers() {
        int tmpN = 4, max = 7, min = 0;
        boolean tmpReplacement = false;
        String tmpMethod = "method1";
        if (difficultyLevelValue == 4) { // No need for this line.
            tmpN = 4; max = 7; min = 0; tmpReplacement = false;
            numberOfAttempts = 10;
        } else if ( difficultyLevelValue == 6) {
            tmpN = 6;
            max = 8; min = 0; tmpReplacement = false;
            numberOfAttempts = 8;
        } else if (difficultyLevelValue == 8) {
            tmpN = 8;
            max = 9; min = 0; tmpReplacement = false;
            numberOfAttempts = 6;
        }
        int[] defaultRandomRange = null;
        if (difficultyLevelValue == 4) {
            defaultRandomRange = new int[4];
            for (int i = 0; i < defaultRandomRange.length; i++) {
                defaultRandomRange[i] = (int) (Math.random()* 7);
            }
            return defaultRandomRange;
        } else if (difficultyLevelValue == 6) {
            defaultRandomRange = new int[6];
            for (int i = 0; i < defaultRandomRange.length; i++) {
                defaultRandomRange[i] = (int) (Math.random()* 8);
            }
            return defaultRandomRange;
        } else if (difficultyLevelValue == 8) {
            defaultRandomRange = new int[8];
            for (int i = 0; i < defaultRandomRange.length; i++) {
                defaultRandomRange[i] = (int) (Math.random()* 9);
            }
            return defaultRandomRange;
        } else {
            defaultRandomRange = new int[4];
            for (int i = 0; i < defaultRandomRange.length; i++) {
                defaultRandomRange[i] = (int) (Math.random()* 7);
            }
            return defaultRandomRange;
        }
//        return RandomIntegerGenerator.getRandomNumber(tmpN, max, min, tmpReplacement, tmpMethod);
    }

    /**
     * ReadeUserInput accepts user's input then validates. If the number is wrong, the allows the user to re-enter.
     * @return #convertStringToArrayOfInteger(String userGuess) it returns an array of integers.
     * @See #convertStringToArrayOfInteger()
     */
    public int[] readUserInput() {
        String userGuess = userInput.next();
        while (!isValid(userGuess)) {
            System.out.println("Please enter a valid number");
            userGuess = userInput.next();
        }
        saveGuessHistory(userGuess);
        return convertStringToArrayOfInteger(userGuess); // it is referencing the object. Which can cause problems later.
    }
    /**
     * isValid accepts a string input then converts to integer, and it also validates the domain for the input values.
     * If digits are less 0 (negative) and greater than (9), return false, and if userInput's guess length doesn't match
     * the computer the chosen difficulty level, it returns false.
     * @param userGuess
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
     * @param computerGeneratedNumbers
     * @param userGuess
     * @return integer correct guess with correct index location which determines if a player wins/loses the game.
     * @See #play()
     * @See #getHistory()
     * @See #getFeedback()
     * @See #saveToDB()
     */
    public int calculate(int[] computerGeneratedNumbers, int[] userGuess) {
        int[] tmpArrayResult = new int[difficultyLevelValue];
        int guessedCorrectAndNumberLocation = 0;
        int guessedCorrectNumber = 0;
        int incorrectGuess = 0;
        int matchedBoth = -1; // it is for tmp array. make sure it doesn't overlap with matched = -10
        int matched = -100; // not changing.
        //Creating arrays to keep track of matched values.
        int[] tmpComputerGeneratedCode = new int[difficultyLevelValue];
        int[] tmpUserGuess = new int[difficultyLevelValue];

        //merge the two.
        for (int i = 0; i < tmpUserGuess.length; i++) {
            tmpComputerGeneratedCode[i] = computerGeneratedNumbers[i];
        }
        for (int i = 0; i < tmpUserGuess.length; i++) {
            tmpUserGuess[i] = userGuess[i];
        }
        for(int i = 0; i < userGuess.length; i++){ //Outer loop is the user guess.
            for(int j = 0; j < userGuess.length; j++){ //Inner loop is computer SecCode.
                if(tmpUserGuess[i] == tmpComputerGeneratedCode[j] && i == j){ // For each user input at index[i] loop through the entire secCode
                    // to find a match. i == j, on Both location and value
                    guessedCorrectAndNumberLocation++;
//                        System.out.println("Correct on both : " + userGuess[i] + "=" +  computerGeneratedNumbers[j]);
                    tmpUserGuess[i] = matchedBoth; //-1, decrement to not find the match. There is no negative because domain. user[i] > -1.
                    tmpComputerGeneratedCode[j] = matchedBoth; // -1
                    matchedBoth--; // decrement to -2, to avoid positive matches.
                }
            }
        }
        //I am select each element of userInput and comparing it against the entire elements of SecCode.
        for(int i = 0; i < userGuess.length; i++){ // 4
            for(int j = 0; j < userGuess.length; j++){
                if(tmpUserGuess[i] == tmpComputerGeneratedCode[j] && i != j){ //computer userGuess to secCode. On unmatched position.
                    guessedCorrectNumber++;
//                        System.out.println("Incorrect position : " + userGuess[i] + "=" +  computerGeneratedNumbers[j]);
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
        saveFeedback(numberOfSavedFeedback,correctResult, incorrectResult, incorrectGuessResult);
        //saveFeedbackToMySQL
        //saveToDatabase
        String tmpGuess = Arrays.toString(userGuess);
        String tmpFeedback = correctResult +" | " + incorrectGuessResult +" | " + incorrectGuessResult;
        writeFeedbackGuessToDB(userName, tmpFeedback, tmpGuess);
        System.out.println("Printing... You guessed -> " + Arrays.toString(userGuess));
        return guessedCorrectAndNumberLocation;
    }

    /**
     * Result displays the number of attempts it took the player to win.
     * @param counter
     * @return
     */
    public String result(int counter) {
        return "You Won! in " + (counter) + " attempts";
    }

    /**
     * IsPlayAgain asks the user to make a decision after winning or losing the game. The choices are
     * Do you want to play again?
     * Do you want to check the history for input and feedback output?
     * It also retrieves the history from the database where it displays the result and feedback from other players.
     * @param checkResponse pass three strings 1. yes, 2. history, 3. no. Based on these the method decides where to lead
     *                      the user.
     * @See @play().checkResponse()
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
            readFeedback();//database user_result
            getHistory();
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
            getHistory();
            getFeedback();
            System.exit(0);
        } else {
            System.out.println("Unknown entry, shutting down...");
            System.exit(0);
        }
    }

    /**
     * Hint triggers when a play can't win in 3 guesses, then it gives a feedback for the minimum and maximum values
     * to help the user.
     * @param array
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
     * Reset History, resets the history when a player wins then tries to play again, therefore, the following variables are
     * affected with this method.
     * @Field #numberOfAttempts
     * @Field #numberOfSavedFeedback
     * @Field #numberOfSavedHistory
     * @Field #isWin
     * @Field #computerFeedback
     * @Field #guessHistory
     *
     * @See #play()
     * @See #isPlayAgain()
     */
    private void resetHistory() {
        numberOfAttempts = 10;
        numberOfSavedFeedback = 0;
        numberOfSavedHistory = 0;
        isWin = false;
        computerFeedback = new String[10][3];
        guessHistory = new String[10][2];
        //userId = 0;
    }

    /**
     * Saves feedback in the RAM to display it back to the user.
     * @param index
     * @param correctNumLoc
     * @param incorrectNum
     * @param incorrectEntry
     * @return returns a string of array for testing purpose.
     */
    //Save/Read from RAM.
    private String[] saveFeedback(int index, String correctNumLoc, String incorrectNum, String incorrectEntry) {
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
     * GetFeedback reads feedback from RAM for display purpose only. If game stops, the data is lost.
     * @return
     */
    private int getFeedback() {
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
     * @param guess
     */
    private void saveGuessHistory(String guess) {
        guessHistory[numberOfSavedHistory][0] = userName;
        guessHistory[numberOfSavedHistory][1] = guess;
        numberOfSavedHistory++;
    }

    /**
     * GetHistory reads the saved data from RAM.
     *
     * @See #saveGuessHistory()
     */
    public void getHistory() {
        for (int i = 0; i < numberOfSavedHistory; i++) {
            System.out.println(guessHistory[i][0] + ", your guessed history: " + (i+1) + "  "  + guessHistory[i][1]);
        }
    }

    /**
     * The following methods are used to communicate with an installed instance of MySQL.
     * The main class for MySQL is created in a seperate java file called ConnectToMySQL. This class establishes
     * connection with mysql database then creates/reads the data.
     *
     * @See #Class#ConnecToMySQL
     *
     */

    /**
     * WriteInputHistoryIntoDB saves @Field #userName and @Field #userGuess into mysql's database.
     * @param userName
     * @param guess
     *
     * @See #class#ConnectToMySQL
     */
    private void writeInputHistoryIntoDB(String userName, String guess) {
        ConnectToMySQL.writeInputHistoryIntoDB(userName, guess);
    }

    /**
     * ReadUserInputHistoryFromDB read data from mysql database.
     * @See #writeInputHistoryIntoDB()
     * @See #class#ConnectToMySQL
     */
    private void readUserInputHistoryFromDB(){
        ConnectToMySQL.readUserInputHistoryFromDB();
    }

    /**
     * Reads saved feedbacks from MySQL. Check class ConnectToMySQL for the implementation.
     */
    private void readFeedback() {
        ConnectToMySQL.readFeedback();
    }

    /**
     * Writes Feedback into the table as the integer values only, so a play can compute it later.
     * @param feedback consist of the 3 variables and userName.
     *
     * @See #play()
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
     * @See #ConnectToMySQL#commentedSQLCommands for table names and structure.
     */
    private void readFeedbackGuessFromDB() {
        ConnectToMySQL.readFeedbackGuessFromDB();
    }


}