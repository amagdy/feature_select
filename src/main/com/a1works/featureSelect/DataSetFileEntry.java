/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.a1works.featureSelect;


import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author magdy
 */
public class DataSetFileEntry {
    public String class_name;
    public Map<String, Double> features;
    
    public static DataSetFileEntry getInstanceByLineString(String _line) {
        DataSetFileEntry entry = new DataSetFileEntry();
        String[] parts = _line.split("\\s+");
        entry.class_name = parts[0];
        entry.features = new HashMap<>(parts.length - 1);
        for (int i = parts.length - 1; i >= 1; i--) {
            String[] feature_and_value = parts[i].split(":");
            entry.features.put(feature_and_value[0], Double.valueOf(feature_and_value[1]));
        }
        return entry;
    }
    
}
