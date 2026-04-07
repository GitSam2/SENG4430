package ccTest.multiClassExample;

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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    
}
