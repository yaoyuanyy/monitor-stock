package com.skyler.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * <pre>
 *
 * </pre>
 * NB.
 *
 * @author skyler_11@163.com
 * Created by on 2021-10-24 at 16:48
 */
public class StockMetaInfoContainer {

    private static Map<String, String> container = new HashMap<>();

    public static void set(String key, String value) {
        container.put(key, value);
    }

    public static Map<String, String> getContainer() {
        return container;
    }

    public static String get(String key) {
        return container.get(key);
    }

    public static int size() {
        return container.size();
    }

}
