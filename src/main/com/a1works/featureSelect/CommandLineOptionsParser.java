package com.a1works.featureSelect;

import com.a1works.utils.MayBe;
import java.io.File;
import static org.junit.runner.notification.RunListener.ThreadSafe;

@ThreadSafe
public final class CommandLineOptionsParser implements AppOptionsFactory {

    ///TODO read this from application.properties
    private static final int THREADS_MAX_COUNT = 64;

    private AppOptions options;
    private static CommandLineOptionsParser instance;

    public static CommandLineOptionsParser getInstance() {
        if (instance == null) {
            synchronized (CommandLineOptionsParser.class) {
                if (instance == null) {
                    instance = new CommandLineOptionsParser();
                }
            }
        }
        return instance;
    }

    private CommandLineOptionsParser(){}

    @Override
    public synchronized MayBe<AppOptions> getAppOptions() {
        return new MayBe<AppOptions>(options);
    }



    public void parseCommandLine(final String arguments[]) {
        // parse options
        AppOptionsImpl tmpOptions = new AppOptionsImpl();

        int i;
        for (i = 0; i < arguments.length; i++) {
            if (arguments[i].charAt(0) != '-') {
                // read data set file path
                if (tmpOptions.dataSetFile == null) {
                    tmpOptions.dataSetFile = new File(arguments[i]);
                    if (!tmpOptions.dataSetFile.exists())
                        throw new UsageException(String.format("Data Set File '%s' does not exist", arguments[i]));

                } else if (tmpOptions.outputFile == null) {   // read output file path
                    tmpOptions.outputFile = new File(arguments[i]);

                } else {    // else this is the end of the params
                    break;
                }
            } else {
                if (++i >= arguments.length) {
                    CustomLogger.exitWithUsage();
                }
                char option_char = arguments[i - 1].charAt(1);
                switch (option_char) {
                    case 'h':
                        CustomLogger.exitWithUsage();
                        break;
                    case 'm':
                        try {
                            tmpOptions.algorithm = FeatureSelectionAlgorithmFactory.getInstance().getAlgorithmByName(arguments[i]);
                        } catch (IllegalArgumentException ex) {
                            throw new UsageException(String.format("Invalid algorithm name '%s'", arguments[i]));
                        }
                        break;
                    case 'n':
                        tmpOptions.selectedFeaturesCount = Integer.valueOf(arguments[i]).intValue();
                        break;
                    case 't':
                        tmpOptions.threadsCount = Integer.valueOf(arguments[i]).intValue();
                        if (tmpOptions.threadsCount > THREADS_MAX_COUNT) {
                            throw new UsageException(String.format("Thread Count cannot be more than %d", THREADS_MAX_COUNT));
                        }
                        break;
                    default:
                        CustomLogger.logAndExit("Invalid option: -" + option_char);
                }
            }
        }

        if (tmpOptions.dataSetFile == null || tmpOptions.selectedFeaturesCount < 1 || tmpOptions.algorithm == null) {
            throw new UsageException("Data set file, metric and number of selected features are mandatory parameters");
        }
        synchronized (this) {
            this.options = tmpOptions;
        }
    }

    private static class AppOptionsImpl implements AppOptions {
        private FeatureSelectionAlgorithm algorithm;
        private File dataSetFile;
        private int selectedFeaturesCount;
        private File outputFile;
        private int threadsCount;

        @Override
        public FeatureSelectionAlgorithm getAlgorithm() {
            return algorithm;
        }

        @Override
        public File getDataSetFile() {
            return dataSetFile;
        }

        @Override
        public int getSelectedFeaturesCount() {
            return selectedFeaturesCount;
        }

        @Override
        public MayBe<File> getOutputFile() {
            return new MayBe<File>(outputFile);
        }

        @Override
        public int getThreadsCount() {
            return threadsCount;
        }
    }

}
