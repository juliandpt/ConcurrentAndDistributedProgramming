package DiningPhilosophersProblem;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {

    /*
    Lock is a utility for blocking other threads from accessing a certain segment of code, apart from the thread that's executing it currently.
    ReentrantLock class implements the Lock interface. It offers the same concurrency and memory semantics, as the implicit monitor lock accessed
    using synchronized methods and statements, with extended capabilities. A ReentrantLock is owned by the thread last successfully locking, but not yet unlocking it.
    A thread invoking lock will return, successfully acquiring the lock, when the lock is not owned by another thread. The method will return immediately if the current thread already owns the lock.
    */
    //Usamos una especie de semaforo pero sin usar la clase semaforo.
    /*
    The Semaphore is used for blocking thread level access to some part of the physical or logical resource.
    A semaphore contains a set of permits; whenever a thread tries to enter the critical section,
    it needs to check the semaphore if a permit is available or not.
   */
    private ReentrantLock lock;

    public Fork() {
        this.lock = new ReentrantLock();
    }

    //Adquiere el lock si está disponible; Si el lock no está disponible, se bloquea al thread hasta que se libere el lock.
    public void take() {
        lock.lock();
    }

    //Desbloquea la instancia del lock si esta siendo usada por el hilo que lo solicita
    public void drop() {
        if (!isHeld())
            return;
        lock.unlock();
    }

    //Comprueba que hilo esta usando el lock
    public boolean isHeld() {
        return lock.isHeldByCurrentThread();
    }
}