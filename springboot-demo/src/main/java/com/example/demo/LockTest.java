package com.example.demo;

public class LockTest {

    public static Object obj = new Object();

    public static void main(String[] args) {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockTest lockTest = new LockTest();
        lockTest.testMyThread(obj);

    }


    public void testMyThread(Object obj) {
        MyThread t1 = new MyThread("t1", 10000, obj);
        MyThread t2 = new MyThread("t2", 10000, obj);
        t1.start();
        t2.start();
        try {
            Thread.sleep(5000);
            synchronized (obj) {
                obj.notify();
                System.out.println("main notify one thread");
            }
            Thread.sleep(2000);
            synchronized (obj) {
                obj.notify();
                System.out.println("main notify one thread");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
