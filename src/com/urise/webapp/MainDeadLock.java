package com.urise.webapp;

public class MainDeadLock {
    private static Integer i1 = 0;
    private static Integer i2 = 0;

    private static Object LOCK1 = new Object();
    private static Object LOCK2 = new Object();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                inc(LOCK1, LOCK2);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                inc(LOCK2, LOCK1);
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(i1 + "  " + i2);
    }

    private static void inc(Object lock1, Object lock2){
        synchronized (lock1){
            synchronized (lock2){
                i1++;
                i2++;
            }
        }
    }
}
