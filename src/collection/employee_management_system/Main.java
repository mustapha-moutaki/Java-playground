package collection.employee_management_system;

public class Main {
    public static void main(String[] args) {
        // 1- crete an employee
        Employee employee1 = new Employee("mohammed", "IT", 20000);
        Employee employee2 = new Employee("ahmed", "Marketing", 12000);


        System.out.println("======add-employees=======");
        // add two employees
        Company company = new Company();
        company.addEmplyee(employee1);
        company.addEmplyee(employee2);
        System.out.println("====================");



        System.out.println("=====employee-List=========");
        //display all employees
        System.out.println(company.displayAllEmployees());
        System.out.println("====================");


        System.out.println("=====Employee for each depa======");
        //get employees of each department
        company.getEmployeebyDepartment();
        System.out.println("====================");


        System.out.println("======remove employee=========");
        //remove employee by id
        company.removeEmployeeById(1);
        System.out.println("====================");



        System.out.println("======update salary======");
        // update the salary
        company.updateSalary(2, 10);
        company.getEmployeesWithSalsaryMore300();
        System.out.println("====================");
    }
}
