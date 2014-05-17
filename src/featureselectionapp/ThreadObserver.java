/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureselectionapp;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author magdy
 */
public abstract class ThreadObserver {
    
    protected final List<DataSetFileEntry> data = new ArrayList();
    private AtomicInteger working_threads;
    private int threads_count = 0;
    
    public void startThreads(int _thread_count, String _file_path) {
        // adjust threads count and features per thread
        File data_set_file = new File(_file_path);
        if (!data_set_file.exists()) {
            CustomLogger.logAndExit("Dataset file " + _file_path + "does not exist");
        }
        threads_count = _thread_count;
        
        working_threads = new AtomicInteger(threads_count);
        
        for (int i = 0; i < threads_count; i++) {
            ReaderThread th = new ReaderThread(_file_path, i, threads_count, this);
            th.execute();
        }
    }
    
    public void threadFinished(int _thread_index, List<DataSetFileEntry> _class_features) {
        synchronized(data) {
            data.addAll(_class_features);
        }
        int count = working_threads.decrementAndGet();
        if (count <= 0) allThreadsFinished(data);
    }
    
    public abstract void allThreadsFinished(List<DataSetFileEntry> _data);
        
    
}
