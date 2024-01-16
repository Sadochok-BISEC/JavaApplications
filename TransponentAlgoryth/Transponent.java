/**
 * @author Ilya Borisov
 * @version java 8.0
 */
public class Transponent {

    public static void main(String[] args) {
/**
 * main - точка входа в класс и приложение
 * создает базовую матрицу n на n
 */
        //создаем матрицу n на n
        int n = 3;
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = n*i + j;
            }
        }

        // выводим начальную матрицу
        System.out.println("Начальная матрица");
        System.out.println("------");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%4d", a[i][j]);
            }
            System.out.println();
        }
/**
 * транспонирует матрицу n на n
 * и выводит ее на экран
 */
        //транспонирование матрицы
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int temp = a[i][j];
                a[i][j] = a[j][i];
                a[j][i] = temp;
            }
        }

        //выводим на экран транспонированную матрицу
        System.out.println();
        System.out.println("Новая матрица");
        System.out.println("------");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%3d", a[i][j]);
            }
            System.out.println();
        }

    }
}