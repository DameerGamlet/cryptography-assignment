package table_operation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class TableOperation {
    public Set<String> createTable(String key, List<String> keyElements){
        Set<String> characterList = new LinkedHashSet<>();
        if (key.length() != 0) characterList.addAll(keyElements);

        for (char c = 'A'; c <= 'Z'; ++c) {
            if (c == 'J' || keyElements.contains(String.valueOf(c))) continue;
            characterList.add(c + "");
        }
        return characterList;
    }

    public void showTable(List<String> tableElement) {
        StringBuilder res = new StringBuilder();
        int i = 0;
        for (String elem : tableElement) {
            if (i++ == 0) {
                res.append("\t|");
            }
            res.append(" ").append(elem).append(" ");
            if (i == 5) {
                res.append("|\n");
                i = 0;
            }
        }
        System.out.println(res);
    }
}
