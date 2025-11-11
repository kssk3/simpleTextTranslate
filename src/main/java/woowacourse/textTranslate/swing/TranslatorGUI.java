package woowacourse.textTranslate.swing;

import javax.swing.JFrame;

public class TranslatorGUI extends JFrame {

    private Translator translator;

    public TranslatorGUI(Translator translator) {
        setTitle("간단한 텍스트 번역기");
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        this.translator = translator;
    }

    public static void main(String[] args) {
        TranslatorGUI translatorGUI = new TranslatorGUI(
                new Translator(new KoreanText("한글"), new TargetText("English")));

    }
}
