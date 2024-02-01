package flashcard;

import java.util.List;
import java.util.Set;

public interface AbstractProblem {
    Set<String> answer();

    int score();

    String prompt();

    List<BasicChoice> choices();
}
