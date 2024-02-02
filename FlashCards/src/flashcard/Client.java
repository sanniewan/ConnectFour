package flashcard;

import flashcard.japanese.JapaneseProblemBank;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Client {
    private static boolean buttonClicked = false;
    private static boolean exit = false;
    private static int testSize = 1;

    public static void main(String[] args) {

        AbstractProblemBank bank = new JapaneseProblemBank(4);
        while (true) {
            examination(bank);
            presentTerminate();
            if (exit) {
                break;
            }
        }
        System.out.println("Done!");
    }

    private static void presentTerminate() {
        buttonClicked = false;
        JButton continueButton = getBlockingButton("Continue");
        continueButton.addActionListener(e -> exit = false);

        JButton exitButton = getBlockingButton("Exit");
        exitButton.addActionListener(e -> exit = true);

        JFrame frame = new JFrame("Grades");
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(continueButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(exitButton, gbc);

        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 1400);  // Adjust the size as needed
        frame.setVisible(true);
        waitForButtonClick();
    }

    private static void examination(AbstractProblemBank bank) {
        GradingSheet grade = new GradingSheet();
        for (int i = 0; i < testSize; ++i) {
            AbstractProblem problem = bank.generate();
            int problemNumber = i + 1;
            System.out.printf("%d. ", problemNumber);
            System.out.println(problem);
            grade.record(problem);
            present(problemNumber, problem);
        }
        present(grade);
        System.out.println(grade);
    }

    private static void present(GradingSheet grade) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 10;

        String summaryHtml = String.format("<html>%s</html>", grade.summary().replace("\n", "<br>"));
        JLabel summary = new JLabel(summaryHtml);
        summary.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(summary, gbc);

        JFrame frame1 = new JFrame("Grades");
        frame1.getContentPane().add(panel);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(900, 1400);  // Adjust the size as needed
        frame1.setVisible(true);

        for (String incorrect : grade.incorrect()) {
            displayIncorrect(summaryHtml, incorrect);
        }
    }

    private static void displayIncorrect(String summaryHtml, String incorrect) {
        buttonClicked = false;
        JFrame frame = new JFrame("Grades");
        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 10;

        JLabel summary = new JLabel(summaryHtml);
        summary.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(summary, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 10;

        String incorrectHtml = String.format("<html>%s</html>", incorrect.replace("\n",
                "<br>"));
        JLabel incorrectLabel = new JLabel(incorrectHtml);
        incorrectLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(incorrectLabel, gbc);
        frame.getContentPane().add(panel);
        frame.setSize(900, 1400);  // Adjust the size as needed

        JButton submitButton = getBlockingButton("Confirm");
        gbc.gridy = 2;
        panel.add(submitButton, gbc);
        frame.setVisible(true);
        waitForButtonClick();
    }

    private static JButton getBlockingButton(String name) {
        Font customFont = new Font("Arial", Font.PLAIN, 20);
        JButton button = new JButton(name);
        button.setFont(customFont);
        button.addActionListener(e -> {
            synchronized (Client.class) {
                buttonClicked = true;
                Client.class.notifyAll();
            }
        });
        return button;
    }

    private static void present(int problemNumber, AbstractProblem problem) {
        buttonClicked = false;
        JFrame frame = new JFrame("Test on ...");
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
        JButton submitButton = getBlockingButton("Submit");
        gbc.gridx = 1;
        panel.add(submitButton, gbc);

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
        frame.setSize(900, 1400);  // Adjust the size as needed
        frame.setVisible(true);
        waitForButtonClick();
    }

    private static ChoiceButton createButton(BasicChoice choice) {
        // Create a JButton
        return new ChoiceButton(choice);
    }

    private static void waitForButtonClick() {
        synchronized (Client.class) {
            while (!buttonClicked) {
                try {
                    Client.class.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
