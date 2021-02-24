package net.lyczak.LafStudentUtils;

import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;

public class HelperUtils {
    private HelperUtils() {}

    public static String getScript(String script) {
        InputStream in = UglyTest.class.getClassLoader().getResourceAsStream(script);
        java.util.Scanner s = new Scanner(in).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static Date fromMoodleDate(long moodleDate) {
        return new Date(moodleDate * 1000);
    }
}
