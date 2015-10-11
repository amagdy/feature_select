/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a1works.featureSelect;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author magdy
 */
public class ReaderThread {

    private ThreadObserver observer = null;
    private int thread_index = 0;
    private File dataSetFile;
    private int thread_count = 0;
    
    public ReaderThread(File dataSetFile, int _thread_index, int _thread_count, ThreadObserver _observer){
        this.dataSetFile = dataSetFile;
        thread_index = _thread_index;
        thread_count = _thread_count;
        observer = _observer;
    }
    
    public void execute(){
        Thread th = new Thread(new Runnable() {
            
            @Override
            public void run() {
                List<DataSetFileEntry> class_features = new ArrayList();
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(dataSetFile)));
                } catch (FileNotFoundException ex) {
                    CustomLogger.logAndExit(ex, "Could not open Data set file for reading");
                }
                
                
                String line = null;
                try {
                    int line_number = 0;
                    while ((line = br.readLine()) != null) {
                        if ((line_number % thread_count) == thread_index) class_features.add(DataSetFileEntry.getInstanceByLineString(line));
                        line_number++;
                    }
                } catch (IOException ex) {
                    CustomLogger.log("Error while reading from data set file");
                }
                try {
                    if (br != null) br.close();
                } catch (IOException ex) {
                    CustomLogger.log("Error while closing data set file");
                }
                observer.threadFinished(thread_index, class_features);
            }
        });
        th.start();
    }
}
