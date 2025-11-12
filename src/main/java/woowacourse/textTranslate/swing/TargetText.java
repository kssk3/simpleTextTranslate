package woowacourse.textTranslate.swing;

public class TargetText {

    private final String Language;

    public TargetText(String Language) {
        this.Language = Language;
    }

    private void validate(String language) {
        if(language == null ||  language.isEmpty()) {
            throw new IllegalArgumentException("번역할 언어를 입력해주세요");
        }

        if (KoreanRegex.isKoreanRegex(language)) {
            throw new IllegalArgumentException("한국어로 번역할 수 없습니다.");
        }
    }

    public String getLanguage() {
        return Language;
    }
}
