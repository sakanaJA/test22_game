package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUpdater {
    public static void main(String[] args) {
        String filePath = "C:/temp/file.txt"; // ファイルのパス

        // ファイル読み込み
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // ここでファイルの内容を書き換える処理を行う
                // 例えば、文字列の置換や追加などを行うことができます
                line = line.replaceAll("oldText", "newText");
                fileContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // ファイル書き込み
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(fileContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ファイルの書き換えが完了しました。");
    }
}