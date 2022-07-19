package string_operation;

import interfaces.VariablesInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringOperation implements VariablesInterface {
    public String removeEverythingExceptLatin(String input) {
        return input.replaceAll("[^a-zA-Z]", "").toUpperCase();
    }

    public List<String> splitIntoBlocksLengthTwo(List<String> mesElements, boolean enOrDe) {
        List<String> mesFormat = new ArrayList<>();
        int index = 0;
        StringBuilder tempString = new StringBuilder();
        // разделяем слово на части длиной 2
        for (int j = 0; j < mesElements.size(); j++) {
            tempString.append(mesElements.get(j));
            if (index++ == 1) {
                List<String> temp = Arrays.asList(tempString.toString().split(""));
                if (Objects.equals(temp.get(0), temp.get(1)) && enOrDe) {
                    tempString = new StringBuilder(temp.get(0) + (temp.get(0).equals(variables.element_replacement_one) ? variables.element_replacement_two : variables.element_replacement_one));
                    j--;
                }
                mesFormat.add(tempString.toString());
                tempString = new StringBuilder();
                index = 0;
            }
        }
        // если последний элемент одиночный, то добавляем символ для составления пары
        if (tempString.length() % 2 != 0 && enOrDe) {
            mesFormat.add(tempString + (!tempString.toString().equals(variables.element_replacement_one) ?
                    variables.element_replacement_one : variables.element_replacement_two));
        }
        return mesFormat;
    }
}
