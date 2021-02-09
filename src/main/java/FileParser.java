import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListSet;

public class FileParser implements Runnable {

    private Map<String, ConcurrentSkipListSet<String>> values;
    private String filePath;

    public FileParser(String filePath, Map<String, ConcurrentSkipListSet<String>> values) {
        this.filePath = filePath;
        this.values = values;
    }

    @Override
    public void run() {

        File file = new File(filePath);
        
        if (file.isFile() && file.canRead()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                //reading first string
                String readString = br.readLine();
                String[] headers = readString.trim().split(";");

                //parsing first string
                for (String header : headers) {
                    if (!values.containsKey(header)) {
                        values.put(header, new ConcurrentSkipListSet<>());
                    }
                }

                String[] parsedString;

                //reading and parsing rest of the file
                while ((readString = br.readLine()) != null) {
                    parsedString = readString.trim().split(";");
                    for (int i = 0; i < parsedString.length; i++) {
                        values.get(headers[i]).add(parsedString[i]);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }
}
