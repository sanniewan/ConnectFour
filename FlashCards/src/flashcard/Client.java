package flashcard;

import flashcard.japanese.JapaneseProblemBank;

import java.util.*;

public class Client {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        AbstractProblemBank bank = new JapaneseProblemBank();
        GradingSheet grade = new GradingSheet();
        for (int i = 0; i < 3; ++i) {
            AbstractProblem problem = bank.generate();
            System.out.println(problem);
            String choice = console.next();
            grade.record(problem, choice);
        }
        System.out.println(grade);
    }
}
