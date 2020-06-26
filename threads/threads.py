import os
import multiprocessing as mp
import threading

def gstar_cpu():
    while True:
        pass

print("El id del PID es: ", os.getpis())
print("El numero dde hilos activos es: ", threading.active_count())

for thread in threading.enumerate():
    print("Thread --> " + str(thread))

for i in range(5):
    mp.Process(target = gastar_cpu).start()

print("El id del PID es: ", os.getpis())
print("El numero dde hilos activos es: ", threading.active_count())

for t in threading.enumerate():
    print("Thread ----> " + str(t))