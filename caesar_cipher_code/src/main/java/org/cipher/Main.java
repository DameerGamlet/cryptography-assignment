package org.cipher;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.cipher.services.FileService.*;

public class Main {

    private static final List<Character> PUNCT = new ArrayList<>(Arrays.asList(
            '!', '"', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<',
            '=', '>', '?', '@', '_', '{', '}', ' ', '\n')
    );

    // печать алфавита на экран
    public static void printAbc(Map<Integer, String> map) {
        System.out.println("Алфавит:");

        AtomicInteger index = new AtomicInteger(0);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (index.getAndIncrement() % 6 == 0) System.out.println();
            System.out.printf("\t%s - %d   ", (entry.getKey() != 65 ? entry.getValue() : " "), entry.getKey());
        }
    }

    // изменяем ключ в правильном виде
    public static StringBuilder convertString(Map<Integer, String> map, String key) {
        StringBuilder result = new StringBuilder();
        for (String item : key.split("")) {
            map.forEach((k, v) -> {
                if (v.equals(item)) result.append(item);
            });
        }
        return result;
    }

    // создание списка алфавита с соответствующими элементами
    public static Map<Integer, String> createAbc() {
        int index = 0;
        Map<Integer, String> map = new LinkedHashMap<>();
        // буквы
        for (char i = 'А'; i <= 'Я'; i++) {
            map.put(index, String.valueOf(i));
            index += 1;
            if (i == 'Е') {
                map.put(index, "Ё");
                index += 1;
            }
        }
        // числа
        for (int i = 0; i <= 9; i++) map.put(index++, String.valueOf(i));
        // знаки пунктуации
        for (Object k : PUNCT) map.put(index++, "" + k);

        return map;
    }

    // номера всех элементов сходного сообщения для шифрования
    public static List<Integer> keyNumber(Map<Integer, String> map, List<String> keyList, String message, String key) {
        int mLength = message.length();
        int kLength = key.length();
        int j = 0;
        List<Integer> kNumber = new ArrayList<>();
        // проход по всем элементам входного слова
        for (int i = 0; i < mLength; i++) {
            String element = keyList.get(j);
            j += 1;
            AtomicInteger atomicInteger = new AtomicInteger();
            // ищем номера всех элементов входного слова
            map.forEach((k, v) -> {
                if (v.equals(element)) atomicInteger.set(k);
            });
            // сохранить номер элемента
            kNumber.add(atomicInteger.get());
            if (j == kLength) j = 0;
        }
        return kNumber;
    }

    private static void showMessageKey(String message, String key) {
        System.out.printf("""
                Текст для шифрования: \n%s
                                
                Ключ для шифрования: %s
                """, message, key);
    }

    // шифрование
    public static void encrypt(String message, String key) {
        Map<Integer, String> map = createAbc();
        printAbc(map);

        message = String.valueOf(convertString(map, message));
        key = String.valueOf(convertString(map, key));

        showMessageKey(message, key);

        // получить номера всех символов входного слова
        List<Integer> keyNumber = keyNumber(map, Arrays.asList(key.split("")), message, key);

        List<String> en = new ArrayList<>();
        List<String> list = Arrays.asList(message.split(""));
        int j = 0;
        int len = map.size();
        // шифрование
        for (int i = 0; i < list.size(); i++) {
            String item = list.get(j);
            int textNumber = 0;
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (entry.getValue().equals(item)) {
                    textNumber = entry.getKey();
                    break;
                }
            }
            // новый результат
            int resultNumber = textNumber + keyNumber.get(j);
            en.add(resultNumber > len - 1 ? map.get(resultNumber % len) : map.get(resultNumber));

            j += 1;
        }
        String result = String.join("", en);
        String res = result.isEmpty() ? null : result.substring(0, result.length() - 1);

        System.out.println("\nРезультат: \n" + res);
        write(PATH_TO_OUTPUT + "result_encoder.txt", String.join("", res));
    }

    // получить список номер всех слов входного сообщения
    public static List<Integer> getNumberList(Map<Integer, String> map, String message, String key) {
        int j = 0;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < message.length(); i++) {
            String element = Arrays.asList(key.split("")).get(j);
            j += 1;
            AtomicInteger indexCurrent = new AtomicInteger();
            // находим слово - сохраняем
            map.forEach((k, v) -> {
                if (v.equals(element)) indexCurrent.set(k);
            });
            // сохранить элемент
            list.add(indexCurrent.get());
            if (j == key.length()) j = 0;
        }
        return list;
    }

    public static void decrypt(String message, String key) {
        // алфавит
        Map<Integer, String> map = createAbc();
        map.forEach((k, v) -> System.out.println(k + " -> " + v));
        printAbc(map);

        showMessageKey(message, key);

        // получить номера всех элементов алфавита
        List<Integer> keyListInt = getNumberList(map, message, key);
        List<String> de = new ArrayList<>(),
                list = Arrays.asList(message.split(""));
        int j = 0;
        int len = map.size();
        for (int i = 0; i < list.size(); i++) {
            int indexCurrent = 0;
            // получить элемент алфавита (его номер, например, э - 30)
            // иначе говоря поиск номера символа
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (entry.getValue().equals(list.get(j))) {
                    indexCurrent = entry.getKey();
                    // нашли номер, выходим
                    break;
                }
            }
            // получаем новый номер элемента
            int newIndex = indexCurrent - keyListInt.get(j);
            // если вышли за границы, то выполняем код ниже
            if (newIndex < 0)  newIndex = len - Math.abs(newIndex);

            System.out.println(newIndex);
            // сохраняем результат

            de.add(newIndex > len - 1 ? map.get(newIndex % len) : map.get(newIndex));

            j += 1;
        }
        String result = String.join("", de);
        String res = result.isEmpty() ? null : result.substring(0, result.length() - 1);

        System.out.println("\nРезультат: \n" + res);
        write(PATH_TO_OUTPUT + "result_decoder.txt", res);
    }

    private static void breakMain(String message) {
        System.out.println(message);
        System.exit(0);
    }

    // вывод выбора на экран
    public static void action(String message, String key) throws IOException {
        System.out.print("""

                \t(1) Зашифровать сообщение
                \t(2) Расшифровать сообщение
                \t(0) Выйти
                \tКакое из действий выполнить:\s""");
        int numberSubTask = 0;
        try {
            numberSubTask = input.nextInt();
        } catch (Exception e) {
            breakMain("\nВы должны ввести числа 1 или 2");
        }
        if (numberSubTask == 1) {
            if (message.isEmpty()) {
                breakMain("Попытка скрыть пустое сообщение !!!");
            }
            if (key.isEmpty()) {
                breakMain("Пустой ключ !!!");
            }
            encrypt(message, key);
        } else if (numberSubTask == 2) {
            String enText = readFromFile(PATH_TO_OUTPUT + "result_encoder.txt");
            if (enText.isEmpty()) {
                breakMain(" Пустое сообщение нельзя раскрыть! ");
                return;
            }
            decrypt(enText, key);
        } else if (numberSubTask == 0) {
            breakMain("\nЗавершение выполнения программы");
            return;
        } else {
            System.err.println(" Вы ввели неверные данные !!!");
        }
        System.out.println(" -".repeat(50));
        action(message, key);
    }

    // сканер для чтения из консоли
    public static Scanner input = new Scanner(System.in);

    private static String textFormatter(String path) throws IOException {
        return readFromFile(path)
                .replaceAll("[a-zA-Z]", "")
                .toUpperCase();
    }

    public static void main(String[] args) throws IOException {
        String message = textFormatter(PATH_TO_INPUT + "message.txt");
        String key = textFormatter(PATH_TO_INPUT + "key.txt");
        action(message, key);
    }
}
