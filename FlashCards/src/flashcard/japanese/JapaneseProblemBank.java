package flashcard.japanese;

import flashcard.AbstractProblem;
import flashcard.AbstractProblemBank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JapaneseProblemBank implements AbstractProblemBank {

    public JapaneseProblemBank() {
        super();
        String[] resources = {"flashcard/japanese/resources/hiragana.txt", "flashcard/japanese/resources/katakana.txt"};

        for (String res : resources) {
            List<String> strings = loadResource(res);
            System.out.println(strings);
        }
    }

    private static List<String> loadResource(String resourceName) {
        ClassLoader classLoader = JapaneseProblemBank.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourceName);

        if (inputStream != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
                return bufferedReader.lines().collect(Collectors.toList());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Resource not found!");
        }
        return Collections.emptyList();
    }

    public AbstractProblem generate() {
        return null;
    }
}
