package woowacourse.textTranslate.domain;

public enum TargetLanguage {
    ENGLISH("en", "영어"),
    JAPANESE("ja", "일본어"),
    CHINESE("zh-CN", "중국어");

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
