/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureselectionapp;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

/**
 *
 * @author magdy
 */
public class DataSetFileEntry {
    public String class_name;
    public TMap<String, Double> features;
    
    public static DataSetFileEntry getInstanceByLineString(String _line) {
        DataSetFileEntry entry = new DataSetFileEntry();
        String[] parts = _line.split("\\s+");
        entry.class_name = parts[0];
        entry.features = new THashMap(parts.length - 1);
        for (int i = parts.length - 1; i >= 1; i--) {
            String[] feature_and_value = parts[i].split(":");
            entry.features.put(feature_and_value[0], Double.valueOf(feature_and_value[1]));
        }
        return entry;
    }
    
}
