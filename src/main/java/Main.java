import java.util.Map;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        //map key is a value title / file name
        //mapped set contains values / file content
        Map<String, ConcurrentSkipListSet<String>> values = new ConcurrentHashMap<>();

        ExecutorService parsingExecutorService = Executors.newCachedThreadPool();

        //starting file parser for each file passed in arguments
        for (String filePath : args) {
            parsingExecutorService.submit(new FileParser(filePath, values));
        }

        //waiting for file parsers to finish
        parsingExecutorService.shutdown();
        parsingExecutorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);


        ExecutorService writingExecutorService = Executors.newCachedThreadPool();

        //starting file writer for each writer that needs to be written
        for (String fileName : values.keySet()) {
            writingExecutorService.submit(new FileWriter(fileName, values.get(fileName)));
        }

        //waiting for file writers to finish
        writingExecutorService.shutdown();
        writingExecutorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        System.out.println("Task completed successfully.");
    }
}
