import java.util.ArrayList;
import java.util.List;

public class PerformanceMonitor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private boolean dataSubmitted = false;

    public void submitPerformance(Teacher teacher) {
        dataSubmitted = true;
        System.out.println("Данные успеваемости от " + teacher.getName() + " получены.");
    }

    public void weeklyCheck() {
        if (!dataSubmitted) {
            notifyObservers();
        }
        dataSubmitted = false; // Сброс на следующую неделю
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
