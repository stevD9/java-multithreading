package me.stev.multiexecutor;

import java.util.List;

public class MultiExecutor {

    private List<Runnable> tasks;

    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    public void executeAll() {
        tasks.forEach(task -> {
            Thread thread = new Thread(task);
            thread.start();
        });
    }
}
