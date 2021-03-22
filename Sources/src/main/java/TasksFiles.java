import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TasksFiles {
    private ArrayList<MonitoredData> monitoredData;
    private ArrayList<String> activitati;
    private FileParser fp;

    public TasksFiles(String path) {
        fp = new FileParser(path);
        this.monitoredData = fp.getMonitoredData();
        this.activitati = fp.getActivitati();
    }

    public void createTask1() {
        String s = "";
        for (MonitoredData m : monitoredData) {
            s += m.toString() + "\n";
        }
        fp.write("task_1.txt", s);
    }

    public void createTask2() {
        long nr = monitoredData.stream().map(m -> m.getStartDay()).distinct().count();
        fp.write("task_2.txt","Distinct days that appear in the monitoring data: " + nr);
    }

    public HashMap<String, Integer> createTask3() {
        HashMap<String, Integer> activities = new HashMap<>();
        List<String> allActivities = monitoredData.stream().map(m -> m.getActivity()).collect(Collectors.toList());
        activitati.forEach(st -> activities.put(st,(int)allActivities.stream().filter(act -> act.equals(st)).count()));

        String s = "Number of times each activity has appeared over the monitoring period:\n\n";
        for (HashMap.Entry<String, Integer> entry : activities.entrySet())
            s += entry.getKey() + " " + entry.getValue()+"\n";
        fp.write("task_3.txt", s);
        return activities;
    }

    public HashMap<Integer, HashMap<String,Integer>> createTask4() {
        HashMap<Integer, HashMap<String,Integer>> activityCountDays = new HashMap<>();
        ArrayList<String> allStartDays = new ArrayList<>();
        ArrayList<String> startDays = new ArrayList<>();
        monitoredData.forEach(m -> allStartDays.add(m.getStartDay()));

        allStartDays.forEach( str -> {
            if (!startDays.contains(str))
                startDays.add(str);
        });

        startDays.forEach(day -> {
            HashMap<String, Integer> activityCount = new HashMap<>();
            activitati.forEach( act ->
                    activityCount.put(act, (int)monitoredData.stream().filter(m -> (m.getEndDay().equals(day) || m.getStartDay().equals(day)) && m.getActivity().equals(act)).count())
            );
            activityCountDays.put(activityCountDays.size() + 1, activityCount);
        });

        String s = "Number of times each activity has appeared for each day over the monitoring period:\n\n";
        for (HashMap.Entry<Integer, HashMap<String, Integer>> a : activityCountDays.entrySet()) {
            s += "Ziua " + a.getKey() + "\n";
            for (HashMap.Entry<String, Integer> b : a.getValue().entrySet())
                s += b.getKey() + " " + b.getValue() + "\n";
            s += "\n";
        }
        fp.write("task_4.txt", s);
        return activityCountDays;
    }

    public HashMap<String, Duration> createTask5() {
        HashMap<String, Duration> durations = new HashMap<>();
        activitati.forEach( act -> {
            final long[] time = {0};
            List<Long> duration = monitoredData.stream().filter(m -> act.equals(m.getActivity())).map(m-> m.getDuration()).collect(Collectors.toList());
            duration.forEach(dur -> time[0] += dur);
            Duration dur = Duration.ofMillis(time[0]);
            durations.put(act, dur);
        });

        String s = "Entire duration of each activity over the monitoring period:\n\n";
        for (HashMap.Entry<String, Duration> d : durations.entrySet()) {
            long nrSecunde = d.getValue().toSeconds();
            String st = String.format("%d:%02d:%02d", nrSecunde / 3600, (nrSecunde % 3600) / 60, (nrSecunde % 60));
            s += d.getKey() + " " + st + "\n";
        }
        fp.write("task_5.txt", s);
        return durations;
    }

    public List<String> createTask6() {
        List<String> activities = new ArrayList<>();

        activitati.forEach( act -> {
            List<Long> duration = monitoredData.stream().filter(m -> act.equals(m.getActivity())).map(m-> m.getDuration()).collect(Collectors.toList());
            long nr = duration.stream().filter(dur -> dur/(1000*60) < 5).count();
            int nrTotal = duration.size();
            if (nr > 0.90 * nrTotal)
                activities.add(act);
        });

        String s = "Activities that have more than 90% of the monitoring records with duration less than 5 minutes:\n\n";
        for (String st : activities) {
            s += st + "\n";
        }
        fp.write("task_6.txt", s);
        return activities;
    }

}
