package org.cipher.services;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileService {

    public final static String PATH_TO_INPUT = "caesar_cipher_code/src/main/resources/input/";
    public final static String PATH_TO_OUTPUT = "caesar_cipher_code/src/main/resources/output/";

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

}
