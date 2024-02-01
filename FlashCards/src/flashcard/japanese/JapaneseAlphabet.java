package flashcard.japanese;

public final class JapaneseAlphabet {
    public final Long id;
    public final String japaneseChar;
    public final String romanPronounce;
    public final String chineseSource;

    public JapaneseAlphabet(Long id, String japaneseChar, String romanPronounce, String chineseSource) {

        this.id = id;
        this.japaneseChar = japaneseChar;
        this.romanPronounce = romanPronounce;
        this.chineseSource = chineseSource;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", japaneseChar, romanPronounce, chineseSource);
    }
}
