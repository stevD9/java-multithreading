package me.stev.multiexecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        List<Runnable> tasks = new ArrayList<>();
        tasks.add(new IntTask());
        tasks.add(new DoublerTask());
        tasks.add(new BooleanTask());

        MultiExecutor multiExecutor = new MultiExecutor(tasks);
        multiExecutor.executeAll();
    }

    private static class IntTask implements Runnable {

        Random random = new Random();
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + random.nextInt());
        }
    }

    private static class DoublerTask implements Runnable {

        Random random = new Random();
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + random.nextDouble());
        }
    }

    private static class BooleanTask implements Runnable {

        Random random = new Random();
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " : " + random.nextBoolean());
        }
    }
}
