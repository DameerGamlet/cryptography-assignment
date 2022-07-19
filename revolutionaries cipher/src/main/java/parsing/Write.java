package parsing;

import interfaces.VariablesInterface;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class Write implements VariablesInterface {
    public void write(String path, String text) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))) {
            writer.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
