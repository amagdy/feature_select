package com.a1works.featureSelection;

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
        //////TODO
    }

    public boolean isRunning(){
        return isAppRunning;
    }


}
