import interfaces.VariablesInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main implements VariablesInterface {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        System.out.println(" 1. Шифр Уитстона-Плейфера.");
        String mes = operationWithString.removeEverythingExceptLatin(Files.readString(Paths.get(variables.pathMes))),
                key = operationWithString.removeEverythingExceptLatin(Files.readString(Paths.get(variables.pathKey))),
                res = operationWithString.removeEverythingExceptLatin(Files.readString(Paths.get(variables.pathResEn)));

        if (mes.length() == 0) {
            System.err.println(" Пустое сообщение нельзя скрыть ! ");
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
