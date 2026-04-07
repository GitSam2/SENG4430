package ccTest.multiClassExample;

import java.util.List;

public class Company {
    private String companyName;
    private List<Employee> employees;

    public Company(String companyName, List<Employee> employees) {
        this.companyName = companyName;
        this.employees = employees;
    }

    public void printEmployeeCount() {
        System.out.println("Employees: " + employees.size());
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
