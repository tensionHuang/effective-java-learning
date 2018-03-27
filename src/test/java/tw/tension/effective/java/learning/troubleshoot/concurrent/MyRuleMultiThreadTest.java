package tw.tension.effective.java.learning.troubleshoot.concurrent;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * Created by Tension Huang
 */
public class MyRuleMultiThreadTest {

    /**
     * reproduce ConcurrentModificationException
     */
    @Test
    public void concurrentTest() throws Exception {

        final ExecutorService executionService = Executors.newFixedThreadPool(10);
        final List<FutureTask<String>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final FutureTask<String> futureTask = new FutureTask<>(new MyTask());
            executionService.submit(futureTask);
            tasks.add(futureTask);
        }

        executionService.shutdown();

        for (final Future<String> ft : tasks) {
            final String result = ft.get();
            System.out.println(result);
        }
    }

    private static class MyTask implements Callable<String> {

        @Override 
        public String call() throws Exception {
            return MyRule.getInstance().getDefinitions().toString();
        }
    }
}