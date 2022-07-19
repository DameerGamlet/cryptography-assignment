import java.io.IOException;
import java.util.*;

import static java.util.Map.entry;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        String input = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
        List<String> inputList = Arrays.asList(input.split(""));

        int key = 7;

        System.out.println("Входное сообщение: \"" + input + "\"" + "\n" + "Ключ: " + key + "\n" + "-".repeat(50));

        Map<String, String> abcConverted = Map.ofEntries(
                entry("A", "B"),
                entry("B", "C"),
                entry("C", "D"),
                entry("D", "F"),
                entry("E", "G"),
                entry("F", "H"),
                entry("G", "J"),
                entry("H", "K"),
                entry("I", "L"),
                entry("J", "M"),
                entry("K", "N"),
                entry("L", "P"),
                entry("M", "Q"),
                entry("N", "R"),
                entry("O", "S"),
                entry("P", "T"),
                entry("Q", "V"),
                entry("R", "W"),
                entry("S", "X"),
                entry("T", "Y"),
                entry("U", "Z"),
                entry("V", "A"),
                entry("W", "E"),
                entry("X", "I"),
                entry("Y", "O"),
                entry("Z", "U"),
                entry(" ", " "),
                entry("(", "("),
                entry(")", ")")
        );

        List<String> newElements = new ArrayList<>(inputList);

        for (int i = 1; i <= key; i++) {
            System.out.println("Шаг номер " + i);
            List<String> temp = new ArrayList<>();
            newElements.forEach(item -> {
                System.out.print(abcConverted.get(item));
                temp.add(abcConverted.get(item));
            });
            newElements = new ArrayList<>(temp);
            System.out.println("\n");
        }

    }
}