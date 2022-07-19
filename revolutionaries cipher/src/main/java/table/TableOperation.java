package table;

import java.util.ArrayList;
import java.util.List;

public class TableOperation {
    public List<List<String>> createTable(int countTable, List<String> listKey, List<String> listMes) {
        List<List<String>> listCipher = new ArrayList<>();
        int lenKey = listKey.size(),
                lenMes = listMes.size(),
                index = 0,
                countLen = lenKey;
        List<String> temp = new ArrayList<>();

        for (int k = 0; k < countTable; k++) {
            System.out.println("| " + String.join(" | ", listKey) + " |\n" + " ---".repeat(lenKey));
            for (int i = 0; i < lenKey; i++) {
                for (int j = 0; j < countLen; j++) {
                    if (index == lenMes) {
                        break;
                    }
                    temp.add(listMes.get(index));
                    index++;
                }
                countLen--;
                System.out.println("| " + String.join(" | ", temp) + " |");
                listCipher.add(temp);
                temp = new ArrayList<>();
                if (index == lenMes) {
                    return listCipher;
                }
            }
            countLen = lenKey;
            System.out.println();
        }
        return listCipher;
    }

    public List<List<String>> createEmptyTable(int countTable, int lenKey, int lenMes) {
        List<List<String>> listCipher = new ArrayList<>();
        int index = 0, countLen = lenKey;

        List<String> temp = new ArrayList<>();
        for (int k = 0; k < countTable; k++) {
            for (int i = 0; i < lenKey; i++) {
                for (int j = 0; j < countLen; j++) {
                    if (index == lenMes) {
                        break;
                    }
                    temp.add("_");
                    index++;
                }
                countLen--;
                listCipher.add(temp);
                temp = new ArrayList<>();
                if (index == lenMes) {
                    return listCipher;
                }
            }
            countLen = lenKey;
        }
        return listCipher;
    }
}
