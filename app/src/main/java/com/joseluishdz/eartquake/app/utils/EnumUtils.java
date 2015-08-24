package com.joseluishdz.eartquake.app.utils;

import com.joseluishdz.eartquake.app.R;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by drac94 on 8/23/15.
 */
public class EnumUtils {
    public static final Map<Integer, Integer> colors;
    static {
        Map<Integer, Integer> aMap = new HashMap<Integer, Integer>();
        aMap.put(0, R.color.green);
        aMap.put(1, R.color.green_dark);
        aMap.put(2, R.color.blue);
        aMap.put(3, R.color.blue_dark);
        aMap.put(4, R.color.yellow);
        aMap.put(5, R.color.yellow_dark);
        aMap.put(6, R.color.orange);
        aMap.put(7, R.color.orange_dark);
        aMap.put(8, R.color.red);
        aMap.put(9, R.color.red_dark);
        colors = Collections.unmodifiableMap(aMap);
    }
}
