import java.util.*;
/**
 * Класс Factory представляет собой абстрактную фабрику для class Bus & class Taxi  с использование Singleton для drive
 * @author Dmitriy
 * @version 1.0
 */
public class Factory {
    /**
     * create passenger
     */

    static class Passenger {
        private String name;

        public Passenger(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * singleton for driver, pick only one driver for the class
     */
    static abstract class Driver {
        public abstract void drive();
    }

    static class BusDriver extends Driver {
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

    static class TaxiDriver extends Driver {
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


    /**
     *limits passenger
     */
    static abstract class Vehicle {
        protected Driver driver;
        protected int passengerLimit;
        protected List<Passenger> passengers = new ArrayList<>();

        public void setDriver(Driver driver) {
            if (this.driver != null) {
                System.out.println("Водитель уже назначен.");
                return;
            }

            // Проверка типа водителя
            if (this instanceof Bus && !(driver instanceof BusDriver)) {
                System.out.println("Неправильный тип водителя для автобуса.");
                return;
            }

            if (this instanceof Taxi && !(driver instanceof TaxiDriver)) {
                System.out.println("Неправильный тип водителя для такси.");
                return;
            }

            this.driver = driver;
        }

        public boolean addPassenger(Passenger passenger) {
            if (passengers.size() < passengerLimit) {
                passengers.add(passenger);
                System.out.println("Добавлен новый пассажир, занято мест " + passengers.size());
                return true;
            } else {
                System.out.println("Лимит пассажиров превышен. Пассажир " + passenger.getName() + " не может быть добавлен.");
                return false;
            }
        }

        public boolean isReadyToDepart() {
            return driver != null && !passengers.isEmpty();
        }

        public void depart() {
            if (isReadyToDepart()) {
                System.out.println("Транспортное средство с " + passengers.size() + " пассажирами отправляется.");
                driver.drive();
            } else {
                System.out.println("Транспортное средство не готово к отправлению.");
            }
        }
    }

    static class Bus extends Vehicle {
        public Bus() {
            this.passengerLimit = 30;
        }
    }

    static class Taxi extends Vehicle {
        public Taxi() {
            this.passengerLimit = 4;
        }
    }

    /**
     *vehicle factory
     */
    static abstract class VehicleFactory {
        public abstract Vehicle createVehicle();
        public abstract Driver createDriver();
    }

    static class BusFactory extends VehicleFactory {
        @Override
        public Vehicle createVehicle() {
            return new Bus();
        }

        @Override
        public Driver createDriver() {
            return BusDriver.getInstance();
        }
    }

    static class TaxiFactory extends VehicleFactory {
        @Override
        public Vehicle createVehicle() {
            return new Taxi();
        }

        @Override
        public Driver createDriver() {
            return TaxiDriver.getInstance();
        }
    }


    public static void main(String[] args) {
        System.out.print("Фабрика автовокзала \n");
        VehicleFactory busFactory = new BusFactory();
        Vehicle bus = busFactory.createVehicle();
        Driver busDriver = busFactory.createDriver();

        bus.setDriver(busDriver);
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество пассажиров для автобуса (число): ");
        int number = scanner.nextInt();
        for (int i = 0; i < number; i++) {
            bus.addPassenger(new Passenger("Пассажир автобуса " + (i + 1)));
        }
        bus.setDriver(busDriver);
        bus.depart();

        VehicleFactory taxiFactory = new TaxiFactory();
        Vehicle taxi = taxiFactory.createVehicle();
        Driver taxiDriver = taxiFactory.createDriver();

        System.out.print("Введите количество пассажиров для такси (число): ");
        int num = scanner.nextInt();

        for (int i = 0; i < num; i++) {
            taxi.addPassenger(new Passenger("Пассажир такси " + (i + 1)));
        }
        taxi.setDriver(busDriver);
        taxi.depart();
    }
}
