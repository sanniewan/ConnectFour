package flashcard.japanese;

import flashcard.AbstractProblem;
import flashcard.AbstractProblemBank;
import flashcard.BasicChoice;
import flashcard.BasicProblem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class JapaneseProblemBank implements AbstractProblemBank {

    private static final String hiragana = "平仮名";
    private static final String katakana = "片仮名";
    private static final Random random = new Random();
    private static final Map<Long, JapaneseAlphabet> alphabetIdMap = new HashMap<>();
    private static final List<JapaneseAlphabet> alphabets = new ArrayList<>();
    private int choiceSize;

    public JapaneseProblemBank(int distractors) {
        super();
        String[] resources = {"flashcard/japanese/resources/hiragana.txt", "flashcard/japanese/resources/katakana.txt"};
        for (String res : resources) {
            for (String element : loadResource(res)) {
                String[] frags = element.split("\t");
                JapaneseAlphabet alphabet = new JapaneseAlphabet(Long.valueOf(frags[0]), frags[1], frags[2],
                        frags[3]);
                alphabetIdMap.put(alphabet.id(), alphabet);
                alphabets.add(alphabet);
            }
        }

        this.choiceSize = distractors + 1;
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
            System.err.println("Resource not found!");
        }
        return Collections.emptyList();
    }


    private static <T> List<T> getRandomN(List<T> list, int n) {
        List<T> buffer = new ArrayList<>(list);
        Collections.shuffle(buffer, random);
        return buffer.subList(0, n);
    }

    private static <T> T getRandomSubset(T[] list) {
        if (list == null || list.length == 0) {
            throw new IllegalArgumentException("List is empty or null");
        }

        int randomIndex = random.nextInt(list.length);

        return list[randomIndex];
    }

    public AbstractProblem generate() {
        ProblemType type = getRandomSubset(ProblemType.values());
        return generate(type);
    }

    private BasicProblem generate(ProblemType type) {
        List<JapaneseAlphabet> pick = getRandomN(this.alphabets, choiceSize);
        String prompt = generatePrompt(pick.get(0), type);
        List<BasicChoice> choices = generateChoice(pick, type);

        return new BasicProblem(prompt, choices);
    }

    private enum ProblemType {
        PRONOUNCE,
        SPELL,
        ;

        ProblemType() {
        }
    }

    public String generatePrompt(JapaneseAlphabet alphabet, ProblemType type) {
        switch (type) {
            case SPELL -> {
                return String.format("Which of the following sounds like '%s'?",
                        alphabet.romanPronounce());
            }
            case PRONOUNCE -> {
                return String.format("How to pronounce %s?", alphabet.japaneseChar());
            }
            default -> {
                return "";
            }
        }

    }

    public List<BasicChoice> generateChoice(List<JapaneseAlphabet> pick, ProblemType type) {
        String correct = getPart(pick.get(0), type);
        List<String> parts = new ArrayList<>(pick.stream().map(alphabet -> getPart(alphabet, type)).distinct().toList());
        Collections.shuffle(parts, random);

        List<BasicChoice> result = new ArrayList<>();
        for (int i = 0; i < parts.size(); ++i) {
            String label = String.format("%c", (char) ('a' + i));
            String description = parts.get(i);
            result.add(new BasicChoice(label, description, Objects.equals(description, correct)));
        }
        return result;
    }

    private String getPart(JapaneseAlphabet alphabet, ProblemType type) {
        switch (type) {
            case PRONOUNCE -> {
                return alphabet.romanPronounce();
            }
            case SPELL -> {
                return alphabet.japaneseChar();
            }
        }
        return "";
    }
}
