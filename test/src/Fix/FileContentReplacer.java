package Fix;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class FileContentReplacer extends JFrame implements ActionListener {
	private JTextField FilePass;
	private JTextField Context;
	private JTextField Main_DB;
	private JTextField Sub_DB;
	private JRadioButton sqlserverButton;
	private JRadioButton postgresButton;
	private JButton browseButton;
	private JButton executeButton;

	public FileContentReplacer() {
		// ウィンドウの設定
		setTitle("resource.properties自動書き換え");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 200);

		// パネルの作成
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(7, 2));

		// ラベルとフィールドの追加
		panel.add(new JLabel("ファイルパス: "));
		FilePass = new JTextField();
		panel.add(FilePass);

		// ラベルとフィールドの追加
		panel.add(new JLabel("コンテキスト名: "));
		Context = new JTextField();
		panel.add(Context);

		panel.add(new JLabel("メインDB名: "));
		Main_DB = new JTextField();
		panel.add(Main_DB);

		panel.add(new JLabel("サブDB名: "));
		Sub_DB = new JTextField();
		panel.add(Sub_DB);

		panel.add(new JLabel("DB情報　※選択をしない場合デフォルト"));
		//見栄えの実装
		panel.add(new JLabel());
		
		sqlserverButton = new JRadioButton("SQLServer");
		postgresButton = new JRadioButton("Postgre");
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(sqlserverButton);
		buttonGroup.add(postgresButton);
		panel.add(sqlserverButton);
		panel.add(postgresButton);

		// ボタンの追加
		browseButton = new JButton("参照");
		browseButton.addActionListener(this);
		panel.add(browseButton);

		executeButton = new JButton("実行");
		executeButton.addActionListener(this);
		panel.add(executeButton);

		// フレームにパネルを追加
		add(panel);

	}

	public void actionPerformed(ActionEvent e) {
		//参照ボタン押下
		if (e.getSource() == browseButton) {
			// ファイルを開くダイアログを表示
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {

				// 参照にて選択したディレクトリをファイルパスのテキストに格納
				File directory1 = fileChooser.getSelectedFile();
				FilePass.setText(directory1.getAbsolutePath());
			}
		}
		// 実行ボタン押下
		if (e.getSource() == executeButton) {
			String directory = FilePass.getText();
			String context = Context.getText();
			String main_db = Main_DB.getText();
			String sub_db = Sub_DB.getText();
			boolean issqlserver = sqlserverButton.isSelected();
			boolean ispostgres = postgresButton.isSelected();

			if (directory.isEmpty() || context == null || main_db.isEmpty()
					|| sub_db.isEmpty()) {
				JOptionPane.showMessageDialog(this, "必要な情報を入力してください。");
				return;
			}

			File resourcePath = new File(directory);

			if (directory.isEmpty()) {
				JOptionPane.showMessageDialog(this, "resource.propertiesを指定してください");
				return;
			}

			// String filePath = ".\\esm_war\\WEB-INF\\src\\jp\\co\\softbrain\\wes\\resource.properties";
			// String backupFilePath = ".\\esm_war\\WEB-INF\\src\\jp\\co\\softbrain\\wes\\resource.properties.bk";

			// for (File files : file) {	
			//str インプットファイル

			String backupFilePath = directory.replace("\\resource.properties", "") + "\\resource.properties.bk";

			// File files = new File("C:\\temp\resource.properties.bk");
			//String backupFilePath = "C:\\temp\\resource.properties.bk";
			//resource.propertiesは既に存在しています
			//バックアップを作成し、削除します
			if (resourcePath.exists()) {

				try {
					// ファイルのコピー
					Path sourcePath = resourcePath.toPath();
					Path destinationPath = new File(backupFilePath).toPath();
					Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

					// ファイルの削除
					resourcePath.delete();
					//ファイルのコピーと削除が正常終了
				} catch (IOException exx) {
					//ファイルのコピーまたは削除中にエラーが発生しました:
					JOptionPane.showMessageDialog(this, "ファイルのコピーまたは削除中にエラーが発生しました" + exx.getMessage());
					return;
				}

			}

			String inputFilePath = directory.replace("\\resource.properties", "") + "\\resource.properties.template";

			try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));

					BufferedWriter writer = new BufferedWriter(new FileWriter(resourcePath))) {
				String baseDirPath = "C:\\esm_log\\" + context;
				// ディレクトリの作成
				createDirectory(baseDirPath);
				createDirectory(baseDirPath + "\\esm_lib");
				createDirectory(baseDirPath + "\\ldap");
				createDirectory(baseDirPath + "\\import");
				createDirectory(baseDirPath + "\\job");
				createDirectory(baseDirPath + "\\operation");
				createDirectory(baseDirPath + "\\rest");
				createDirectory(baseDirPath + "\\security");
				createDirectory(baseDirPath + "\\temp\\analytics\\cache");
				
				while ((directory = reader.readLine()) != null) {
					boolean existsConvTgt = false;
					String line = directory.trim();

					if (line.startsWith("/n")) {
						writer.write(line);
						writer.newLine();
						continue;
					}
					if (line.contains("jdbc.username")) {
						line = line.replace("esm", main_db);
						existsConvTgt = true;
					} else if (line.contains("jdbc.password")) {
						line = line.replace("esm", main_db);
						existsConvTgt = true;
					} else if (line.contains("jdbc.sub.username")) {
						line = line.replace("esmSub", sub_db);
						existsConvTgt = true;
					} else if (line.contains("jdbc.sub.password")) {
						line = line.replace("esmSub", sub_db);
						existsConvTgt = true;
					} else if (line.contains("${contextName}")) {
						line = line.replace("${contextName}", context);
						existsConvTgt = true;
						//要確認
					} else if (line.contains("${contextFilePath}")) {
						line = line.replace("${contextFilePath}", "~dp0esm_war\\=/");
						existsConvTgt = true;
					} else if (line.contains("D:/temp/")) {
						line = line.replace("D:/temp/", "C:/esm_log/" + context + "/temp/");
						existsConvTgt = true;
					} else if (line.contains("D:/esm_log/")) {
						line = line.replace("D:/esm_log/", "C:/esm_log/" + context + "/");
						existsConvTgt = true;
					}
					//SQLSERVER独自の処理
					if (issqlserver) {
					} //Postgres独自の処理
					else if (ispostgres) {

					}

					if (!existsConvTgt) {
						writer.write(directory);
						writer.newLine();
					} else {
						writer.write(line);
						writer.newLine();
					}

				}

			} catch (IOException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "ファイルの書き換えが失敗しました");
				return;
			}

			JOptionPane.showMessageDialog(this, "ファイルの書き換えが完了しました。");

		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			FileContentReplacer frame = new FileContentReplacer();
			frame.setVisible(true);
		});
	}

	private static void createDirectory(String path) {
		File directory = new File(path);
		if (!directory.exists()) {
			if (directory.mkdirs()) {
				System.out.println("ディレクトリを作成しました: " + path);
			} else {
				System.out.println("ディレクトリの作成に失敗しました: " + path);
			}
		} else {
			System.out.println("ディレクトリは既に存在しています: " + path);
		}
	}
}
