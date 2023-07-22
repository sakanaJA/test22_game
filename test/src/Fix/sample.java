package Fix;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class sample {
    public static void main(String[] args) {
        String inputFilePath = "./esm_war/WEB-INF/src/jp/co/softbrain/wes/resource.properties.template";
        String outputFilePath = "./esm_war/WEB-INF/src/jp/co/softbrain/wes/resource.properties";
        String inputLine;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            while ((inputLine = reader.readLine()) != null) {
                boolean existsConvTgt = false;
                String line = inputLine.trim();

                if (line.startsWith("/n")) {
                    writer.write(line);
                    writer.newLine();
                    continue;
                }

                if (line.contains("jdbc.username jdbc.password")) {
                    line = line.replace("esm", "MAIN_DB");
                    existsConvTgt = true;
                } else if (line.contains("jdbc.sub.username jdbc.sub.password")) {
                    line = line.replace("esmSub", "SUB_DB");
                    existsConvTgt = true;
                } else if (line.contains("${contextName}")) {
                    line = line.replace("${contextName}", "CONTEX");
                    existsConvTgt = true;
                } else if (line.contains("${contextFilePath}")) {
                    line = line.replace("${contextFilePath}", "PROJECT_PATH");
                    existsConvTgt = true;
                } else if (line.contains("D:/temp/")) {
                    line = line.replace("D:/temp/", "C:/esm_log/" + "CONTEXT" + "/temp/");
                    existsConvTgt = true;
                } else if (line.contains("D:/esm_log/")) {
                    line = line.replace("D:/esm_log/", "C:/esm_log/" + "CONTEXT" + "/");
                    existsConvTgt = true;
                }

                if (!existsConvTgt) {
                    writer.write(inputLine);
                    writer.newLine();
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
