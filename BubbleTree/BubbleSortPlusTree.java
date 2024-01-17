import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class BubbleSortPlusTree {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int size;

        System.out.println("Input size of array(random generation): ");
        size = scanner.nextInt();

        fillingArrayRandomy(size);

    }

    public static void fillingArrayRandomy(int size)
    {
        // We are waiting for a number from the user about the dimension of the array
        // We fill it with random numbers

        Random random = new Random();
        int[] bArray = new int[size];

        // Filling
        for (int i = 0; i < size; i++)
        {
            bArray[i] = random.nextInt(10) + 1;
            System.out.print(bArray[i] + "  ");
        }
        bubbleSortProcess(bArray);
    }

    public static void bubbleSortProcess(int[] bArray)
    {
        boolean isSorted = false;
        int buffer;

        // Starting the bubble sorting process
        while (!isSorted)
        {
            isSorted = true;
            for(int i = 0; i < bArray.length - 1; i++)
            {
                if(bArray[i] > bArray[i+1])
                {
                    isSorted = false;
                    buffer = bArray[i];
                    bArray[i] = bArray[i+1];
                    bArray[i+1] = buffer;
                }
            }
        }
        System.out.println(Arrays.toString(bArray));

        Scanner scanner = new Scanner(System.in);
        int element;

        System.out.println("Input element for binary search: ");
        element = scanner.nextInt();

        binarySearch(element, bArray);
    }

    public static void binarySearch(int element, int[] bArray)
    {
        // We are waiting for the user to provide an element for binary search
        // We are performing a search

        int firstElement, lastElement;

        firstElement = 0;
        lastElement = bArray.length - 1;

        int position;
        int comparisonCount = 1;   // To count the number of comparisons

        // First, let's find the index of the middle element of the array
        position = (firstElement + lastElement) / 2;

        while ((bArray[position] != element) && (firstElement <= lastElement)) {
            comparisonCount++;
            if (bArray[position] > element) {  // If the number is set for the search
                lastElement = position - 1; // Reduce the position by 1.
            } else {
                firstElement = position + 1;    // Otherwise, we increase it by 1
            }
            position = (firstElement + lastElement) / 2;
        }
        if (firstElement <= lastElement) {
            System.out.println(element + " is " + ++position + " an element in the array");
            System.out.println("The binary search method found the number after " + comparisonCount +
                        " comparisons");
        } else {
            System.out.println("The element was not found in the array. The binary search method finished working after "
                        + comparisonCount + " comparisons");
        }
    }

}
