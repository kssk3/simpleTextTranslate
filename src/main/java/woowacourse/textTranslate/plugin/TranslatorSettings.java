package woowacourse.textTranslate.plugin;


import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "KoreanTranslatorSettings",
        storages = @Storage("KoreanTranslator.xml")
)
public class TranslatorSettings implements PersistentStateComponent<TranslatorSettings.State> {

    public static class State {
        public String kakaoApiKey = "";
        public boolean autoTranslate = false;
        public int translationDelay = 500; // Ms
    }

    private State state = new State();

    public static TranslatorSettings getInstance() {
        return ApplicationManager.getApplication().getService(TranslatorSettings.class);
    }

    @Override
    public @Nullable State getState() {
        return this.state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }

}
