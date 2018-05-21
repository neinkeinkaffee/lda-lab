package org.tw.neinkeinkaffee.lda.corpus.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileToStringReader {

    public static String readFileToString(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Path path = Paths.get(fileName);
            Files.lines(path).forEach(line -> {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
