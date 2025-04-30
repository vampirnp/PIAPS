public class Teacher {
    private String name;

    public Teacher(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void submitPerformance(PerformanceMonitor monitor) {
        monitor.submitPerformance(this);
    }
}
