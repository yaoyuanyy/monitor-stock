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
public class Container {

    private static Map<String, String> tokenContainer = new HashMap<>();

    public static void setToken(String key, String value) {
        tokenContainer.put(key, value);
    }

    public static Map<String, String> getTokenContainer() {
        return tokenContainer;
    }

    public static String getToken(String key) {
        return tokenContainer.get(key);
    }

    public static int size() {
        return tokenContainer.size();
    }

}
