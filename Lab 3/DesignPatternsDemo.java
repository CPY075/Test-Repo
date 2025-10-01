import java.util.*;

// 1. SINGLETON PATTERN
class ConfigurationManager {
    private static ConfigurationManager instance;
    private Map<String, String> config = new HashMap<>();

    private ConfigurationManager() {
        config.put("appName", "TaskManagerApp");
        config.put("version", "1.0");
    }

    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    public String getConfig(String key) {
        return config.get(key);
    }

    public void setConfig(String key, String value) {
        config.put(key, value);
    }
}

// Factory
interface Vehicle {
    void drive();
}

class Car implements Vehicle {
    public void drive() { System.out.println("Driving a Car 🚗"); }
}

class Bike implements Vehicle {
    public void drive() { System.out.println("Riding a Bike 🏍️"); }
}

class Truck implements Vehicle {
    public void drive() { System.out.println("Driving a Truck 🚚"); }
}

class VehicleFactory {
    public static Vehicle getVehicle(String type) {
        switch (type.toLowerCase()) {
            case "car": return new Car();
            case "bike": return new Bike();
            case "truck": return new Truck();
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}

// notification
interface Observer {
    void update(String message);
}

class EmailNotifier implements Observer {
    public void update(String message) { System.out.println("📧 Email: " + message); }
}

class SMSNotifier implements Observer {
    public void update(String message) { System.out.println("📱 SMS: " + message); }
}

class PushNotifier implements Observer {
    public void update(String message) { System.out.println("🔔 Push Notification: " + message); }
}

class TaskManager {
    private List<Observer> observers = new ArrayList<>();
    private String taskStatus;

    public void addObserver(Observer obs) { observers.add(obs); }
    public void removeObserver(Observer obs) { observers.remove(obs); }

    public void setTaskStatus(String status) {
        this.taskStatus = status;
        notifyAllObservers();
    }

    private void notifyAllObservers() {
        for (Observer obs : observers) {
            obs.update("Task status changed to: " + taskStatus);
        }
    }
}

// STRATEGY
interface SortStrategy {
    void sort(int[] arr);
}

class QuickSortStrategy implements SortStrategy {
    public void sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        System.out.println("Sorted using QuickSort");
    }

    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p - 1);
            quickSort(arr, p + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high], i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
            }
        }
        int tmp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = tmp;
        return i + 1;
    }
}

class MergeSortStrategy implements SortStrategy {
    public void sort(int[] arr) {
        mergeSort(arr, 0, arr.length - 1);
        System.out.println("Sorted using MergeSort");
    }

    private void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int mid = (l + r) / 2;
            mergeSort(arr, l, mid);
            mergeSort(arr, mid + 1, r);
            merge(arr, l, mid, r);
        }
    }

    private void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1, n2 = r - m;
        int[] L = new int[n1], R = new int[n2];
        for (int i = 0; i < n1; i++) L[i] = arr[l + i];
        for (int j = 0; j < n2; j++) R[j] = arr[m + 1 + j];

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) arr[k++] = (L[i] <= R[j]) ? L[i++] : R[j++];
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }
}

class SortContext {
    private SortStrategy strategy;

    public void setStrategy(SortStrategy strategy) { this.strategy = strategy; }
    public void executeSort(int[] arr) { strategy.sort(arr); }
}

// MAIN DEMO
public class DesignPatternsDemo {
    public static void main(String[] args) {
        // Singleton
        ConfigurationManager config = ConfigurationManager.getInstance();
        System.out.println("App: " + config.getConfig("appName") + " | Version: " + config.getConfig("version"));

        // Factory
        Vehicle car = VehicleFactory.getVehicle("car");
        car.drive();
        Vehicle bike = VehicleFactory.getVehicle("bike");
        bike.drive();

        // Observer
        TaskManager taskManager = new TaskManager();
        taskManager.addObserver(new EmailNotifier());
        taskManager.addObserver(new SMSNotifier());
        taskManager.addObserver(new PushNotifier());
        taskManager.setTaskStatus("In Progress");
        taskManager.setTaskStatus("Completed ✅");

        // Strategy
        int[] data = {34, 7, 23, 32, 5, 62};
        SortContext sorter = new SortContext();

        sorter.setStrategy(new QuickSortStrategy());
        sorter.executeSort(data.clone());

        sorter.setStrategy(new MergeSortStrategy());
        sorter.executeSort(data.clone());
    }
}
