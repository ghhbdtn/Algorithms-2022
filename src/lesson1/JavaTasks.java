package lesson1;

import kotlin.NotImplementedError;
import kotlin.Pair;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) throws IOException {
        //Время О(nlog(n))
        //Ресурсоемкость S(n)

        List<Integer> result = new ArrayList<>();
        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName)))) {
            String timeStr = reader.readLine();
            while(timeStr != null){
                int seconds = timeToSeconds(timeStr);
                result.add(seconds);
                timeStr = reader.readLine();
            }
        }

        Collections.sort(result);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            String finalStr;
            for (int seconds : result) {
                finalStr = secondsToTime(seconds);
                writer.write(finalStr + "\n");
            }
        }

    }

    public static int timeToSeconds(String time) {
        if (!time.matches("^(0[1-9]|1[0-2]):([0-5]\\d):([0-5]\\d) [PA]M$")) throw new IllegalArgumentException();
        String [] timeParts = time.split("[:|\\s]");
        int hh = Integer.parseInt(timeParts[0]);
        int mm = Integer.parseInt(timeParts[1]);
        int ss = Integer.parseInt(timeParts[2]);
        int seconds;
        if (hh == 12) {
            if (Objects.equals(timeParts[3], "AM")) hh = 0;
        }
        else {
            if (Objects.equals(timeParts[3], "PM")) hh += 12;
        }
        seconds = hh * 3600 + mm * 60 + ss;
        return seconds;
    }

    public static String secondsToTime(int seconds) {
        int hh = seconds / 3600;
        int mm = seconds  / 60 - hh * 60;
        int ss = seconds  - hh * 3600 - mm * 60;
        DecimalFormat df = new DecimalFormat("00");
        String last = "AM";
        if (hh == 12) last = "PM";
        else
        if (hh == 0) {
            hh = 12;
            last = "AM";
        }
        else
        if (hh > 12) {
            hh -= 12;
            last = "PM";
        }
        return df.format(hh) + ":" + df.format(mm) + ":" + df.format(ss) + " " + last;
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) throws IOException {
        //Время О(n)
        //Ресурсоемкость S(1)
        int numberOfValues = 5000 + 2730 + 1;
        int[] countOfValues = new int[numberOfValues];
        for (int i = 0; i < numberOfValues; i++){
            countOfValues[i] = 0;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputName)))) {
            String temperature = reader.readLine();
            while (temperature != null) {
                String[] parts = temperature.split("\\.");
                int index;
                if (temperature.startsWith("-")) {
                    index = (Integer.parseInt(parts[0]) * 10 - Integer.parseInt(parts[1])) + 2730;
                } else index = Integer.parseInt(parts[0]) * 10 + Integer.parseInt(parts[1]) + 2730;
                countOfValues[index]++;
                temperature = reader.readLine();
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            for (int i = 0; i < numberOfValues; i++) {
                int value = countOfValues[i];
                if (value != 0) {
                    double n = (i - 2730) / 10.0;
                    for (int j = 1; j <= value; j++) {
                        writer.write(n + "\n");
                    }
                }
            }
        }

    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) throws IOException {
        //Время О(n)
        //Ресурсоемкость S(n)
        Map<Integer, Integer> counter = new HashMap<>();
        List<Integer> numbers = new ArrayList<>();
        Pair<Integer, Integer> maxCountNum = new Pair<>(0,0);
        Scanner sc = new Scanner(Paths.get(inputName));
        while (sc.hasNextLine()){
            Integer number = Integer.parseInt(sc.nextLine());
            if (!counter.containsKey(number)) counter.put(number, 1);
            else counter.put(number, counter.get(number) + 1);
            numbers.add(number);
        }
        for(Map.Entry<Integer, Integer> entry : counter.entrySet()){
            if (maxCountNum.getSecond() == 0)
                maxCountNum = new Pair<>(entry.getKey(), entry.getValue());
            if (maxCountNum.getSecond() < entry.getValue())
                maxCountNum = new Pair<>(entry.getKey(), entry.getValue());
            if (Objects.equals(maxCountNum.getSecond(), entry.getValue()) && entry.getKey() < maxCountNum.getFirst())
                maxCountNum = new Pair<>(entry.getKey(), entry.getValue());
        }

        PrintWriter writer = new PrintWriter(outputName, StandardCharsets.UTF_8);
        for (Integer num : numbers) {
            if (!Objects.equals(num, maxCountNum.getFirst()))
                writer.write(num + "\n");
        }
        for (int i = 0; i < maxCountNum.getSecond(); i++){
            writer.write(maxCountNum.getFirst() + "\n");
        }
        writer.close();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }

}
