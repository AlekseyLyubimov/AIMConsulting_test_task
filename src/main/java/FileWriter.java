import java.io.BufferedWriter;
import java.io.IOException;
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

        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(fileName))) {
            for (String value : values) {
                bw.write(value + ";");
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
