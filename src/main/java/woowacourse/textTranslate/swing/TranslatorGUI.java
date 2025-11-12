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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.jetbrains.annotations.NotNull;

public class TranslatorGUI extends JFrame {

    private final JFrame frame;
    private final JTextField inputField;
    private final JButton translateButton;
    private final JTextArea resultArea;

    public TranslatorGUI() {
        this.frame = new JFrame("간단한 한글-영어 번역기");
        this.inputField = new  JTextField(30);
        this.translateButton = new JButton("번역하기");
        this.resultArea = new JTextArea(10, 30);

        init();
    }

    public void init() {
        // 프레임 기본 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        // 메인 영역
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        BorderFactory.createEmptyBorder(20, 20, 20, 20);

        // 입력 영역
        JPanel inputPanel = createInputPanel();

        // 버튼 영역
        JPanel buttonPanel = createButtonPanel();

        // 결과 영역
        JPanel resultPanel = createResultPanel();

        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(resultPanel);

        frame.add(mainPanel);
    }

    private @NotNull JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel inputLabel = new JLabel("한글 입력 : ");
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        return inputPanel;
    }

    private @NotNull JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(translateButton);
        return buttonPanel;
    }

    private @NotNull JPanel createResultPanel() {
        JPanel resultPanel = new JPanel(new BorderLayout());
        JLabel resultLabel = new JLabel("번역 결과 : ");
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        JScrollPane jScrollPane = new JScrollPane(resultArea);
        resultPanel.add(resultLabel, BorderLayout.NORTH);
        resultPanel.add(jScrollPane, BorderLayout.CENTER);
        return resultPanel;
    }

}
