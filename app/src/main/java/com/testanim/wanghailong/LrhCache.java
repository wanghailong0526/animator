package com.testanim.wanghailong;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : wanghailong
 * @description:
 */
public class LrhCache {
    static LinkedHashMap<Integer, Integer> map = new LinkedHashMap<>(0, 0.75f, true);

    public static void main(String[] args) {
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);
        map.put(7, 7);
        map.get(1);
        map.get(2);
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
