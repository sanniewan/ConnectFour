package flashcard;

import javax.swing.*;
import java.awt.*;

public class ChoiceButton {
    private final Font customFont = new Font("Arial", Font.PLAIN, 20);
    private final JButton button;
    private final BasicChoice choice;

    public ChoiceButton(BasicChoice choice) {
        this.choice = choice;
        button = new JButton(choice.toString());
        button.setFont(customFont);
        button.setName(choice.label());

        // Add an ActionListener to the button
        button.addActionListener(e -> {
            if (choice.checked()) {
                choice.uncheck();
                button.setForeground(UIManager.getColor("Button.foreground")); // Reset to default color
                System.out.println("Unchecked color");
            } else {
                choice.check();
                button.setForeground(Color.RED);
                System.out.println("Checked color");
            }
        });
    }

    public BasicChoice choice() {
        return choice;
    }

    public JButton button() {
        return button;
    }
}
