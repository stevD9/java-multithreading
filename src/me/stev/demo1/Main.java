package me.stev.demo1;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            throw new RuntimeException("FUCK YOU BOY");
        });

        thread.setName("Stupid thread");
        thread.setUncaughtExceptionHandler((t, e) ->
                System.out.println("Uncaught error in thread " + t.getName() + " with message " + e.getMessage()));

        thread.start();

    }
}
