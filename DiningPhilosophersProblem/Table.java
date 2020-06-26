package DiningPhilosophersProblem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Table implements Runnable{
    private ArrayList<Fork> forks;
    private ArrayList<Philosopher> philosophers;
    private Iterator<Long> times;

    //Inicializamos la mesa, con el numero de filosofos, tenedores y tiempos de espera/comiendo aleatorios
    public Table(int numPhilosophers) {
        if (numPhilosophers < 2) {
            throw new IllegalArgumentException("There should be more than one philosopher");
        }

        this.forks = new ArrayList<>();
        this.philosophers = new ArrayList<>();

        //Tiempo de espera/comiendo aleatorios entre 2 y 7 segundos
        this.times = new Random().longs(2000, 7000).iterator();

        //Añadimos tantos tenedores como filosofos haya
        for (int i = 0; i < numPhilosophers; ++i) {
            Fork f = new Fork();
            forks.add(f);
        }
        //Inicializamos a los filosofos e indicamos que tenedores pertenecientes van a tener a sus lados.
        for (int i = 0; i < numPhilosophers; ++i) {
            int n = (i + 1) % numPhilosophers;
            Fork left = forks.get(i);
            Fork right = forks.get(n);
            /*
            Cuando lleguemos al ultimo filosofo, es decir cuando n==0, marcamos que dicho filosofo es zurdo, para a posteriori,
            se pueda indicar que primero va a tener que coger el tenedor izquierdo.
            Esto lo hacemos para evitar el deadlock, es decir, si todos los filosofos cogen al mismo tiempo el tenedor que se
            encuentra a su derecha, se generará un bloqueo permanente dado que todos los filosofos estarán esperando a que se suelte
            el tenedor de su izquierda para que puedan usarlo, y esto nunca pasará.
            Por ello si uno de los filosofos empieza cogiendo primero el tenedor izquierdo y no el derecho, evitaremos que ocurra
            lo anteriormente dicho, es decir, el bloqueo permanente o deadlock.
            */
            boolean isLeftHanded = (n == 0);

            //Inicializamos los filosofos con los tenedores pertenecientes que tienen a sus lados
            Philosopher p = new Philosopher("Philosopher " + (i + 1),this, left, right, isLeftHanded);
            philosophers.add(p);
        }
    }

    /*
    Cuando usamos un bloque sincronizado, internamente Java usa un monitor también conocido como bloqueo de monitor o bloqueo intrínseco, para proporcionar sincronización.
    Estos monitores están vinculados a un objeto, por lo tanto, todos los bloques sincronizados del mismo objeto pueden tener un solo hilo ejecutándolos al mismo tiempo.
    */
    public synchronized long getTime() {
        return times.next();
    }

    public void run() {
        /*
        ExecutorService es una solución completa para el procesamiento asincrónico.
        Gestiona una cola en memoria y programa tareas enviadas en función de la disponibilidad de subprocesos.
        */
        //Creamos un thread pool del tamaño de los n filosofos inicializados
        ExecutorService executorService = Executors.newFixedThreadPool(philosophers.size());
        //Añadimos a los filosofos al thread pool, esto se permite gracias a que la clase Philosophers implementa la interfaz Runnable
        //Una vez el executor se ha creado, podemos usarlo para añadir tareas.
        for (Philosopher p : philosophers) {
            executorService.submit(p);
        }
    }
}