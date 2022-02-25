package lesson2;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.sqrt;

@SuppressWarnings("unused")
public class JavaAlgorithms {
    /**
     * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
     * Простая
     *
     * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
     * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
     *
     * 201
     * 196
     * 190
     * 198
     * 187
     * 194
     * 193
     * 185
     *
     * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
     * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
     * Вернуть пару из двух моментов.
     * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
     * Например, для приведённого выше файла результат должен быть Pair(3, 4)
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public Pair<Integer, Integer> optimizeBuyAndSell(String inputName) throws IOException {
        //Время О(n)
        //Ресурсоемкость S(1)
        Pair<Integer, Integer> sell = new Pair<>(0, 0);
        Pair<Integer, Integer> buy = new Pair<>(0, 0);
        Pair <Integer, Integer> possibleBuy = new Pair<>(0, 0);
        int lineNumber = 0;
        String currentLine;
        int currentCost;
        Scanner reader = new Scanner(Paths.get(inputName));
        while (reader.hasNextLine()) {
            currentLine = reader.nextLine();
            if (!currentLine.matches("^\\d+$")) throw new IllegalArgumentException();
            lineNumber++;

            if (lineNumber == 1) {
                currentCost = Integer.parseInt(currentLine);
                sell = new Pair<>(lineNumber, currentCost);
                buy = new Pair<>(lineNumber, currentCost);
                possibleBuy = new Pair<>(lineNumber, currentCost);
                continue;
            }

            currentCost = Integer.parseInt(currentLine);
            if (currentCost - buy.getSecond() > sell.getSecond() - buy.getSecond())
                sell = new Pair<>(lineNumber, currentCost);
            if (currentCost - possibleBuy.getSecond() > sell.getSecond() - buy.getSecond()) {
                sell = new Pair<>(lineNumber, currentCost);
                buy = new Pair<>(possibleBuy.getFirst(), possibleBuy.getSecond());
            }
            if (possibleBuy.getSecond() > currentCost)
                possibleBuy =  new Pair<>(lineNumber, currentCost);
        }
        return new Pair<>(buy.getFirst(), sell.getFirst());
    }

    /**
     * Задача Иосифа Флафия.
     * Простая
     *
     * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
     *
     * 1 2 3
     * 8   4
     * 7 6 5
     *
     * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
     * Человек, на котором остановился счёт, выбывает.
     *
     * 1 2 3
     * 8   4
     * 7 6 х
     *
     * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
     * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
     *
     * 1 х 3
     * 8   4
     * 7 6 Х
     *
     * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
     *
     * 1 Х 3
     * х   4
     * 7 6 Х
     *
     * 1 Х 3
     * Х   4
     * х 6 Х
     *
     * х Х 3
     * Х   4
     * Х 6 Х
     *
     * Х Х 3
     * Х   х
     * Х 6 Х
     *
     * Х Х 3
     * Х   Х
     * Х х Х
     *
     * Общий комментарий: решение из Википедии для этой задачи принимается,
     * но приветствуется попытка решить её самостоятельно.
     */
    static public int josephTask(int menNumber, int choiceInterval) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая общая подстрока.
     * Средняя
     *
     * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
     * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
     * Если общих подстрок нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     * Если имеется несколько самых длинных общих подстрок одной длины,
     * вернуть ту из них, которая встречается раньше в строке first.
     */
    static public String longestCommonSubstring(String first, String second) {
        //m - длина первой строки, n - длина второй строки
        //Время О(m * n)
        //Ресурсоемкость S(m * n)

        if(first.length() == 0 ||second.length() == 0) return "";

        int[][] matrix = new int[first.length() + 1][second.length() + 1];
        int length = 0;
        int end = 0;
        for(int i = 1; i < first.length(); i++){
            for(int j = 1; j < second.length(); j++){
                if (first.charAt(i - 1) == second.charAt(j - 1)) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                    int l = matrix[i][j];
                    if (l > length) {
                        length = l;
                        end = i;
                    }
                }
            }
        }
        if (end == 0) return "";
        else return first.substring(end - length, end);
    }

    /**
     * Число простых чисел в интервале
     * Простая
     *
     * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
     * Если limit <= 1, вернуть результат 0.
     *
     * Справка: простым считается число, которое делится нацело только на 1 и на себя.
     * Единица простым числом не считается.
     */

    static public int calcPrimesNumber(int limit) {
        //Алгоритм решета Эратосфена
        //Время О(nlog(log(n)))
        //Ресурсоемкость S(n)

        if (limit <= 1) return 0;
        int counter = 0;
        boolean[] primes = new boolean[limit + 1];
        Arrays.fill(primes, true);
        primes[0] = false;
        primes[1] = false;
        for (int i = 2; i * i <= limit; i++){
            if (primes[i]){
                for (int j = 2 * i; j <= limit; j += i) {
                    primes[j] = false;
                }
            }

        }
        for (int i = 2; i <= limit; i++) {
            if (primes[i]) counter++;
        }
        return counter;
    }
}
