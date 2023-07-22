package Fix;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FileContentEditor extends JFrame implements ActionListener {
    private JTextArea textArea;
    private JButton openButton;
    private JButton saveButton;

    public FileContentEditor() {
        // ウィンドウの設定
        setTitle("ファイル内容編集");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // テキストエリアの作成
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        // ボタンの作成
        openButton = new JButton("ファイルを開く");
        saveButton = new JButton("ファイル書き換え終了");
        openButton.addActionListener(this);
        saveButton.addActionListener(this);

        // パネルにボタンを配置
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        // コンポーネントをフレームに追加
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            // ファイルを開くダイアログを表示
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                // 選択されたファイルの内容をテキストエリアに表示
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                readFileContent(filePath);
            }
        } else if (e.getSource() == saveButton) {
            // ファイルを保存ダイアログを表示
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                // テキストエリアの内容を選択されたファイルに保存
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                writeFileContent(filePath);
            }
        }
    }

    private void readFileContent(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            textArea.setText(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFileContent(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String content = textArea.getText();
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // GUIを表示
        FileContentEditor editor = new FileContentEditor();
        editor.setVisible(true);
    }
}
