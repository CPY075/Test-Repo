import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;


interface Vehicle {
    void startEngine();
    void stopEngine();
}

class Car implements Vehicle {
    @Override public void startEngine() { System.out.println("Car engine started"); }
    @Override public void stopEngine()  { System.out.println("Car engine stopped"); }
}

class Bike implements Vehicle {
    @Override public void startEngine() { System.out.println("Bike engine started"); }
    @Override public void stopEngine()  { System.out.println("Bike engine stopped"); }
}

class VehicleService {
    public void start(Vehicle v) { v.startEngine(); }
    public void stop (Vehicle v) { v.stopEngine(); }
    public void startAll(Vehicle[] vs) { for (Vehicle v : vs) v.startEngine(); }
    public void stopAll (Vehicle[] vs) { for (Vehicle v : vs) v.stopEngine();  }
}

interface Task {
    String getName();
    void execute();
}

class EmailTask implements Task {
    private final String to, subject, body;
    public EmailTask(String to, String subject, String body) {
        this.to = to; this.subject = subject; this.body = body;
    }
    @Override public String getName() { return "EmailTask"; }
    @Override public void execute() {
        System.out.println("Sending email to " + to + " | " + subject + " | " + body);
    }
}

class ReportGenerationTask implements Task {
    private final String reportType;
    public ReportGenerationTask(String reportType) { this.reportType = reportType; }
    @Override public String getName() { return "ReportGenerationTask"; }
    @Override public void execute() {
        System.out.println("Generating report: " + reportType);
    }
}

class TaskProcessor {
    public void process(Task task) {
        System.out.println("Processing: " + task.getName());
        task.execute();
    }
    public void processAll(List<Task> tasks) {
        for (Task t : tasks) process(t);
    }
}

public class Lab2Frontend extends JFrame {
    private final JTextArea log = new JTextArea(14, 48);

    private final Vehicle car = new Car();
    private final Vehicle bike = new Bike();
    private final VehicleService vehicleService = new VehicleService();

    private final Task email  = new EmailTask("student@example.com", "Hello", "Welcome!");
    private final Task report = new ReportGenerationTask("Weekly Sales");
    private final TaskProcessor taskProcessor = new TaskProcessor();

    public Lab2Frontend() {
        super("Lab 2 Frontend — Interfaces & Tasks");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(12, 12));

        /* --- Buttons --- */
        JPanel top = new JPanel(new GridLayout(2, 1, 8, 8));
        JPanel vehicleRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JButton startCar  = new JButton("Start Car");
        JButton stopCar   = new JButton("Stop Car");
        JButton startBike = new JButton("Start Bike");
        JButton stopBike  = new JButton("Stop Bike");
        JButton startBoth = new JButton("Start All Vehicles");
        JButton stopBoth  = new JButton("Stop All Vehicles");

        vehicleRow.add(new JLabel("Vehicles:"));
        vehicleRow.add(startCar); vehicleRow.add(stopCar);
        vehicleRow.add(startBike); vehicleRow.add(stopBike);
        vehicleRow.add(startBoth); vehicleRow.add(stopBoth);

        JPanel taskRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        JButton runEmail  = new JButton("Run EmailTask");
        JButton runReport = new JButton("Run ReportTask");
        JButton runAll    = new JButton("Process All Tasks");
        JButton clear     = new JButton("Clear Log");

        taskRow.add(new JLabel("Tasks:"));
        taskRow.add(runEmail); taskRow.add(runReport); taskRow.add(runAll);
        taskRow.add(Box.createHorizontalStrut(16));
        taskRow.add(clear);

        top.add(vehicleRow);
        top.add(taskRow);
        add(top, BorderLayout.NORTH);

        log.setEditable(false);
        log.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(log,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
            BorderLayout.CENTER);


        startCar.addActionListener(e -> {
            vehicleService.start(car);
            append("Car: engine started");
        });
        stopCar.addActionListener(e -> {
            vehicleService.stop(car);
            append("Car: engine stopped");
        });
        startBike.addActionListener(e -> {
            vehicleService.start(bike);
            append("Bike: engine started");
        });
        stopBike.addActionListener(e -> {
            vehicleService.stop(bike);
            append("Bike: engine stopped");
        });
        startBoth.addActionListener(e -> {
            vehicleService.startAll(new Vehicle[]{car, bike});
            append("All vehicles started");
        });
        stopBoth.addActionListener(e -> {
            vehicleService.stopAll(new Vehicle[]{car, bike});
            append("All vehicles stopped");
        });

        runEmail.addActionListener(e -> {
            taskProcessor.process(email);
            append("Processed EmailTask");
        });
        runReport.addActionListener(e -> {
            taskProcessor.process(report);
            append("Processed ReportGenerationTask");
        });
        runAll.addActionListener(e -> {
            taskProcessor.processAll(Arrays.asList(email, report));
            append("Processed all tasks");
        });
        clear.addActionListener(e -> log.setText(""));

        pack();
        setLocationRelativeTo(null);
    }

    private void append(String s) {
        log.append(s + "\n");
        log.setCaretPosition(log.getDocument().getLength());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Lab2Frontend().setVisible(true));
    }
}
