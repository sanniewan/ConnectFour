package flashcard;

import flashcard.japanese.JapaneseProblemBank;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);

        AbstractProblemBank bank = new JapaneseProblemBank(5);
        GradingSheet grade = new GradingSheet();
        JFrame jFrame = new JFrame("Test on ...");
        for (int i = 0; i < 10; ++i) {
            AbstractProblem problem = bank.generate();
            int problemNumber = i + 1;
            System.out.printf("%d. ", problemNumber);
            System.out.println(problem);
            present(jFrame, problemNumber, problem);
            String choice = console.next();
            grade.record(problem, choice);
        }
        System.out.println(grade);
    }

    private static void present(JFrame frame, int problemNumber, AbstractProblem problem) {
        JLabel label = new JLabel(String.format("%d. %s", problemNumber, problem.prompt()));
        label.setFont(new Font("Arial", Font.BOLD, 20));
        // Set layout manager (GridLayout with 1 row and 5 columns)
        List<BasicChoice> choices = problem.choices();
        frame.setLayout(new GridLayout(choices.size(), 1));
        frame.add(label);

        // Add the button to the content pane of the JFrame
        for (BasicChoice choice : choices) {
            ChoiceButton button = createButton(choice);
            frame.add(button.button());
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 400);  // Adjust the size as needed
        frame.setVisible(true);
    }

    private static ChoiceButton createButton(BasicChoice choice) {
        // Create a JButton
        ChoiceButton result = new ChoiceButton( choice);
        return result;
    }
}
