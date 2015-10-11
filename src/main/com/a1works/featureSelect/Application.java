
package com.a1works.featureSelect;

import com.a1works.utils.MayBe;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public final class Application extends ThreadObserver implements Runnable {

    private static Map<String, CustomStringIntHashMap> features_frequencies_per_class;
    private static CustomStringIntHashMap classes_frequencies = new CustomStringIntHashMap();
    private static CustomStringIntHashMap features_frequencies = new CustomStringIntHashMap();
    private static int all_classes_count = 0;
    private static int all_features_count = 0;
    private static int records_count = 0;
    private static final Object mutex = new Object();
    private static volatile boolean processing_done = false;
    private static AppOptions options;

    private static void readDataSetFile(List<DataSetFileEntry> _data) {
        features_frequencies_per_class = new HashMap();
        records_count = _data.size();
        for (DataSetFileEntry line_entry : _data) {
            classes_frequencies.increment(line_entry.class_name, 1);
            for (String feature_name : line_entry.features.keySet()) {
                // the value of the feature is either 1 or nothing ////FIXME this part I am not sure about
                int feature_val = line_entry.features.get(feature_name).intValue();
                CustomStringIntHashMap feature_map;
                if (features_frequencies_per_class.containsKey(feature_name)) {
                    feature_map = features_frequencies_per_class.get(feature_name);
                } else {
                    feature_map = new CustomStringIntHashMap();
                    features_frequencies_per_class.put(feature_name, feature_map);
                }
                feature_map.increment(line_entry.class_name, feature_val);
                features_frequencies.increment(feature_name, feature_val);
            }
        }
        all_features_count = features_frequencies.size();
        all_classes_count = classes_frequencies.size();
    }


    public Application(AppOptions options) {
        this.options = options;

    }

    private void printOutput(final Map<String, Double> all_features_scores) {
        PrintStream os = null;
        if (output_file != null) {
            try {
                os = new PrintStream(output_file);
            } catch (FileNotFoundException ex) {
                CustomLogger.log(ex, "Cannot write to output file");
            }
        } else {
            os = System.out;
        }

        // sort the features
        SortedMap<String, Double> all_features_sorted_scores = new TreeMap(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                double diff = all_features_scores.get(o2) - all_features_scores.get(o1);    // descendingly
                if (diff > 0) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
        all_features_sorted_scores.putAll(all_features_scores);

        // put the top features in the outputfile
        Iterator<String> features_names = all_features_sorted_scores.keySet().iterator();
        int i = 0;
        while (features_names.hasNext() && i < selected_features_count) {
            String fname = features_names.next();
            os.println(fname + "\t" + all_features_scores.get(fname));
            i++;
        }
        if (output_file != null) {
            os.close();
        }
    }

    @Override
    public void allThreadsFinished(List<DataSetFileEntry> _data) {
        readDataSetFile(_data);
        // execute feature selection
        Map<String, Double> all_features_scores = FeatureSelectionMetric.getInstance(
                metric,
                features_frequencies_per_class,
                classes_frequencies,
                features_frequencies,
                records_count).execute();
        
        // print the output
        this.printOutput(all_features_scores);
        synchronized (mutex) {
            processing_done = true;
        }
    }

    private void waitForAllThreads() {
        while (true) {
            try {
                synchronized (mutex) {
                    if (processing_done) {
                        return;
                    }
                    mutex.wait(250);
                }
            } catch (InterruptedException ex) {
                CustomLogger.log(ex);
            }
        }
    }


    @Override
    public void run() {
        startThreads(options.getThreadsCount(), options.getDataSetFile());
        waitForAllThreads();
    }
}
