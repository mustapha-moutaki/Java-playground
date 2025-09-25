package collection.employee_management_system;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Company {
    List<Employee>employees = new LinkedList<>();

    public void addEmplyee(Employee employee){
        // validation
        if(employee == null || employee.getName().isEmpty() || employee.getDepartment().isEmpty()){
            System.out.println("must complete field ");
        }else {
            employees.add(employee);
            System.out.println("employee added successfully !");
        }

    }

    List<Employee>displayAllEmployees(){
        if(!employees.isEmpty()){
            for (Employee employee: employees){
                System.out.println("employee name: "+ employee.getName());
                System.out.println("employee department: "+ employee.getDepartment());
                System.out.println("employee salary: "+ employee.getSalary());
            }
        }else{
            System.out.println("--- no employees--");
        }
        return employees;
    }

    public int showTotalEmployees(){
        return employees.size();
    }


    // need to fix:
    Map<String,List<Employee>> getEmployeebyDepartment(){
        // we can use streams in this case to maek the code more simple
        return employees.stream().collect(Collectors.groupingBy(Employee::getDepartment));
    }

    public void removeEmployeeById(int id){
        employees.remove(id);
    }

    public Employee updateEmployee(Employee employee){
        return new Employee(
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }


    public void updateSalary(int id, double salary){
        Employee emp = employees.get(id);
        if(emp != null){
            emp.setSalary(salary);
        }else{
            System.out.println("error pls try again with valid employee id");
        }
    }

    public void getEmployeesWithSalsaryMore300(){
        Optional<Employee> empOpt = employees.stream()
                .filter(e -> e.getSalary() > 300)
                .findFirst();

        empOpt.ifPresent(emp -> System.out.println(emp.getName()));

    }
}
