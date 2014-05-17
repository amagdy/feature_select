/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureselectionapp;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author magdy
 */
public class FeatureSelectionApp implements FeatureSelectionObserver {

    /**
     * Map<feature_name, Map<class_name, frequency_of_feature_for_this_class>>
     */
    private static TMap<String, CustomStringIntHashMap> features_frequencies_per_class;
    private static CustomStringIntHashMap classes_frequencies = new CustomStringIntHashMap();
    private static CustomStringIntHashMap features_frequencies = new CustomStringIntHashMap();
    private static int all_classes_count = 0;
    private static int all_features_count = 0;
    private static int records_count = 0;
    private static File data_set_file = null;
    private static File output_file = null;
    private static FeatureSelectionMetricEnum metric = null;
    private static int selected_features_count = -1;
    private static int threads_count = 1;
    private static int features_per_thread;
    private static final Object mutex = new Object();
    private AtomicInteger working_threads;
    private volatile boolean finished_processing = false;
    private Map<String, Double> all_features_scores = null;

    private static void parse_command_line(String argv[]) {
        // parse options
        int i;
        for (i = 0; i < argv.length; i++) {
            if (argv[i].charAt(0) != '-') {
                // read data set file path
                if (data_set_file == null) {
                    data_set_file = new File(argv[i]);
                    if (!data_set_file.exists()) {
                        CustomLogger.logAndExit("Dataset file " + argv[i] + "does not exist");
                    }
                } else if (output_file == null) {   // read output file path
                    output_file = new File(argv[i]);
                    if (!output_file.exists()) {
                        try {
                            output_file.createNewFile();
                        } catch (IOException ex) {
                            CustomLogger.logAndExit(ex, "Could not create output file: " + argv[i]);
                        }
                    }
                    if (!output_file.canWrite()) {
                        CustomLogger.logAndExit("Output file " + argv[i] + " is not writable");
                    }
                } else {    // else this is the end of the params
                    break;
                }
            } else {
                if (++i >= argv.length) {
                    CustomLogger.exitWithUsage();
                }
                char option_char = argv[i - 1].charAt(1);
                switch (option_char) {
                    case 'h':
                        CustomLogger.exitWithUsage();
                        break;
                    case 'm':
                        try {
                            metric = FeatureSelectionMetricEnum.getMetricByKey(argv[i]);
                        } catch (IllegalArgumentException ex) {
                            CustomLogger.logAndExit(ex.getMessage());
                        }
                        break;
                    case 'n':
                        selected_features_count = Integer.valueOf(argv[i]).intValue();
                        break;
                    case 't':
                        threads_count = Integer.valueOf(argv[i]).intValue();
                        if (threads_count > Constants.THREADS_MAX_COUNT) {
                            threads_count = Constants.THREADS_MAX_COUNT;
                        }
                        break;
                    default:
                        CustomLogger.logAndExit("Invalid option: -" + option_char);
                }
            }
        }
        
        if (data_set_file == null || selected_features_count < 1 || metric == null) {
            CustomLogger.logAndExitWithUsage("Data set file, metric and number of selected features are mandatory paramters");
        }
    }

    private static void readDataSetFile() throws IOException {
        features_frequencies_per_class = new THashMap();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(data_set_file)));
        } catch (FileNotFoundException ex) {
            CustomLogger.logAndExit(ex, "Could not open Data set file for reading");
        }
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("\\s+");
            String class_name = parts[0];
            classes_frequencies.increment(class_name, 1);
            records_count++;
            for (int i = 1; i < parts.length; i++) {
                String[] feature_and_value = parts[i].split(":");
                String feature_name = feature_and_value[0];
                // the value of the feature is either 1 or nothing ////FIXME this part I am not sure about
                int feature_val = 0;
                try {
                    feature_val = Integer.valueOf(feature_and_value[1]).intValue();
                } catch (NumberFormatException ex) {
                    CustomLogger.logAndExit("The features in the data set are not boolean features.");
                }
                CustomStringIntHashMap feature_map;
                if (features_frequencies_per_class.containsKey(feature_name)) {
                    feature_map = features_frequencies_per_class.get(feature_name);
                } else {
                    feature_map = new CustomStringIntHashMap();
                    features_frequencies_per_class.put(feature_name, feature_map);
                }
                feature_map.increment(class_name, feature_val);
                features_frequencies.increment(feature_name, feature_val);
            }
        }
        br.close();
        all_features_count = features_frequencies.size();
        all_classes_count = classes_frequencies.size();

        // adjust threads count and features per thread
        if (threads_count > Math.ceil((double) all_features_count / (double) Constants.MIN_FEATURES_PER_THREAD)) {
            threads_count = (int) Math.ceil(all_features_count / Constants.MIN_FEATURES_PER_THREAD);
        }
        features_per_thread = (int) Math.ceil((double) all_features_count / (double) threads_count);
    }

    private void startThreadWithFeatures(Set<String> _features_names) {
        Runnable runnable = FeatureSelectionMetric.getInstance(
                metric,
                _features_names,
                features_frequencies_per_class,
                classes_frequencies,
                features_frequencies,
                records_count,
                this);
        Thread th = new Thread(runnable);
        th.start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] argv) {
        // parse the command options and read data set file into data structures
        parse_command_line(argv);
        try {
            readDataSetFile();
        } catch (IOException ex) {
            CustomLogger.logAndExit(ex, "Error while reading dataset file");
        }

        // create an instance of this class to act as an observer for threads
        FeatureSelectionApp app = new FeatureSelectionApp();
        app.working_threads = new AtomicInteger(threads_count);

        // distribute work on threads
        Iterator<String> fnames = features_frequencies.keySet().iterator();
        Set<String> features_names = new HashSet(features_per_thread);
        while (fnames.hasNext()) {
            features_names.add(fnames.next());
            if (features_names.size() >= features_per_thread) {
                app.startThreadWithFeatures(features_names);
                features_names = new HashSet(features_per_thread);
            }
        }
        if (features_names.size() > 0) {
            app.startThreadWithFeatures(features_names);
        }

        // make the app wait for all the threads to finish their work
        app.waitForAllThreads();
    }

    private void waitForAllThreads() {
        while (true) {
            try {
                synchronized (mutex) {
                    if (finished_processing) {
                        return;
                    }
                    mutex.wait(250);
                }
            } catch (InterruptedException ex) {
                CustomLogger.log(ex);
            }
        }
    }

    private void allThreadsDone() {
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
                } else if (diff < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        all_features_sorted_scores.putAll(all_features_scores);
        
        // put the top features in the outputfile
        Iterator<String> features_names = all_features_sorted_scores.keySet().iterator();
        int i = 0;
        while (features_names.hasNext() && i < selected_features_count) {
            String fname = features_names.next();
            os.println(fname + "\t" + all_features_sorted_scores.get(fname));
            i++;
        }
        if (output_file != null) {
            os.close();
        }

    }

    @Override
    public void selectedFeatures(TMap<String, Double> _features_scores) {
        ////TODO put the features scores in an aggregate data structure

        synchronized (mutex) {
            if (all_features_scores == null) {
                all_features_scores = new HashMap(all_features_count);
            }
            all_features_scores.putAll(_features_scores);
        }

        int w = working_threads.decrementAndGet();
        if (w <= 0) {
            allThreadsDone();
            synchronized (mutex) {
                finished_processing = true;
            }
        }
    }
}
