package flashcard;

import flashcard.japanese.JapaneseProblemBank;

import java.util.*;

public class Client {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        AbstractProblemBank bank = new JapaneseProblemBank();
        GradingSheet grade = new GradingSheet();
        for (int i = 0; i < 10; ++i) {
            AbstractProblem problem = bank.generate();
            System.out.println(problem);
            AbstractAnswer answer = problem.answer();
            Score score = answer.verify(console);
            System.out.println(score);
            grade.record(problem, score);
        }
        System.out.println(grade);
    }
}
