import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileParser {
    private ArrayList<MonitoredData> monitoredData = new ArrayList<>();
    private ArrayList<String> activitati = new ArrayList<>();

    public FileParser(String path) {
        try {
            read(path);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void write(String path, String s) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pw.write(s);
        pw.close();
    }

    public void read(String path) throws ParseException {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            List<String> list = stream.collect(Collectors.toList());
            for (String s : list) {
                String[] data = s.split("\t+");
                MonitoredData line = new MonitoredData(data[0], data[1], data[2]);
                monitoredData.add(line);
                if (!activitati.contains(data[2]))
                    activitati.add(data[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<MonitoredData> getMonitoredData() {
        return monitoredData;
    }

    public ArrayList<String> getActivitati() {
        return activitati;
    }
}
