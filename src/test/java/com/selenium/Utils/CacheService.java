package com.selenium.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sudha on 04/10/2018.
 */
public class CacheService {
    private static Map<String, AnyData> dataMap = new HashMap<>();
    private static CacheService
            cacheService;

    public static CacheService getInstance() {
        if (cacheService == null) {
            cacheService = new CacheService();
        }
        return cacheService;
    }

    public static void clearCacheInstance() {

        cacheService = null;
    }

    public void destroyValues(String key) {

        dataMap.remove(key);
    }

    public Map<String, AnyData> getDataMap() {

        return dataMap;
    }

    public void setDataMap(String key, AnyData data) {
        dataMap.put(key, data);
    }

}
