package org.tw.neinkeinkaffee.lda.helper;

import org.tw.neinkeinkaffee.lda.model.corpus.CorpusDocument;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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
