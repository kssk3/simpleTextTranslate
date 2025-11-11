package woowacourse.textTranslate.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TranslatorGUI extends JFrame {

    private Translator translator;

    public TranslatorGUI(Translator translator) {
        this.translator = translator;
        init();
    }

    public void init() {
        setTitle("간단한 텍스트 번역기");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 메인 영역
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        BorderFactory.createEmptyBorder(20, 20, 20, 20);

        // 입력 영역
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(10, 10));

        JLabel inputLabel = new JLabel("번역할 텍스트");
        JTextField inputTextField = new JTextField(30);
        inputTextField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(inputTextField, BorderLayout.CENTER);

        setVisible(true);
    }


    public static void main(String[] args) {
        TranslatorGUI translatorGUI = new TranslatorGUI(
                new Translator(new KoreanText("한글"), new TargetText("English")));

    }
}
