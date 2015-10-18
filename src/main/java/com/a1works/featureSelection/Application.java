package com.a1works.featureSelection;

import java.io.*;

/**
 * Created by Ahmed Magdy <ahmed_magdy@epam.com> on 18.10.15.
 */
public final class Application implements Runnable {

    private volatile boolean isAppRunning = false;

    private static final Application INSTANCE = new Application();

    public static Application getInstance(){
        return INSTANCE;
    }

    public static void main(String[] arguments){
        getInstance().run();
    }

    private Application(){}

    @Override
    public void run(){
        isAppRunning = true;
        System.out.println("Hi Every one...");
    }

    public boolean isRunning(){
        return isAppRunning;
    }


}
