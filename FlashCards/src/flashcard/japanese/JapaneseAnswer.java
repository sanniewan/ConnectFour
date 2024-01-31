package flashcard.japanese;

import flashcard.AbstractAnswer;
import flashcard.Score;

import java.util.Scanner;

public class JapaneseAnswer implements AbstractAnswer {
    @Override
    public Score verify(Scanner console) {
        int choice = console.nextInt();

        return null;
    }
}
