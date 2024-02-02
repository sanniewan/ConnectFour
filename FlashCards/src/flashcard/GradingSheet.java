package flashcard;

import java.util.*;
import java.util.stream.Collectors;

public class GradingSheet {
    List<AbstractProblem> scorebook = new ArrayList<>();

    public void record(AbstractProblem problem) {
        scorebook.add(problem);
    }

    private Set<String> extractResponses(AbstractProblem problem) {
        List<BasicChoice> choices = problem.choices();
        return choices.stream().filter(BasicChoice::checked).map(BasicChoice::label).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return summary() + "\n" + String.join("\n", incorrect());
    }

    public String summary() {
        return String.format("Total Score %d", getTotal()) + "\n" +
                String.format("Total problems %d", scorebook.size()) + "\n" +
                String.format("Correct %d", getCorrectCount()) + "\n" +
                String.format("Incorrect %d", scorebook.size() - getCorrectCount());
    }

    public List<String> incorrect() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < scorebook.size(); i++) {
            StringBuilder sb = new StringBuilder();
            AbstractProblem problem = scorebook.get(i);
            Set<String> response = extractResponses(problem);
            if (Objects.equals(problem.answer(), response)) continue;
            sb.append(problem).append("\n");
            sb.append(problem.answer()).append("\n");
            sb.append(String.format("You answered %s\n", response));
            result.add(sb.toString());
        }
        return result;
    }

    public String details() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < scorebook.size(); i++) {
            AbstractProblem problem = scorebook.get(i);
            sb.append(problem).append("\n");
            sb.append(problem.answer()).append("\n");
            Set<String> response = extractResponses(problem);
            sb.append(String.format("You answered %s\n", response));
        }
        return sb.toString();
    }

    private int getCorrectCount() {
        long total = scorebook.stream().filter(problem -> {
            Set<String> response = extractResponses(problem);
            return Objects.equals(problem.answer(), response);
        }).count();

        return (int) total;
    }

    private int getTotal() {
        Optional<Integer> total = scorebook.stream().map(problem -> {
            Set<String> response = extractResponses(problem);
            return Objects.equals(problem.answer(), response) ? problem.score() : 0;
        }).reduce(Integer::sum);

        return total.orElse(0);
    }
}
