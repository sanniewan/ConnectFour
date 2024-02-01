package flashcard;

import flashcard.japanese.JapaneseProblemBank;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Client {

    public static void main(String[] args) {

        AbstractProblemBank bank = new JapaneseProblemBank(5);
        GradingSheet grade = new GradingSheet();
        JFrame jFrame = new JFrame("Test on ...");
        for (int i = 0; i < 10; ++i) {
            AbstractProblem problem = bank.generate();
            int problemNumber = i + 1;
            System.out.printf("%d. ", problemNumber);
            System.out.println(problem);
            present(jFrame, problemNumber, problem);
            grade.record(problem);
        }
        System.out.println(grade);
    }

    private static void present(JFrame frame, int problemNumber, AbstractProblem problem) {
        JPanel panel = new JPanel(new GridBagLayout());

        JLabel label = new JLabel(String.format("%d. %s", problemNumber, problem.prompt()));
        label.setFont(new Font("Arial", Font.BOLD, 20));

        List<BasicChoice> choices = problem.choices();
        // Add the button to the content pane of the JFrame

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        panel.add(label, gbc);
        for (int i = 0; i < choices.size(); ++i) {
            ChoiceButton button = createButton(choices.get(i));
            gbc.gridx = i % 2;
            gbc.gridy = 2 + i / 2;
            gbc.gridheight = 1;
            gbc.gridwidth = 2;
            panel.add(button.button(), gbc);
        }

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 400);  // Adjust the size as needed
        frame.setVisible(true);
    }

    private static ChoiceButton createButton(BasicChoice choice) {
        // Create a JButton
        return new ChoiceButton(choice);
    }
}
