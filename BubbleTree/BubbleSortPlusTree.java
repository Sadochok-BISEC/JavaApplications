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
        //Ждем от пользователя числа о размерности массива
        //заполняем его случайными числами

        Random random = new Random();
        int[] bArray = new int[size];

        //заполнение
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

        //запускаем процесс пузырьковой сортировки
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
        //Ждем от пользователя элемент для бинарного поиска
        //выполняем поиск

        int firstElement, lastElement;

        firstElement = 0;
        lastElement = bArray.length - 1;

        int position;
        int comparisonCount = 1;   // для подсчета количества сравнений

        // для начала найдем индекс среднего элемента массива
        position = (firstElement + lastElement) / 2;

        while ((bArray[position] != element) && (firstElement <= lastElement)) {
            comparisonCount++;
            if (bArray[position] > element) {  // если число заданного для поиска
                lastElement = position - 1; // уменьшаем позицию на 1.
            } else {
                firstElement = position + 1;    // иначе увеличиваем на 1
            }
            position = (firstElement + lastElement) / 2;
        }
        if (firstElement <= lastElement) {
            System.out.println(element + " является " + ++position + " элементом в массиве");
            System.out.println("Метод бинарного поиска нашел число после " + comparisonCount +
                        " сравнений");
        } else {
            System.out.println("Элемент не найден в массиве. Метод бинарного поиска закончил работу после "
                        + comparisonCount + " сравнений");
        }
    }

}
