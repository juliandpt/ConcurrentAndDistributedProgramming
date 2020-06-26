package DiningPhilosophersProblem;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Setting up dinner...");
        Table table = new Table(5);
        Thread dinner = new Thread(table);

        System.out.println("Starting dinner");
        dinner.start();
        dinner.join();
    }
}