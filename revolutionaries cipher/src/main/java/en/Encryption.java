package en;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import interfaces.VariablesInterface;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Encryption implements VariablesInterface {
    public void encrypt(String mes, String key) {
        int lenKey = key.length(),
                lenMes = mes.length(),
                lenFromTable = (lenKey * (lenKey + 1) / 2),     // количество элементов в каждой таблице
                countTable = lenMes % lenFromTable;

        System.out.println("Сообщение: " + mes + " (" + lenMes + ")" +
                "\n    Ключ:  " + key + " (" + lenKey + ")" +
                "\nКол-во эл. в каждой таблице: " + lenFromTable +
                "\n              Кол-во таблиц: " + countTable + "\n");

        List<String> listMes = Arrays.asList(mes.split("")),
                listKey = Arrays.asList(key.split("")),
                abcKey = new ArrayList<>(listKey);
        Collections.sort(abcKey);

        List<List<String>> listCipher = operationWithTable.createTable(countTable, listKey, listMes);

        System.out.println("\nВ норм. порядке ключ: " + listKey + "\n" +
                "В алф. порядке ключ:  " + abcKey);

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
        }

        System.out.println();

        StringBuilder cipher = new StringBuilder();
        List<String> tempList = new ArrayList<>(keyListNew);
        Collections.sort(tempList);
        for (String item : tempList) {
            System.out.println(item + " - > " + multimap.get(item));
            cipher.append(String.join("", multimap.get(item)));
        }
        System.out.println("\nРезультат: " + cipher);

        write.write(variables.pathResEn, String.valueOf(cipher));
    }
}
