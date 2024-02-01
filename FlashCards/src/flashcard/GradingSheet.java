package flashcard;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class GradingSheet {
    Map<AbstractProblem, String> scorebook = new HashMap<>();

    public void record(AbstractProblem problem, String response) {
        scorebook.put(problem, response);
    }

    @Override
    public String toString() {
        return String.format("Total Score %d", getTotal()) + "\n" +
                String.format("Total problems %d", scorebook.size()) + "\n" +
                String.format("Correct %d", getCorrectCount()) + "\n" +
                String.format("Incorrect %d", scorebook.size() - getCorrectCount()) + "\n";
    }

    public String details() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<AbstractProblem, String> entry : scorebook.entrySet()) {
            AbstractProblem problem = entry.getKey();
            sb.append(problem).append("\n");
            sb.append(problem.answer()).append("\n");
        }
        return sb.toString();
    }

    private int getCorrectCount() {
        long total = scorebook.entrySet().stream().filter(entry -> {
            AbstractProblem problem = entry.getKey();
            String response = entry.getValue();
            return Objects.equals(problem.answer(), response);
        }).count();

        return (int) total;
    }

    private int getTotal() {
        Optional<Integer> total = scorebook.entrySet().stream().map(entry -> {
            AbstractProblem problem = entry.getKey();
            String response = entry.getValue();
            return Objects.equals(problem.answer(), response) ? problem.score() : 0;
        }).reduce(Integer::sum);

        return total.orElse(0);
    }
}
