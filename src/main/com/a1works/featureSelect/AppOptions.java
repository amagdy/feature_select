package com.a1works.featureSelect;

import com.a1works.utils.MayBe;

import java.io.File;

public interface AppOptions {
    public FeatureSelectionAlgorithm getAlgorithm();
    public File getDataSetFile();
    public int getSelectedFeaturesCount();
    public MayBe<File> getOutputFile();
    public int getThreadsCount();
}
