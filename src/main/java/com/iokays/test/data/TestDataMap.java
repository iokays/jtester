package com.iokays.test.data;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class TestDataMap {
    
    public static final Map<String, Map<String, List<Object>>> map = getMap();
    
    private static Map<String, Map<String, List<Object>>> getMap() {
        Map<String, Map<String, List<Object>>> map = Maps.newHashMap();
        try {
            ResourceBundle rb = ResourceBundle.getBundle("jtester", Locale.getDefault());
            String path = rb.getString("files");
            String[] paths = path.split(",");
            
            if (null != paths && 0 != paths.length) {
                for (String temp : paths) {
                    try {
                        InputStream inputStream = ClassLoader.getSystemResourceAsStream(temp);
                        String context = IOUtils.toString(inputStream);
                        @SuppressWarnings("serial")
                        Map<String, List<Object>> retMap = new Gson().fromJson(context, new TypeToken<HashMap<String, List<Object>>>() {}.getType());
                        if (null != retMap && !retMap.isEmpty()) {
                            Map<String, List<Object>> retMapTemp = Maps.newHashMap();
                            Set<String> set = retMap.keySet();
                            for (String string : set) {
                                List<Object> list = retMap.get(string);
                                retMapTemp.put(string.toLowerCase(), list);
                            }
                            map.put(temp.substring(0, temp.indexOf(".")).toLowerCase(), retMapTemp);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(map);
        return map;
    }
    
}
