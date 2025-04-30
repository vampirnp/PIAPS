import java.util.*;

// Интерфейс Component
interface AircraftComponent {
    int getTotalWeight();
    void add(AircraftComponent component);
    void remove(AircraftComponent component);
    AircraftComponent getChild(int index);
    String getDescription();
    AircraftComponent getParent();
    void setParent(AircraftComponent parent);
}

// Абстрактный класс для примитивов
abstract class Primitive implements AircraftComponent {
    protected AircraftComponent parent;

    @Override
    public void add(AircraftComponent component) {
        throw new UnsupportedOperationException("Cannot add to a primitive.");
    }

    @Override
    public void remove(AircraftComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a primitive.");
    }

    @Override
    public AircraftComponent getChild(int index) {
        throw new UnsupportedOperationException("Primitive has no children.");
    }

    @Override
    public AircraftComponent getParent() {
        return parent;
    }

    @Override
    public void setParent(AircraftComponent parent) {
        this.parent = parent;
    }
}

// Класс Passenger (Лист)
class Passenger extends Primitive {
    private String name;
    public int baggageWeight;
    private String type;

    public Passenger(String name, int baggageWeight, String type) {
        this.name = name;
        this.baggageWeight = baggageWeight;
        this.type = type;
    }

    @Override
    public int getTotalWeight() {
        return baggageWeight;
    }

    @Override
    public String getDescription() {
        return name + " (" + type + "), Baggage: " + baggageWeight + " kg";
    }
}

// Класс CrewMember (Лист)
class CrewMember extends Primitive {
    private String name;
    private String role;

    public CrewMember(String name, String role) {
        this.name = name;
        this.role = role;
    }

    @Override
    public int getTotalWeight() {
        return 0; // Пилоты и стюардессы не имеют багажа
    }

    @Override
    public String getDescription() {
        return name + " (" + role + ")";
    }
}

// Абстрактный класс Composite
abstract class AircraftComposite implements AircraftComponent {
    protected List<AircraftComponent> components = new ArrayList<>();
    protected String name;
    protected AircraftComponent parent;

    public AircraftComposite(String name) {
        this.name = name;
    }

    @Override
    public int getTotalWeight() {
        int totalWeight = 0;
        for (AircraftComponent component : components) {
            totalWeight += component.getTotalWeight();
        }
        return totalWeight;
    }

    @Override
    public void add(AircraftComponent component) {
        components.add(component);
        component.setParent(this);
    }

    @Override
    public void remove(AircraftComponent component) {
        components.remove(component);
        component.setParent(null);
    }

    @Override
    public AircraftComponent getChild(int index) {
        return components.get(index);
    }

    @Override
    public AircraftComponent getParent() {
        return parent;
    }

    @Override
    public void setParent(AircraftComponent parent) {
        this.parent = parent;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder(name + " contains:\n");
        for (AircraftComponent component : components) {
            sb.append(component.getDescription()).append("\n");
        }
        return sb.toString();
    }
}

// Класс Airplane, содержащий классы пассажиров
class Airplane extends AircraftComposite {
    private AircraftComposite firstClass;
    private AircraftComposite businessClass;
    private AircraftComposite economyClass;

    public Airplane() {
        super("Airplane");
        this.firstClass = new FirstClass();
        this.businessClass = new BusinessClass();
        this.economyClass = new EconomyClass();
        add(firstClass);
        add(businessClass);
        add(economyClass);
    }

    public void addPassengerToFirstClass(Passenger passenger) {
        firstClass.add(passenger);
    }

    public void addPassengerToBusinessClass(Passenger passenger) {
        businessClass.add(passenger);
    }

    public void addPassengerToEconomyClass(Passenger passenger) {
        economyClass.add(passenger);
    }

    public void checkAndAdjustWeight() {
        int maxAllowedWeight = 2000; // Пример максимальной допустимой загрузки
        if (getTotalWeight() > maxAllowedWeight) {
            System.out.println("Excess weight detected! Removing baggage from Economy Class.");
            // Логика удаления багажа из эконом класса
            while (getTotalWeight() > maxAllowedWeight) {
                for (int i = 0; i < economyClass.components.size(); i++) {
                    Passenger passenger = (Passenger) economyClass.getChild(i);
                    if (passenger.getTotalWeight() > 0) {
                        passenger.baggageWeight = 0;
                        break;
                    }
                }
            }
            System.out.println("Baggage removed. New Total Baggage Weight: " + getTotalWeight() + " kg");
        }
    }
}

// Классы для различных категорий пассажиров
class FirstClass extends AircraftComposite {
    public FirstClass() {
        super("First Class");
    }
}

class BusinessClass extends AircraftComposite {
    public BusinessClass() {
        super("Business Class");
    }
}

class EconomyClass extends AircraftComposite {
    public EconomyClass() {
        super("Economy Class");
    }
}

// Основной класс с методом main
public class Main {
    public static void main(String[] args) {
        // Создание самолета
        Airplane airplane = new Airplane();

        // Добавление пилотов и стюардесс
        airplane.add(new CrewMember("Vladimir Ilyin", "Pilot"));
        airplane.add(new CrewMember("Alexandr Ivanovich", "2nd Pilot"));
        for (int i = 1; i <= 6; i++) {
            airplane.add(new CrewMember("Stewardess " + i, "Stewardess"));
        }

        // Добавление пассажиров в классы
        for (int i = 1; i <= 10; i++) {
            airplane.addPassengerToFirstClass(new Passenger("First Class Passenger " + i, 30, "First Class"));
        }
        for (int i = 1; i <= 20; i++) {
            airplane.addPassengerToBusinessClass(new Passenger("Business Class Passenger " + i, 25, "Business Class"));
        }
        for (int i = 1; i <= 150; i++) {
            airplane.addPassengerToEconomyClass(new Passenger("Economy Class Passenger " + i, 15, "Economy Class"));
        }

        // Вывод информации о загрузке самолета
        System.out.println("Airplane Loading Map:");
        System.out.println(airplane.getDescription());
        System.out.println("Total Baggage Weight: " + airplane.getTotalWeight() + " kg");

        // Проверка превышения допустимой загрузки багажа
        airplane.checkAndAdjustWeight();
    }
}
