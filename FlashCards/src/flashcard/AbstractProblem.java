package flashcard;

import java.util.List;

public interface AbstractProblem {
    String answer();

    int score();

    String prompt();

    List<BasicChoice> choices();
}
