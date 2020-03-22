package me.stev.atomicreference;

import java.util.concurrent.atomic.AtomicReference;

public class Main {

    public static void main(String[] args) {
        String oldName = "old name";
        String newName = "new name";
        AtomicReference<String> stringAtomicReference = new AtomicReference<>(oldName);

        if (stringAtomicReference.compareAndSet(oldName, newName)) {
            System.out.println("The new value is : " + stringAtomicReference.get());
        } else {
            System.out.println("Nothing changed");
        }
    }
}
