import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentSkipListSet;

public class FileWriter implements Runnable {

    private String fileName;
    private ConcurrentSkipListSet<String> values;

    public FileWriter(String fileName, ConcurrentSkipListSet<String> values) {
        this.fileName = fileName;
        this.values = values;
    }

    @Override
    public void run() {

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(fileName + ".csv"), StandardCharsets.UTF_8))) {

            for (String value : values) {
                bw.write(value + ";");
            }

            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
