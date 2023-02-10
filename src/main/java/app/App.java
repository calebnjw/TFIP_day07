package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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

    // List<Employee> employees = getEmployees();

    // exampleFunctionalInterface();

    // exampleLoopPrint(employees);
    // exampleListObjectSort(employees);
    // exampleListIntegerSort();
    // exampleArraySort();
    // exampleMapSort();

    // exampleStreamFilter(employees);

    // exampleThread();
    // exampleThreadSpinOff();
    // exampleThreadLambda();

    // exampleLinkedList();
    exampleStack();
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

  // FUNCITONAL INTERFACE
  public static void exampleFunctionalInterface() {
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
  }

  // LOOP AND PRINT
  public static void exampleLoopPrint(List<Employee> employees) {
    System.out.println("for each loop");
    for (Employee e : employees) {
      System.out.println(e);
    }
    System.out.println("list.forEach");
    employees.forEach(e -> System.out.println(e));
  }

  // SORTING
  public static void exampleListObjectSort(List<Employee> employees) {
    Comparator<Employee> comparator = Comparator.comparing(e -> e.getFirstName());

    // sort by first name in ascending order
    employees.sort(comparator);
    System.out.println(employees);
    // sort by first name in descending order
    employees.sort(comparator.reversed());
    System.out.println(employees);
  }

  public static void exampleListIntegerSort() {
    List<Integer> integers = Arrays.asList(7, 3, 6, 4, 5, 9, 2, 34, 6, 8, 1, 7, 68, 0, 3);
    integers.sort(Comparator.naturalOrder());
    System.out.println(integers);
  }

  public static void exampleArraySort() {
    // SORTING ARRAY
    String arr[] = { "practice", "workshop", "lecture", "revision" };
    Arrays.sort(arr); // default sort is ascending
    System.out.println("Ascending Order: " + Arrays.toString(arr));
    Arrays.sort(arr, Collections.reverseOrder()); // introduce a comparator class to sort other ways
    System.out.println("Descending Order" + Arrays.toString(arr));
  }

  public static void exampleMapSort() {
    // don't have to define <String, Integer> on the right side
    // because it will take in <String, Integer>
    // from the type declaration on the left
    Map<String, Integer> mapList = new HashMap<>();
    mapList.put("Sushi", 2);
    mapList.put("Sashimi", 6);
    mapList.put("Bento", 1);
    mapList.put("Udon", 3);
    mapList.put("Tempura", 5);
    mapList.put("Takoyaki", 4);

    mapList.forEach((k, v) -> System.out.println(k + ": " + v));

    List<Entry<String, Integer>> list = new ArrayList<>(mapList.entrySet()); // Entry class is from Map.Entry.
    list.sort(Entry.comparingByValue());
    list.forEach(System.out::println);
  }

  // STREAM AND FILTER
  public static void exampleStreamFilter(List<Employee> employees) {
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
  }

  // THREADS
  public static void exampleThread() {
    // creating multiple process that will run on different threads
    RunnableImplement ri1 = new RunnableImplement("Task 1");
    RunnableImplement ri2 = new RunnableImplement("Task 2");
    RunnableImplement ri3 = new RunnableImplement("Task 3");
    RunnableImplement ri4 = new RunnableImplement("Task 4");
    RunnableImplement ri5 = new RunnableImplement("Task 5");

    // the Executor Service helps to manage what process will run on which thread
    // and how many total threads can be used
    ExecutorService es = Executors.newFixedThreadPool(4);
    es.execute(ri1); // threads are submitted in this order, but not executed in this order
    es.execute(ri2); // .execute does not return anything
    es.submit(ri3); // but .submit CAN return the result of computation
    es.submit(ri4);
    es.submit(ri5);

    es.shutdown(); // shutdown the runnable processes and release resources
  }

  public static void exampleThreadSpinOff() {
    RunnableImplement ri6 = new RunnableImplement("Task 6");
    RunnableImplement ri7 = new RunnableImplement("Task 7");
    RunnableImplement ri8 = new RunnableImplement("Task 8");
    RunnableImplement ri9 = new RunnableImplement("Task 9");
    RunnableImplement ri10 = new RunnableImplement("Task 10");

    // this is the "manual way" to execute threads
    Thread t1 = new Thread(ri6);
    t1.start(); // thread will automatically end itself once complete
    Thread t2 = new Thread(ri7);
    t2.start();
    Thread t3 = new Thread(ri8);
    t3.start();
    Thread t4 = new Thread(ri9);
    t4.start();
    Thread t5 = new Thread(ri10);
    t5.start();
  }

  // LAMBDA
  public static void exampleThreadLambda() {
    new Thread(
        () -> {
          for (int i = 0; i < 5; i++) {
            System.out.println(i);
          }
        }).start();
  }

  public static void exampleLinkedList() {
    LinkedList<String> myList = new LinkedList<String>();

    myList.add("A");
    myList.add("B");
    myList.add("C");
    myList.add("D");
    myList.add(1, "E"); // will insert "E" at index 1
    System.out.println(myList); // [ A, E, B, C, D ]

    myList.remove("C"); // will remove "C"
    System.out.println(myList); // [ A, E, B, D ]

    myList.remove(1); // will remove item at index 1, which should be "E"
    System.out.println(myList); // [ A, B, D ]
  }

  public static void exampleStack() {
    Stack<Integer> pancakes = new Stack<>();

    for (int i = 1; i <= 10; i++) {
      pancakes.push(i);
    }

    for (int j = 0; j < 5; j++) {
      Integer item = pancakes.pop(); // removes the last element and returns it
      System.out.println("pop ->" + item);
    }

    Integer lastElement = pancakes.peek(); // looks at the last element without removing it
    System.out.println("Last element in the stack: " + lastElement);

    Integer firstElement = pancakes.firstElement(); // looks at the first element
    System.out.println("First element in the stack: " + firstElement);

    Iterator<Integer> iterator = pancakes.iterator();
    // iterator has built in property called hasNext
    // which tells you whether there are more elements
    while (iterator.hasNext()) {
      Integer nextElement = iterator.next();
      System.out.println("Next element in the stack: " + nextElement);
    }
  }
}