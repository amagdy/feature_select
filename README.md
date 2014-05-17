feature_select
===============

A Feature Selection application using PMI and Chi-squared metrics

Overview
---------
`feature_select` is a feature selection application that uses the following metrics:
- **PMI** : Pointwise Mutual Information (http://en.wikipedia.org/wiki/Pointwise_mutual_information)
- **Chi-Squared**: Pearson's chi-squared test (http://en.wikipedia.org/wiki/Pearson%27s_chi-squared_test)
 
 
Testing the application
------------------------
I have tested the code on the connect-4 data set as it has boolean features:
http://www.csie.ntu.edu.tw/~cjlin/libsvmtools/datasets/multiclass/connect-4


    Usage: ./feature_select [options] data_set_file [output_file]
    options:
    -h : to show this help
    -m metric_type : feature selection metric
	    pmi  -- PMI (Pointwise Mutual Information) feature selection metric
	    chi2 -- Chi^2 (X^2) feature selection metric
    -n number : number of features to select and return
    -t number : number of threads  [optional]

Sample use:

    chmod +x feature_select`
    ./feature_select -m pmi -n 10 -t 8 connect-4 selected_features_file`


