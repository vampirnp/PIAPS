import java.util.*;


// Класс Passenger
abstract class Passenger {
    protected String name;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Классы для различных типов пассажиров
class AdultPassenger extends Passenger {
    public AdultPassenger(String name) {
        super(name);
    }
}

class ChildPassenger extends Passenger {
    public ChildPassenger(String name) {
        super(name);
    }
}

class PrivilegedPassenger extends Passenger {
    public PrivilegedPassenger(String name) {
        super(name);
    }
}

// Абстрактный класс Driver
abstract class Driver {
    public abstract void drive();
}

// Классы водителей
class BusDriver extends Driver {
    private static BusDriver instance;

    private BusDriver() {}

    public static synchronized BusDriver getInstance() {
        if (instance == null) {
            instance = new BusDriver();
        }
        return instance;
    }

    @Override
    public void drive() {
        System.out.println("Водитель автобуса ведет автобус.");
    }
}

class TaxiDriver extends Driver {
    private static TaxiDriver instance;

    private TaxiDriver() {}

    public static synchronized TaxiDriver getInstance() {
        if (instance == null) {
            instance = new TaxiDriver();
        }
        return instance;
    }

    @Override
    public void drive() {
        System.out.println("Водитель такси ведет такси.");
    }
}

// Абстрактный класс Vehicle
abstract class Vehicle {
    protected Driver driver;
    protected List<Passenger> passengers = new ArrayList<>();
    protected int passengerLimit;
    protected boolean hasChildSeat = false;

    public void setDriver(Driver driver) {
        if (this.driver != null) {
            System.out.println("Водитель уже назначен.");
            return;
        }
        this.driver = driver;
    }

    public boolean addPassenger(Passenger passenger ) {


        if (passengers.size() < passengerLimit) {
            passengers.add(passenger);
            System.out.println("Добавлен новый пассажир, занято мест " + passengers.size());
            if (passenger instanceof ChildPassenger) {
                hasChildSeat = false;
            }
            return true;
        } else {

            System.out.println("Лимит пассажиров превышен. Пассажир " + passenger.getName() + " не может быть добавлен.");

            //if (Taxilimit){
            //    System.out.println("Лимит пассажиров для такси превышен. Пассажир " + passenger.getName() + " не может поехать вызовите новое!");
            //}

            return false;
        }
    }

    public boolean isReadyToDepart() {
        return driver != null && !passengers.isEmpty();
    }

    public void depart() {
        if (isReadyToDepart()) {
            if (this instanceof Taxi && !hasChildSeat) {
                System.out.println("Такси не может отправиться без детского кресла.");
                return;
            }
            System.out.println("Транспортное средство с " + passengers.size() + " пассажирами отправляется.");
            driver.drive();
        } else {
            System.out.println("Транспортное средство не готово к отправлению.");
        }
    }
}

// Классы Bus и Taxi
class Bus extends Vehicle {
    public Bus() {
        this.passengerLimit = 30;
    }
}

class Taxi extends Vehicle {
    public Taxi() {
        this.passengerLimit = 4;
    }
}

// Интерфейс Builder
interface VehicleBuilder {
    void buildDriver(Driver driver);
    void buildPassenger(Passenger passenger);
    Vehicle getVehicle();
}

// Конкретный строитель для автобуса
class BusBuilder implements VehicleBuilder {
    private Bus bus = new Bus();

    @Override
    public void buildDriver(Driver driver) {
        bus.setDriver(driver);
    }

    @Override
    public void buildPassenger(Passenger passenger) {
        bus.addPassenger(passenger);
    }

    @Override
    public Bus getVehicle() {
        return bus;
    }
}

// Конкретный строитель для такси
class TaxiBuilder implements VehicleBuilder {
    private Taxi taxi = new Taxi();

    @Override
    public void buildDriver(Driver driver) {
        taxi.setDriver(driver);
    }

    @Override
    public void buildPassenger(Passenger passenger) {
        taxi.addPassenger(passenger);
    }

    @Override
    public Taxi getVehicle() {
        return taxi;
    }
}

// Класс Director
class Director {
    public void constructBus(VehicleBuilder builder, Driver driver, List<Passenger> passengers) {
        builder.buildDriver(driver);
        for (Passenger passenger : passengers) {
            builder.buildPassenger(passenger);
        }
    }

    public void constructTaxi(VehicleBuilder builder, Driver driver, List<Passenger> passengers) {
        builder.buildDriver(driver);
        for (Passenger passenger : passengers) {
            builder.buildPassenger(passenger);
        }
    }
}

// Основной класс с методом main
public class Main {
    public static void main(String[] args) {
        Director director = new Director();

        // Создание автобуса
        BusBuilder busBuilder = new BusBuilder();
        Driver busDriver = BusDriver.getInstance();
        List<Passenger> busPassengers = new ArrayList<>();

        System.out.print("Фабрика автовокзала \n");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество взрослых пассажиров для автобуса (число): ");
        int numberAd = scanner.nextInt();
        for (int i = 0; i < numberAd; i++) {
            busPassengers.add(new AdultPassenger("Взрослый " + (i + 1)));
        }
        System.out.print("Введите количество льготных пассажиров для автобуса (число): ");
        int numberPr = scanner.nextInt();
        for (int i = 0; i < numberPr; i++) {
            busPassengers.add(new PrivilegedPassenger("Льготный пассажир " + (i + 1)));
        }
        System.out.print("Введите количество детей пассажиров для автобуса (число): ");
        int numberCh = scanner.nextInt();
        for (int i = 0; i < numberCh; i++) {
            busPassengers.add(new ChildPassenger("Ребенок " + (i + 1)));
        }

        director.constructBus(busBuilder, busDriver, busPassengers);
        Bus bus = busBuilder.getVehicle();
        bus.depart();

        // Создание такси
        TaxiBuilder taxiBuilder = new TaxiBuilder();
        Driver taxiDriver = TaxiDriver.getInstance();
        List<Passenger> taxiPassengers = new ArrayList<>();


        System.out.print("Введите количество взрослых пассажиров для такси (число): ");
        int numberAdT = scanner.nextInt();
        for (int i = 0; i < numberAdT; i++) {
            boolean Taxilimit = false;
            taxiPassengers.add(new AdultPassenger("Взрослый " + (i + 1)));
        }

        System.out.print("Введите количество детей пассажиров для такси (число): ");
        int numberChT = scanner.nextInt();
        for (int i = 0; i < numberChT; i++) {
            taxiPassengers.add(new ChildPassenger("Ребенок " + (i + 1)));
        }

        director.constructTaxi(taxiBuilder, taxiDriver, taxiPassengers);
        Taxi taxi = taxiBuilder.getVehicle();
        taxi.depart();
    }
}
