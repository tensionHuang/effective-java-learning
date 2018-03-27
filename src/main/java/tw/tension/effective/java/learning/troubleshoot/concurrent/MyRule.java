package tw.tension.effective.java.learning.troubleshoot.concurrent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tension Huang
 */
public class MyRule {

    private final List<String> definitions = new ArrayList<>();
    private static MyRule singleton = new MyRule();
    private MyRule() {
    }
    public static MyRule getInstance() {
        return singleton;
    }

    public List<String> getDefinitions() throws InterruptedException {
        System.out.println(this.toString() + " getDefinitions!");
        // note: add synchronized to fix the ConcurrentModificationException
//        synchronized (this.definitions) {
            if (definitions.isEmpty()) {
                init();
                return filterRuleDefinitions(definitions);
            }
//        }
        return definitions;
    }

    private List<String> filterRuleDefinitions(List<String> def) {
        List<String> results = new ArrayList<>();
        for (String d : def) {
            if (isInt(d)) {
                results.add(d);
            }
        }
        return results;
    }

    private void init() throws InterruptedException {
        System.out.println("init rule definitions");
        List<String> properties = Arrays.asList("123", "456", "789", "abc", "efg", "ijk", "1", "2", "3", "4", "5", "6", "7");
        for (String p : properties) {
            Thread.sleep(200);
            definitions.add(p);
        }
    }

    private boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
