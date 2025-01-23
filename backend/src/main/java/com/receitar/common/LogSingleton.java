package com.receitar.common;

public class LogSingleton {

    private static LogSingleton instance;

    public static LogSingleton getInstance() {
        if (instance == null) {
            instance = new LogSingleton();
        }
        return instance;
    }

    public void log(String value) {
        System.out.println(value);
    }
}
