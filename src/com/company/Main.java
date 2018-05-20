package com.company;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

    static Resurce_buffer CR1 = new Resurce_buffer(10);
    static CyclicBarrier br1 = new CyclicBarrier(2);
    static CyclicBarrier br2 = new CyclicBarrier(2);
    public static Semaphore sem1 = new Semaphore(0, true);
    public static Semaphore sem2 = new Semaphore(0, true);
    static Resurce_var CR2 = new Resurce_var();

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        Thread3 thread3 = new Thread3();
        Thread4 thread4 = new Thread4();
        Thread5 thread5 = new Thread5();
        Thread6 thread6 = new Thread6();
        try {
            thread1.t.join();
            thread2.t.join();
            thread3.t.join();
            thread4.t.join();
            thread5.t.join();
            thread6.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
class Thread1 implements Runnable{

    public Thread t;
    public Thread1() {
        t = new Thread(this,"1");
        t.start();
    }
    @Override
    public void run() {
        while (true) {
            Main.CR1.consume();
            Thread.yield();
        }
    }
}
class Thread2 implements Runnable{
    public Thread t;
    public Thread2() {
        t = new Thread(this,"2");
        t.start();
    }
    @Override
    public void run() {
        while (true) {
            System.out.println("P2:WAIT BR1");
            try {
                Main.br1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("P2:BR1 SYNC");

            System.out.println("P2: Semaphore before sync");
            Main.sem2.release();
            try {
                Main.sem1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("P2: Semaphore after sync");
            System.out.println("P3:MONITOR:");
            Main.CR1.produce();
        }
    }
}
class Thread3 implements Runnable{
    private Lock mutex = new ReentrantLock();
    public Thread t;
    public Thread3() {
        t = new Thread(this,"3");
        t.start();
    }
    @Override
    public void run() {
        while (true) {
            System.out.println("P3:WAIT BR1");
            try {
                Main.br1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("P3:BR1 SYNC");

            mutex.lock();
            System.out.println("P3:mutex lock");
            Main.CR2.consume();
            System.out.println("P3:mutex unlock");
            mutex.unlock();

            System.out.println("P3:WAIT BR2");
            try {
                Main.br2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("P3:BR2 SYNC");
            /*try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}
class Thread4 implements Runnable{
    public Thread t;
    public Thread4() {
        t = new Thread(this,"4");
        t.start();
    }
    @Override
    public void run() {
        while (true) {
            Main.CR1.produce();
        }
    }
}
class Thread5 implements Runnable{
    public Thread t;
    private Lock mutex = new ReentrantLock();
    public Thread5() {
        t = new Thread(this,"5");
        t.start();
    }
    @Override
    public void run() {
        while (true) {
            System.out.println("P5: Semaphore before sync");
            Main.sem1.release();
            try {
                Main.sem2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("P5: Semaphore after sync");
            System.out.println("P5: MONITOR");
            Main.CR1.produce();
            mutex.lock();
            System.out.println("P5:mutex lock");
            Main.CR2.consume();
            System.out.println("P5:mutex unlock");
            mutex.unlock();
            /*try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }
}
class Thread6 implements Runnable{
    public Thread t;
    private Lock mutex = new ReentrantLock();
    public Thread6() {
        t = new Thread(this,"6");
        t.start();
    }
    @Override
    public void run() {
        while (true) {
            System.out.println("P6:WAIT BR2");
            try {
                Main.br2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("P6:BR1 SYNC");

            mutex.lock();
            System.out.println("P6:mutex lock");
            Main.CR2.produce();
            System.out.println("P6:mutex unlock");
            mutex.unlock();
        }
    }
}