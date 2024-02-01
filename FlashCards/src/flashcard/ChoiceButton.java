package flashcard;

import javax.swing.*;

public class ChoiceButton {

    private final JButton button;
    private final BasicChoice choice;

    public ChoiceButton(BasicChoice choice) {
        this.choice = choice;
        button = new JButton(choice.toString());
        button.setName(choice.label());

        // Add an ActionListener to the button
        button.addActionListener(e -> choice.check());
    }

    public BasicChoice choice() {
        return choice;
    }

    public JButton button() {
        return button;
    }
}
