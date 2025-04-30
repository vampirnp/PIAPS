public class Deanery implements Observer {
    @Override
    public void update() {
        notifyDepartment();
    }

    private void notifyDepartment() {
        System.out.println("Деканат: Данные не получены! Уведомление кафедры.");
    }
}
