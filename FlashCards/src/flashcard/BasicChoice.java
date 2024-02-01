package flashcard;

public class BasicChoice {
    private final String label;
    private final String description;
    private final boolean correctChoice;
    private boolean checked;

    public BasicChoice(String label, String description, boolean correctChoice) {

        this.label = label;
        this.description = description;
        this.correctChoice = correctChoice;
    }

    public void check() {
        this.checked = true;
    }

    public String label() {
        return label;
    }

    public String description() {
        return description;
    }

    public boolean correctChoice() {
        return correctChoice;
    }

    @Override
    public String toString() {
        return this.label() + ".\t" + this.description();
    }

    public boolean checked() {
        return checked;
    }
}
