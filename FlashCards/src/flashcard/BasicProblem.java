package flashcard;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class BasicProblem implements AbstractProblem {
    private final String prompt;
    private final List<BasicChoice> choices;

    public BasicProblem(String prompt, List<BasicChoice> choices) {

        this.prompt = prompt;
        this.choices = choices;
    }

    @Override
    public Set<String> answer() {
        return choices.stream().filter(BasicChoice::correctChoice).map(BasicChoice::label).collect(Collectors.toSet());
    }

    @Override
    public int score() {
        return 10;
    }

    @Override
    public String prompt() {
        return prompt;
    }

    @Override
    public List<BasicChoice> choices() {
        return choices;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s (score %d)", prompt, score())).append("\n");
        for (BasicChoice choice : choices) {
            sb.append(choice).append("\n");
        }
        return sb.toString();
    }
}
