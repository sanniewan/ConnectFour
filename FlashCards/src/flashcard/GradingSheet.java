package flashcard;

import java.util.*;
import java.util.stream.Collectors;

public class GradingSheet {
    Map<AbstractProblem, Set<String>> scorebook = new HashMap<>();

    public void record(AbstractProblem problem) {
        List<BasicChoice> choices = problem.choices();
        Set<String> response = choices.stream().filter(c -> c.checked()).map(c -> c.label()).collect(Collectors.toSet());
        scorebook.put(problem, response);
    }

    @Override
    public String toString() {
        return String.format("Total Score %d", getTotal()) + "\n" +
                String.format("Total problems %d", scorebook.size()) + "\n" +
                String.format("Correct %d", getCorrectCount()) + "\n" +
                String.format("Incorrect %d", scorebook.size() - getCorrectCount()) + "\n" +
                incorrect();
    }

    private String incorrect() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<AbstractProblem, Set<String>> entry : scorebook.entrySet()) {
            AbstractProblem problem = entry.getKey();
            Set<String> response = entry.getValue();
            if (Objects.equals(problem.answer(), response)) continue;
            sb.append(problem).append("\n");
            sb.append(problem.answer()).append("\n");
            sb.append(String.format("You answered %s\n", response));
        }
        return sb.toString();
    }

    public String details() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<AbstractProblem, Set<String>> entry : scorebook.entrySet()) {
            AbstractProblem problem = entry.getKey();
            sb.append(problem).append("\n");
            sb.append(problem.answer()).append("\n");
            Set<String> response = entry.getValue();
            sb.append(String.format("You answered %s\n", response));
        }
        return sb.toString();
    }

    private int getCorrectCount() {
        long total = scorebook.entrySet().stream().filter(entry -> {
            AbstractProblem problem = entry.getKey();
            Set<String> response = entry.getValue();
            return Objects.equals(problem.answer(), response);
        }).count();

        return (int) total;
    }

    private int getTotal() {
        Optional<Integer> total = scorebook.entrySet().stream().map(entry -> {
            AbstractProblem problem = entry.getKey();
            Set<String> response = entry.getValue();
            return Objects.equals(problem.answer(), response) ? problem.score() : 0;
        }).reduce(Integer::sum);

        return total.orElse(0);
    }
}
