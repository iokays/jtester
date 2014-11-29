package com.iokays.test.data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class TestDataMap {
    
    private static final Logger log =LoggerFactory.getLogger(TestDataMap.class);
    
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
                        Path path2 = Paths.get(temp.trim());
                        
                        byte[] byBuffer = Files.readAllBytes(path2); 
                        String strRead = new String(byBuffer);
                        @SuppressWarnings("serial")
                        Map<String, List<Object>> retMap = new Gson().fromJson(strRead, new TypeToken<HashMap<String, List<Object>>>() {}.getType());
                        if (null != retMap && !retMap.isEmpty()) {
                            Map<String, List<Object>> retMapTemp = Maps.newHashMap();
                            Set<String> set = retMap.keySet();
                            for (String string : set) {
                                List<Object> list = retMap.get(string);
                                retMapTemp.put(string.toLowerCase(), list);
                            }
                            final String fileName = path2.toFile().getName();
                            map.put(fileName.substring(0, fileName.indexOf(".")).toLowerCase(), retMapTemp);
                        }
                    } catch (Exception e) {
                        log.error(e.toString());
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
        log.info(map.toString());
        return map;
    }
    
    public static void main(String[] args) {
        
    }
}
