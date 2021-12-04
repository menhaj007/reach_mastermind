package main;
import com.github.iarks.RandomOrgAPI.InvalidMethodCallException;
import com.github.iarks.RandomOrgAPI.InvalidResponseException;
import com.github.iarks.RandomOrgAPI.RandomNumber;

public class RandomIntegerGenerator {
    public static int[] getRandomNumber(int n, int max, int min, boolean replacement, String method) {
        int number = 0, bitsLeft, bitsUsed, requestsLeft;
        int[] arrayOfRandomNNumbers = new int[n];
        try {
            RandomNumber rn = new RandomNumber("a31090c0-5226-4954-874e-ea1607631855");
            rn.generate(n, max, min, replacement,method);
            System.out.println("Obtained random numbers:");
            System.out.println("obtaining random numbers together as an integer array");
            arrayOfRandomNNumbers = rn.getElementAsArray();
            bitsLeft=rn.getBitsLeft();
            bitsUsed=rn.getBitsUsed();
            requestsLeft=rn.getRequestsLeft();
            System.out.println("Bits Used = " + bitsUsed);
            System.out.println("Bits Left = " + bitsLeft);
            System.out.println("Requests Left = " + requestsLeft);
        }
        catch (InvalidResponseException invalidResponseException)
        {
            System.out.println(invalidResponseException.getMessage());
            invalidResponseException.printStackTrace();
        }
        catch(InvalidMethodCallException invalidMethodCallException)
        {
            System.out.println(invalidMethodCallException.getMessage());
            invalidMethodCallException.printStackTrace();
        }
        return arrayOfRandomNNumbers;
    }
}
