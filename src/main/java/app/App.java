package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import main.java.app.Employee;

/**
 * Hello world!
 *
 */
public class App {
  private App() {
  }

  @FunctionalInterface
  // T is a generic type, can be replaced by anything, int, string, bool, etc.
  interface Operator<T> {
    T process(T a, T b); // type of a and b are defined here
  }

  public static void main(String[] args) {
    // TAKING IN ARGS
    // taking in command line arguments
    if (args[0].equalsIgnoreCase("kill")) {
      System.exit(0); // 0 means that process exited successfully
    }

    // FUNCITONAL INTERFACE
    // all of these use the same generic interface, but perform different operations
    Operator<Integer> addOperation = (a, b) -> {
      return (a + b);
    };
    // to perform the operation, call the method name defined in the interface
    // in this case it's called process
    System.out.println(addOperation.process(13, 2));

    Operator<Integer> subtractOperation = (a, b) -> {
      return (a - b);
    };
    System.out.println(subtractOperation.process(63, 31));

    // the constructor can even take in a different kind of
    Operator<String> concatOperation = (a, b) -> {
      return a.concat(b);
    };
    System.out.println(concatOperation.process("Hello", "World"));

    // SORTING
    List<Employee> employees = getEmployees();
    Comparator<Employee> comparator = Comparator.comparing(e -> e.getFirstName());

    // sort by first name in ascending order
    employees.sort(comparator);
    System.out.println(employees);
    // sort by first name in descending order
    employees.sort(comparator.reversed());
    System.out.println(employees);

    List<Integer> integers = Arrays.asList(7, 3, 6, 4, 5, 9, 2, 34, 6, 8, 1, 7, 68, 0, 3);
    integers.sort(Comparator.naturalOrder());
    System.out.println(integers);

    // STREAM AND FILTER
    // stream and filter employees by last name
    // each item is passed one by one without using a loop
    List<Employee> filteredEmployees = employees.stream() // turn data from list into a stream
        .filter(e -> e.getLastName() // filter by last name
            .equals("Ng")) // last name equals to Ng (this is checking the last name)
        .collect(Collectors.toList()); // collect and turn back into a list
    System.out.println(filteredEmployees);

    Employee oneEmployee = employees.stream()
        .filter(e -> e.getLastName()
            .equals("Ng")) // same filter. performs action on the last name.
        .findFirst() // finds the first "true": first employee with last name "Ng"
        .get(); // takes the result from findFirst and returns it
    System.out.println(oneEmployee);

    int i = employees.indexOf(oneEmployee);
    Employee returnedEmployee = employees.get(i);
    System.out.printf("%d >>> %s \n", i, returnedEmployee);

    // LOOP AND PRINT
    System.out.println("for each loop");
    for (Employee e : employees) {
      System.out.println(e);
    }
    System.out.println("list.forEach");
    employees.forEach(e -> System.out.println(e));
  }

  public static List<Employee> getEmployees() {
    List<Employee> employees = new ArrayList<Employee>();

    employees.add(new Employee(1, "Hsien Loong", "Lee", 70));
    employees.add(new Employee(2, "Chee Hean", "Teo", 68));
    employees.add(new Employee(3, "Swee Keat", "Heng", 62));
    employees.add(new Employee(4, "Pritam", "Singh", 46));
    employees.add(new Employee(5, "Eng Hen", "Ng", 64));
    employees.add(new Employee(6, "Lawrence", "Wong", 68));
    employees.add(new Employee(7, "Kwan Yew", "Lee", 95));
    employees.add(new Employee(7, "Sam", "Ng", 95));

    return employees;
  }
}