import interfaces.VariablesInterface;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Main implements VariablesInterface {
    public static Scanner scanner = new Scanner(System.in);

    public static String replaceSuperfluous(String input) {
        return input.replaceAll("[a-zA-Z]", "").replace("\n", " ").toUpperCase();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("\n\t3. Шифр революционеров.\n");

        String mes = replaceSuperfluous(Files.readString(Paths.get(variables.pathMes)));
        String key = replaceSuperfluous(Files.readString(Paths.get(variables.pathKey))).replaceAll(" ", "");
        String res = replaceSuperfluous(Files.readString(Paths.get(variables.pathResEn)));

        List<String> test = Arrays.asList(key.split(""));
//        List<String> listWithoutDuplicates =
//                test.stream().distinct().collect(Collectors.toList());
        key = String.join("", test);

        if (mes.length() == 0) {
            System.err.println(" Пустое сообщение нельзя зашифровать ! ");
            System.exit(0);
        }
        if (key.length() == 0) {
            System.err.println(" Попытка использовать пустой ключ ! ");
            System.exit(0);
        }

        System.out.print("\n\tВыберите:\n\t\t1. Зашифровать сообщение,\n\t\t2. Расшифровать сообщение.\n\tВыбор: ");
        String choice = scanner.next();
        if (choice.equals("1")) {
            encryption.encrypt(mes, key);
        } else if (choice.equals("2")) {
            if (res.length() == 0) {
                System.err.println(" Пустое сообщение нельзя раскрыть! ");
                System.exit(0);
            }
            decryption.decrypt(res, key);
        } else {
            System.err.println(" Некорректный выбор !");
        }
    }
}
