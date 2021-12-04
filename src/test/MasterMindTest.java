package test;

import main.MasterMind;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MasterMindTest {

    @Test
    void calculate() {
        MasterMind masterMind = new MasterMind();
        int[] tmpArr = {1,2,3,4};
        int[] tmpArr2 = {1,2,3,4};
        int expected = 4;
        Assertions.assertEquals(4, masterMind.calculate(tmpArr, tmpArr2));
    }
    @Test
    void chooseDifficultyLevel() {
        MasterMind mm = new MasterMind();
        //in the chooseDifficultyLevel, change the user choice manually.
        int userChoice = 2;
        switch (userChoice) {
            case 1:
                System.out.println("If input 1, output should 4");
                Assertions.assertEquals(4, mm.chooseDifficultyLevel());
                break;
            case 2:
                System.out.println("If input 2, output should 6");
                Assertions.assertEquals(6, mm.chooseDifficultyLevel());
                break;
            case 3:
                System.out.println("If input 3, output should 8");
                Assertions.assertEquals(8, mm.chooseDifficultyLevel());
                break;
            default:
                System.out.println("Invalid");
                break;
        }
    }
    @Test
    void generateRandomNumbers() {
        MasterMind masterMind = new MasterMind();
        System.out.println("Default size of Array is 4. Other choices depend on chooseDifficultyLevel()");
        Assertions.assertEquals(4, masterMind.generateRandomNumbers().length);
    }
    @Test
    void convertStringToArrayOfInteger() {
        MasterMind masterMind = new MasterMind();
        String userGuess1 = "(-1)24";
        String userGuess2 = "12342347477373";
        String userGuess3 = "1234";
        int[] correctAnswer = {1,2,3,4};
        int correctLength = correctAnswer.length;
        Assertions.assertEquals(correctAnswer.length, userGuess3.length());
    }
    @Test
    void result() {
        MasterMind masterMind = new MasterMind();
        Assertions.assertEquals("You Won! in " + 4 + " attempts", masterMind.result(4));
    }
    @Test
    void hint() {
        MasterMind masterMind = new MasterMind();
        int[] randNumbers = {1,2,3,4};
        int min = randNumbers[0];
        int max = randNumbers[3];
        int[] res = {1,4};
        Assertions.assertArrayEquals(res, masterMind.hint(randNumbers));
    }

}
