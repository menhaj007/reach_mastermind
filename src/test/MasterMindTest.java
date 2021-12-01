package test;

import main.MasterMind;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MasterMindTest {


    @Test
    void name() {
    }

//    @BeforeEach
    @Test
    void calculate() {
        MasterMind masterMind = new MasterMind();
        int[] tmpArr = {1,2,3,4};
        int[] tmpArr2 = {1,2,3,4};
        int expected = 4;
        Assertions.assertEquals(4, masterMind.calculate(tmpArr, tmpArr2));
    }

//    @org.junit.jupiter.api.Test
//    void startGame() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void chooseDifficultyLevel() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void generateRandomNumbers() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void isValid() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void readUserInput() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void play() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void getHistory() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void saveWinners() {
//    }
//
//    @org.junit.jupiter.api.Test
//    void getWinners() {
//    }
}