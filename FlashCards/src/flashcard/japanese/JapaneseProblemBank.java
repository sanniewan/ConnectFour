package flashcard.japanese;

import flashcard.AbstractProblem;
import flashcard.AbstractProblemBank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JapaneseProblemBank implements AbstractProblemBank {

    private final String hiragana = "平仮名";
    private final String katakana = "片仮名";

    public JapaneseProblemBank() {
        super();
        String[] resources = {"flashcard/japanese/resources/hiragana.txt", "flashcard/japanese/resources/katakana.txt"};
        Map<Long, JapaneseAlphabet> alphabets = new HashMap<>();
        for (String res : resources) {
            for (String element : loadResource(res)) {
                String[] frags = element.split("\t");
                JapaneseAlphabet alphabet = new JapaneseAlphabet(Long.valueOf(frags[0]), frags[1], frags[2],
                        frags[3]);
                alphabets.put(alphabet.id(), alphabet);
            }
        }
        System.out.println(alphabets);
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
        JapaneseProblem problem = new JapaneseProblem() {

        };

        return problem;
    }
}
