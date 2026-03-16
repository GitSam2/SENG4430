package IdLength.multiClassExample;

public class Employee extends Person {
    private String employeeId;
    private double salary;

    public Employee(String name, int age, String thisIdentifierIsOver30CharactersLong, String employeeId, double salary) {
        super(name, age, thisIdentifierIsOver30CharactersLong);
        this.employeeId = employeeId;
        this.salary = salary;
    }

    public void giveRaise(double amount) {
        salary += amount;
    }
}
