package woowacourse.textTranslate.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TranslatorGUI extends JFrame {

    private Translator translator;
    private JTextField inputTextField;
    private JLabel resultLabel;
    private JButton translateButton;

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
        inputTextField = new JTextField(20);
        inputTextField.setFont(new Font("맑은 고딕", Font.PLAIN, 16));

        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(inputTextField, BorderLayout.CENTER);

        // 버튼 영역
        JPanel buttonPanel = new JPanel();
        translateButton = new JButton("번역하기");
        translateButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        buttonPanel.add(translateButton);

        // 결과 영역
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout(10, 10));
        resultPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel resultTitleLabel = new JLabel("번역 결과");
        resultLabel = new JLabel("");
        resultLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        resultLabel.setForeground(new Color(0, 102, 204));

        resultPanel.add(resultTitleLabel, BorderLayout.NORTH);
        resultPanel.add(resultLabel, BorderLayout.CENTER);

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        mainPanel.add(resultPanel);

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        TranslatorGUI translatorGUI = new TranslatorGUI(
                new Translator(new KoreanText("한글"), new TargetText("English")));

    }
}
