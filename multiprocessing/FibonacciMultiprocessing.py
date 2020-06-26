# coding=utf-8
import multiprocessing as mp
import math
import time

def fibo(data):
    a, b = 0, 1
    for _ in range(0, data):
        a, b = b, a + b
    return ((data, a))

def fibo_paralelo(data):
    totalCores = mp.cpu_count() #Obtenemos los cores que tiene el ordenador
    dividedWork = math.ceil(data / totalCores) #Dividimos la carga de trabajo de forma equitativa
    MC = mp.RawArray('f', data) #Creamos memoria compartida
    cores = [] #Creamos array de cores para lanzar en paralelo

    for core in range(totalCores): #Se asigna a cada core su trabajo
        i_MC = core * dividedWork #inicio del trabajo del core
        f_MC = (core+1) * dividedWork #fin del trabajo del core
        cores.append(mp.Process(target = fiboCore, args = (data, MC, i_MC, f_MC))) #añadimos al array todos los resultados de cada core
    for core in cores:
        core.start()
    for core in cores:
        core.join()

def fiboCore(n, array, p, f): #funcion fibonacci que hara cada core
    a, b = p, f
    for i in range(p, f):
        print(i)
        a, b = b, a + b
        array[i] = a #añadimos al array el resultado del core, aqui se encuentra el fallo

if __name__ == "__main__":
    numExp = 21
    inicioS = time.time()
    print("Fibonacci secuencial: ", fibo(numExp))
    finS = time.time()
    print("tiempo de ejecucion secuencial: ", finS - inicioS)
    print("=================================================")
    inicioP = time.time()
    print("Fibonacci paralelo: ", fibo_paralelo(numExp))
    finP = time.time()
    print("tiempo de ejecucion paralelo: ", finP - inicioP)