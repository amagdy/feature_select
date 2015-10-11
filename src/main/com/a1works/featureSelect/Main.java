package com.a1works.featureSelect;

import com.a1works.utils.MayBe;

/**
 * Created by magdy on 11.10.15.
 */
public class Main {
    public static void main(String[] argv) {
        // parse the command options and read data set file into data structures
        CommandLineOptionsParser algorithmFactory = CommandLineOptionsParser.getInstance();
        algorithmFactory.parseCommandLine(argv);
        MayBe<AppOptions> maybe_options = algorithmFactory.getAppOptions();
        if (!maybe_options.iterator().hasNext())
            throw new UsageException("No application options were provided");
        AppOptions options = maybe_options.iterator().next();

        // Create and run the application
        Application app = new Application(options);
        app.run();
    }
}
