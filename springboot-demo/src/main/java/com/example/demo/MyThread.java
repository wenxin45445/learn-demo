package com.example.demo;

public class MyThread extends Thread {

    private long sleepTime;
    private Object obj;

    MyThread(String name, long sleepTime, Object obj) {
        this.setName(name);
        this.sleepTime = sleepTime;
        this.obj = obj;
    }

    @Override
    public void run() {

        synchronized (obj) {
            System.out.println("i'm thread " + this.getName() + ", i get the lock and go to waiting");
            try {
//                obj.wait(this.sleepTime);
                Thread.sleep(this.sleepTime);
                System.out.println("i'm thread " + this.getName() + ", i wait done, start work");
                long st = System.currentTimeMillis();
                while (System.currentTimeMillis() - st < this.sleepTime) {
                    System.out.println("i'm thread " + this.getName() + ", i'm eating.....");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}