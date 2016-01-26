package connectn;

/*
CS4341 - Artificial Intelligence - WPI - Project 1

Akshay Thejaswi
William Hartman
 */

import java.util.concurrent.ConcurrentHashMap;


public class Stopwatch {

    private static ConcurrentHashMap<String, Long> timers = new ConcurrentHashMap<String, Long>();

    public static void start(String key){
        timers.put(key, System.currentTimeMillis());
    }

    public static void check(String key){
        long start = timers.get(key);
        System.out.printf("%s %dms\n", key, System.currentTimeMillis()-start);
    }

    public static void stop(String key){
        long start = timers.remove(key);
        System.out.printf("%s %dms\n", key, System.currentTimeMillis()-start);
    }
}