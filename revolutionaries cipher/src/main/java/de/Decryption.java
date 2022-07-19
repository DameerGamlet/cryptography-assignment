package de;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import interfaces.VariablesInterface;

import java.util.*;

public class Decryption implements VariablesInterface {

    public void decrypt(String mes, String key) {
        int lenKey = key.length(), lenMes = mes.length(),
                lenFromTable = (lenKey * (lenKey + 1) / 2),     // количество элементов в каждой таблице
                countTable = lenMes % lenFromTable;

        System.out.println("Скрытое сообщение: " + mes + " (" + lenMes + ")" + "\n    Ключ:  " + key + " (" + lenKey + ")");

        List<String> listMes = Arrays.asList(mes.split("")), listKey = Arrays.asList(key.split(""));
        System.out.println();

        List<List<String>> listCipher = operationWithTable.createEmptyTable(countTable, lenKey, lenMes);
        Multimap<String, String> multimap = ArrayListMultimap.create();

        int index = 0;
        Set<String> keyListNew = new LinkedHashSet<>();
        for (List<String> item : listCipher) {
            int count = 0;
            for (int i = 0; i < item.size(); i++) {
                String newKey = (index++ / lenFromTable) + "_" + listKey.get(i) + "_" + count++;
                keyListNew.add(newKey);
                multimap.put(newKey, item.get(i));
            }
//            System.out.println();
        }

        List<String> tempList = new ArrayList<>(keyListNew);
        Collections.sort(tempList);

        List<String> test = new ArrayList<>();
        for (String item : tempList) {
            test.add(item.split("_")[2] + "_" + multimap.get(item).size());
        }
        Map<String, List<String>> map = new TreeMap<>();
        int a = 0, b = 0;
        int number = 0, index_new = 0;
        List<Map<String, List<String>>> tableCount = new ArrayList<>();
        for (String element : test) {
            // 0_a_2
            String q = element.split("_")[0];
            b += Integer.parseInt(element.split("_")[1]);
            System.out.println(index_new + "_" + (listKey.indexOf(q) + 1) + "_" + q + " " + listMes.subList(a, b));
            map.put(index_new + "_" + (listKey.indexOf(q) + 1) + "_" + q, listMes.subList(a, b));

            a = b;
            if (++number % lenKey == 0) {
                index_new++;
                tableCount.add(map);
                map = new TreeMap<>();
                System.out.println();
            }
        }
        tableCount.add(map);

        System.out.println("\nНормальный вид таблицы: ");
        StringBuilder deCipher = new StringBuilder();
        for (Map<String, List<String>> map1 : tableCount) {
            Multimap<Integer, String> newMul = ArrayListMultimap.create();

            Map<Integer, List<String>> temp = new HashMap<>();
            map1.forEach((k, v) -> temp.put(Integer.parseInt(k.split("_")[2]), v));

            temp.forEach((k, v) -> {
                System.out.println(k + " -> " + v);
                for (int i = 0; i < v.size(); i++) {
                    newMul.put(i, v.get(i));
                }
            });
            newMul.forEach((k, v) -> deCipher.append(String.join("", v)));
        }

        System.out.println("Результат расшифровки: " + deCipher);
        write.write(variables.pathResDe, String.valueOf(deCipher));
    }
}
