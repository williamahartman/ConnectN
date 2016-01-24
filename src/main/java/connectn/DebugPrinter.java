package connectn;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by will on 1/24/16.
 */
public class DebugPrinter {
    public static final String DEBUG_FILE_PATH = "debug-log.txt";
    private static PrintWriter writer;

    static {
        try {
            DebugPrinter.writer = new PrintWriter(DEBUG_FILE_PATH, "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
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
