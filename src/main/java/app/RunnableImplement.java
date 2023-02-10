package app;

public class RunnableImplement implements Runnable {
  private String taskName;

  public RunnableImplement(String tname) {
    this.taskName = tname;
  }

  @Override
  public void run() {
    for (int i = 0; i < 5; i++) {
      System.out.println(this.taskName + ": " + Integer.toString(i + 1));
    }
  }
}
