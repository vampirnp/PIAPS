public class Main {
    public static void main(String[] args) {
        PerformanceMonitor monitor = new PerformanceMonitor();
        Deanery deanery = new Deanery();
        monitor.registerObserver(deanery);

        Teacher teacher = new Teacher("Иванов");

        System.out.println("\n1 сценарий - Преподаватель не отправил данные");
        // Сценарий 1: Преподаватель не отправил данные
        monitor.weeklyCheck(); // Уведомление
        System.out.println("\n2 сценарий - Преподаватель отправил данные");
        teacher.submitPerformance(monitor);
        monitor.weeklyCheck(); // Уведомления
    }
}