public class Singleton {

    private static Singleton instance;

    private Singleton() {}


    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("Doing something...");
    }
}


class Main123 {
    public static void main(String[] args) {
        // Получаем экземпляр Singleton
        Singleton singleton = Singleton.getInstance();

        // Используем методы Singleton
        singleton.doSomething();

        // Проверяем, что это действительно один и тот же объект
        Singleton anotherSingleton = Singleton.getInstance();
        System.out.println(singleton == anotherSingleton); // Должно вывести true
    }
}
