package collection.employee_management_system;

public class Employee {
    private int empid;
    private String name;
    private String department;
    private double salary;

    private int counter = 109;// to avoid insert id manually
    public Employee(String name, String department, double salary){
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
    // to get the employee id
    public int getEmpid() {
        return empid;
    }
    // set employee id in this case is not important because
    public void setEmpid(int empid) {
        this.empid = empid;
    }

    // to get name
    public String getName() {
        return name;
    }

    // set employee name
    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return "the employee id: "+empid+"and his name is: "+name+"in department: "+department+"salary is: "+salary+".";
    }
}
