/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package featureselectionapp;

import gnu.trove.map.hash.THashMap;

/**
 *
 * @author magdy
 */
public class CustomStringIntHashMap extends THashMap<String, Integer> {
    
    public void increment(String key, int inc) {
        if (this.containsKey(key)) {
            inc += this.get(key).intValue();
        }
        this.put(key, inc);
    }
    
    public void decrement(String key, int dec) {
        if (this.containsKey(key)) {
            dec = this.get(key).intValue() - dec;
        }
        this.put(key, dec);
    }
}
