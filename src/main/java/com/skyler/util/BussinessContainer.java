package com.skyler.util;

import java.time.LocalDateTime;
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
public class BussinessContainer {

    private static Map<String, LocalDateTime> container = new HashMap<>();

    public static void set(String key, LocalDateTime value) {
        container.put(key, value);
    }

    public static Map<String, LocalDateTime> getContainer() {
        return container;
    }

    public static LocalDateTime getValue(String key) {
        return container.get(key);
    }

    public static int size() {
        return container.size();
    }

}
