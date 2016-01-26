package connectn;

/*
CS4341 - Artificial Intelligence - WPI - Project 1

Akshay Thejaswi
William Hartman
 */

import java.io.PrintWriter;

public class DebugPrinter {
    public static final String DEBUG_FILE_PATH = "debug-log.txt";
    private static PrintWriter writer;

    static {
        try {
            DebugPrinter.writer = new PrintWriter(DEBUG_FILE_PATH, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void print(String toWrite) {
        writer.print(toWrite);
        writer.flush();
    }

    public static void println(String toWrite) {
        writer.println(toWrite);
        writer.flush();
    }
}
