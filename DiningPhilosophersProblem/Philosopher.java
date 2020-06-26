package DiningPhilosophersProblem;

public class Philosopher implements Runnable{
    private String name;
    private Table table;
    private Fork right;
    private Fork left;
    private boolean isLeftHanded;

    public Philosopher(String name, Table table, Fork left, Fork right, boolean isLeftHanded) {
        this.name = name;
        this.table = table;
        this.right = right;
        this.left = left;
        this.isLeftHanded = isLeftHanded;
    }

    //Metodo que pone a un filosofo a la espera(pensando un tiempo aleatorio entre 2 y 7 segundos)
    public void think() throws InterruptedException {
        long time = table.getTime();
        System.out.println(this.name + " thinking during " + time + " ms");
        spendTime(time);
    }

    /*Metodo que permite al filosofo "comer", es decir,
     1. Coger los tenedores (tomar prestado los tenedores de su lado(tarea/bloque de codigo) mientras bloqueas a los otros filosofos(hilos) de poder acceder a esta).
     2."Comer"(comerá durante un tiempo aleatorio entre 2 y 7 segundos, es decir, estara comiendo durante ese tiempo y bloqueado el acceso a los tenedores que estan
        siendo usados(bloqueando el acceso de otros hilos a esa parte del codigo/tarea)).
     3. Soltar los tenedores (liberara dichos tenedores (tarea/bloque de codigo) para que otros filosofos(hilos), puedan usarlo si esta a su derecha o izquierda el tenedor a usar).
    */
    public void eat() throws InterruptedException {
        takeForks();
        long time = table.getTime();
        System.out.println(this.name + " eating during " + time + " ms");
        spendTime(time);
        dropForks();
    }

    //Metodo que ejecutará cada hilo
    public void run() {
        while (true) {
            try {
                think();
                eat();
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }

    //Metodo para coger los tenedores
    private void takeForks() {
        if (isLeftHanded) { //Si es zurdo(el ultimo filosofo) coge primero el izquierdo y luego el derecho si estan disponibles.
            left.take();
            right.take();
        } else { //Sino, es decir, el resto de filosofos, cogen primero el tenedor derecho y luego el izquierdo si estan disponibles.
            right.take();
            left.take();
        }
    }

    //Metodo para soltar los tenedores
    private void dropForks() {
        if (isLeftHanded) { //Si es zurdo(el ultimo filosofo) suelta primero el izquierdo y luego el derecho.
            left.drop();
            right.drop();
        } else { //Sino, es decir, el resto de filosofos, sueltan primero el tenedor derecho y luego el izquierdo.
            right.drop();
            left.drop();
        }
    }

    //Tiempo transcurrido en el que un filosofo estara pensando o comiendo
    private void spendTime(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}