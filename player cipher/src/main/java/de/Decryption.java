package de;

import interfaces.VariablesInterface;

import java.util.*;

public class Decryption implements VariablesInterface {
    public void decrypt(String encrypted, String key) {
        List<String> keyElements = new ArrayList<>(List.of(key.toUpperCase().replace("J", "I").split(""))),
                mesElements = List.of(encrypted.toUpperCase().split("")),
                mesFormat = operationWithString.splitIntoBlocksLengthTwo(mesElements, false);

        Set<String> temp = new LinkedHashSet<>(keyElements);
        keyElements.clear();
        keyElements.addAll(temp);

        System.out.println("\n\tИнформация:\n\t\tЗащифрованное сообщение: " + encrypted + " (" + encrypted.length() + " символов)"
                + "\n\t\tСозданные пары: " + mesFormat + " (" + mesFormat.size() + " пар)"
                + "\n\t\tКлюч: " + String.join("", keyElements) + " (" + keyElements.size() + " символов)" + "\n");

        Set<String> characterList = operationWithTable.createTable(key, keyElements);
        operationWithTable.showTable(new ArrayList<>(characterList));

        Map<String, List<Integer>> map = new LinkedHashMap<>();
        List<List<String>> list = new ArrayList<>();
        List<String> tmpList = new ArrayList<>();

        int ii = 0, jj = 0;
        // создаём удобрный формат для хранения элементов
        for (String elem : characterList) {
            map.put(elem, Arrays.asList(ii, jj));
            tmpList.add(elem);
            if (jj++ == 4) {
                list.add(new ArrayList<>(tmpList));
                ii++;
                jj = 0;
                tmpList.clear();
            }
        }

        // сам процесс расшиварония
        List<String> unCipher = new ArrayList<>();
        for (String text : mesFormat) {
            List<String> tempStr = List.of(text.split(""));
            String res_elem, a = tempStr.get(0), b = tempStr.get(1);
            int i_a = map.get(a).get(0), j_a = map.get(a).get(1), i_b = map.get(b).get(0), j_b = map.get(b).get(1);
            List<String> list_a = list.get(i_a), list_b = list.get(i_b);

            // вариант 1: на разных строках и разных столбках
            if (i_a != i_b && j_a != j_b) {
                res_elem = list_a.get(j_b) + "" + list_b.get(j_a);
            }
            // вариант 2: на одном столбце
            else if (j_a == j_b) {
                list_a = list.get((i_a != 0) ? i_a - 1 : 4);
                list_b = list.get((i_b != 0) ? i_b - 1 : 4);
                res_elem = list_a.get(j_a) + "" + list_b.get(j_b);
            }
            // вариант 3: на одной строке
            else {
                res_elem = list_a.get((j_a != 0) ? j_a - 1 : 4) + "" + list_b.get((j_b != 0) ? j_b - 1 : 4);
            }
            unCipher.add(res_elem);
        }
        // результат и запись в файл
        String unCipherText = String.join("", unCipher);
        System.out.println("\tРасшифрованное сообщение: " + unCipherText);
        write.write(variables.pathResDe, unCipherText);
    }
}
