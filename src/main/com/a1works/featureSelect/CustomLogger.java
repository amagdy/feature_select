/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a1works.featureSelect;

import java.io.PrintStream;

/**
 *
 * @author magdy
 */
public class CustomLogger {
    
    private static PrintStream ps = System.err;
    
    private static void printUsage() {
        log("Usage: ./feature_selection_app [options] data_set_file [output_file]\n"
                + "options:\n"
                + "-h : to show this help\n"
                + "-m metric_type : feature selection metric\n"
                + "	pmi  -- PMI (Pointwise Mutual Information) feature selection metric\n"
                + "	chi2 -- Chi^2 (X^2) feature selection metric\n"
                + "-n number : number of features to select and return\n"
                + "-t number : number of threads\n");
    }
    
    public static void exitWithUsage(){
        printUsage();
        System.exit(1);
    }
    
    public static void logAndExit(String txt) {
        log(txt);
        System.exit(2);
    }
    
    public static void logAndExit(Throwable ex, String txt) {
        log(ex, txt);
        System.exit(2);
    }
    
    public static void logAndExitWithUsage(String txt) {
        log(txt);
        exitWithUsage();
    }
    
    public static void logAndExitWithUsage(Throwable ex, String txt) {
        log(ex, txt);
        exitWithUsage();
    }
    
    public static void log(String txt){
        ps.println(txt);
    }
    
    public static void log(Throwable t) {
        ps.println(t.getMessage());
    }
    
    public static void log(Throwable t, String txt) {
        ps.println(txt);
        ps.println(t.getMessage());
        t.printStackTrace(ps);
    }
    
}
