package net.lyczak.LafStudentUtils;

import java.io.InputStream;
import java.util.Scanner;

public class BrowserDriverUtils {
    private BrowserDriverUtils() {}

    public static String getScript(String script) {
        InputStream in = UglyTest.class.getClassLoader().getResourceAsStream(script);
        java.util.Scanner s = new Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
