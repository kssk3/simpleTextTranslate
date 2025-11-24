package woowacourse.textTranslate.view;

import woowacourse.textTranslate.domain.TargetLanguage;

public interface TranslatorView {
    String getInputText();
    TargetLanguage getTargetLanguage();
    default void showWellComeMessage(){}
    default boolean askContinue(){return false;}
    void displayResult(String result);
    void displayError(String error);
    default void close(){}
}
