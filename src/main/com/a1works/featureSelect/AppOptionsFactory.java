package com.a1works.featureSelect;

import com.a1works.utils.MayBe;

/**
 * Created by magdy on 11.10.15.
 */
public interface AppOptionsFactory {
    public MayBe<AppOptions> getAppOptions();
}
