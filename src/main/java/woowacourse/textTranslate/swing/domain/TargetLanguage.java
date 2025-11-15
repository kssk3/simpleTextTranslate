package woowacourse.textTranslate.swing.domain;

public enum TargetLanguage {
    ENGLISH("en", "영어"),
    JAPANESE("jp", "일본어"),
    CHINESE("cn", "중국어");

    private final String code;
    private final String displayName;

    TargetLanguage(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }
}
