package lesson7;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * Если есть несколько самых длинных общих подпоследовательностей, вернуть любую из них.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        //Трудоемкость O(first.length * second.length)
        //Ресурсоемкость O(first.length * second.length)
        int[][] matrix = new int[first.length() + 1][second.length() + 1];
        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                if (i == 0 || j == 0)
                    matrix[i][i] = 0;
                else if (first.charAt(i - 1) == second.charAt(j - 1))
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                else matrix[i][j] = max(matrix[i - 1][j], matrix[i][j - 1]);
            }
        }
        StringBuilder result = new StringBuilder();
        int i = first.length();
        int j = second.length();
        while (i > 0 && j > 0) {
            if (matrix[i][j] == matrix[i - 1][j])
                j++;
            else if (matrix[i][j - 1] == matrix[i][j])
                i++;
            else result.append(first.charAt(i - 1));
            i--;
            j--;
        }

        return  result.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Сложная
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        //Трудоемкость O(N*logN)
        //Ресурсоемкость O(N)

        if (list.isEmpty()) return new ArrayList<>();

        int[] d = new int[list.size() + 1];
        int[] seqPosition = new int[list.size() + 1];
        int[] prevPosition = new int[list.size()];
        int length = 0;

        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.MIN_VALUE;
        }
        d[0] = Integer.MAX_VALUE;
        seqPosition[0] = -1;

        for (int i = list.size() - 1; i >= 0; i--) {
            int index = binarySearch(d, list.get(i));
            if (d[index - 1] > list.get(i) && list.get(i) > d[index]) {
                d[index] = list.get(i);
                seqPosition[index] = i;
                prevPosition[i] = seqPosition[index - 1];
                length = max(length, index);
            }
        }

        List<Integer> res = new ArrayList<>();
        if (length == 1) {
            res.add(list.get(0));
            return res;
        }

        int p = seqPosition[length];
        while (p != -1) {
            res.add(list.get(p));
            p = prevPosition[p];
        }
        return res;
    }

    private static int binarySearch(int[] d, Integer x) {
        int left = 0;
        int right = d.length - 1;
        while (right - left > 1) {
            int median = (left + right) / 2;
            if (x < d[median]) {
                left = median;
            } else {
                right = median;
            }
        }
        return right;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Средняя
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }
}
