package flashcard;

import java.util.List;
import java.util.Optional;

public class BasicProblem implements AbstractProblem {
    private final String prompt;
    private final List<BasicChoice> choices;

    public BasicProblem(String prompt, List<BasicChoice> choices) {

        this.prompt = prompt;
        this.choices = choices;
    }

    @Override
    public String answer() {
        Optional<BasicChoice> correctAnswer = choices.stream().filter(BasicChoice::correctChoice).findAny();
        return correctAnswer.map(BasicChoice::label).orElse(null);
    }

    @Override
    public int score() {
        return 10;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s (score %d)", prompt, score())).append("\n");
        for (BasicChoice choice : choices) {
            sb.append(choice.label()).append("\t").append(choice.description()).append("\n");
        }
        return sb.toString();
    }
}
