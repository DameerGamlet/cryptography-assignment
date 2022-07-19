import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    // чтение из файла
    public static String readFromFile(String path) throws IOException {
        String everything;
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            everything = sb.toString();
        }
        return everything;
    }

    // запись в файл
    public static void write(String path, String text) {
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(path), StandardCharsets.UTF_8))) {
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // печать алфавита на экран
    public static void printAbc(Map<Integer, String> map) {
        System.out.println("Алфавит:");
        AtomicInteger atomicInteger = new AtomicInteger();
        map.forEach((k, v) -> {
            System.out.print("\t" + (k != 65 ? v : " ") + " - " + k + "   ");
            // 65 - заменяем переход на новую строку пробелом
            if (atomicInteger.incrementAndGet() == 6) {
                System.out.println();
                atomicInteger.set(0);
            }
        });
    }

    // изменяем ключ в правильном виде
    public static StringBuilder convertString(Map<Integer, String> map, String key) {
        StringBuilder result = new StringBuilder();
        for (String item : key.split("")) {
            map.forEach((k, v) -> {
                if (v.equals(item)) {
                    result.append(item);
                }
            });
        }
        return result;
    }

    // создание списка алфавита с соответствующими элементами
    public static Map<Integer, String> createAbc() {
        int j = 0;
        Map<Integer, String> map = new LinkedHashMap<>();
        // буквы
        for (char i = 'А'; i <= 'Я'; i++) {
            map.put(j, i + "");
            j += 1;
            if (i == 'Е') {
                map.put(j, "Ё");
                j += 1;
            }
        }
        // числа
        for (int i = 0; i <= 9; i++) {
            map.put(j, String.valueOf(i));
            j += 1;
        }
        // знаки пунктуации
        for (Object k : new ArrayList<>(Arrays.asList('!', '"', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '_', '{', '}', ' ', '\n'))) {
            map.put(j, "" + k);
            j += 1;
        }
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
                if (v.equals(element)) {
                    atomicInteger.set(k);
                }
            });
            // сохранить номер элемента
            kNumber.add(atomicInteger.get());
            if (j == kLength) {
                j = 0;
            }
        }
        return kNumber;
    }

    // шифрование
    public static void encrypt(String message, String key) {
        Map<Integer, String> map = createAbc();
        message = String.valueOf(convertString(map, message));
        key = String.valueOf(convertString(map, key));

        System.out.println();
        System.out.println("Текст для шифрования: \n" + message);
        System.out.println("Ключ для шифрования: " + key);
        // получить номера всех символов входного слова
        List<Integer> keyNumber = keyNumber(map, Arrays.asList(key.split("")), message, key);
        printAbc(map);

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
            if (resultNumber > len - 1) {
                en.add(map.get(resultNumber % len));
            } else {
                en.add(map.get(resultNumber));
            }
            j += 1;
        }
        String result = String.join("", en);
        String res = result.length() == 0 ? null : result.substring(0, result.length() - 1);

        System.out.println("\nРезультат: \n" + res);
        write("src/main/resources/output/result_encoder.txt", String.join("", res));
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
            if (j == key.length()) {
                j = 0;
            }
        }
        return list;
    }

    public static void decrypt(String message, String key) {
        // алфавит
        Map<Integer, String> map = createAbc();
        map.forEach((k, v) -> System.out.println(k + " -> " + v));
        // вывод на экран
        System.out.println();
        System.out.println("Текст для расшифрования: \n" + message + "\n");
        System.out.println("Ключ для расшифрования: " + key);
        printAbc(map);
        // получить номера всех элементов алфавита
        List<Integer> keyListInt = getNumberList(map, message, key);
        List<String> de = new ArrayList<>();
        List<String> list = Arrays.asList(message.split(""));
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
            if (newIndex < 0) {
                newIndex = len - Math.abs(newIndex);
            }
            System.out.println(newIndex);
            // сохраняем результат
            if (newIndex > len - 1) {
                de.add(map.get(newIndex % len));
            } else {
                de.add(map.get(newIndex));
            }
            j += 1;
        }
        String result = String.join("", de);
        String res = result.length() == 0 ? null : result.substring(0, result.length() - 1);

        System.out.println("\nРезультат: \n" + res);
        write("src/main/resources/output/result_decoder.txt", res);
    }

    // вывод выбора на экран
    public static void action(String message, String key) throws IOException {
        System.out.print("\n\t(1) Зашифровать сообщение" +
                "\n\t(2) Расшифровать сообщение" +
                "\n\t(0) Выйти" +
                "\n\tКакое из действий выполнить: ");
        int numberSubTask = 0;
        try {
            numberSubTask = input.nextInt();
        } catch (Exception e) {
            System.out.println("\nВы должны ввести числа 1 или 2");
            System.exit(0);
        }
        if (numberSubTask == 1) {
            if (message.length() == 0) {
                System.out.println("Попытка скрыть пустое сообщение !!!");
                System.exit(0);
            }
            if (key.length() == 0) {
                System.out.println("Пустой ключ !!!");
                System.exit(0);
            }
            encrypt(message, key);
        } else if (numberSubTask == 2) {
            String enText = readFromFile("src/main/resources/output/result_encoder.txt");
            if (enText.length() == 0) {
                System.err.println(" Пустое сообщение нельзя раскрыть! ");
                System.exit(0);
                return;
            }
            decrypt(enText, key);
        } else if (numberSubTask == 0) {
            System.out.println("\nЗавершение выполнения программы");
            System.exit(0);
            return;
        } else {
            System.err.println(" Вы ввели неверные данные !!!");
        }
        System.out.println(" -".repeat(50));
        action(message, key);
    }

    // сканер для чтения из консоли
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        String message = readFromFile("src/main/resources/input/message.txt").replaceAll("[a-zA-Z]", "").toUpperCase();
        String key = readFromFile("src/main/resources/input/key.txt").replaceAll("[a-zA-Z]", "").toUpperCase();
        action(message, key);
    }
}
