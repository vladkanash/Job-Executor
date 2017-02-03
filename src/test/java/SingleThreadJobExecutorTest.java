import main.java.com.vladkanash.executor.JobExecutor;
import main.java.com.vladkanash.executor.SingleThreadJobExecutor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 03.02.2017.
 */

public class SingleThreadJobExecutorTest {

    private JobExecutor executor;

    @Before
    public void setUp() {
        executor = new SingleThreadJobExecutor();
    }

    @After
    public void tearDown() {
        executor.shutdown();
    }

    @Test
    public void SerialJobExecutionTest() {
        final List<Integer> consumer = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int value = i;
            executor.execute(() -> consumer.add(value));
        }

        executor.execute(() ->
                Assert.assertArrayEquals(consumer.toArray(new Integer[5]), new Integer[]{0, 1, 2, 3, 4}));
    }

    @Test (timeout = 2000)
    public void NonBlockingJobExecutionTest() {
        executor.execute(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ParallelSupplierTest() {
        final ArrayList<Integer> consumer = new ArrayList<>();

        Runnable concurrentThread = () -> {
            for (int i = 0; i < 5; i++) {
                int value = i;
                executor.execute(() -> consumer.add(value));
            }
        };

        for (int i = 0; i < 3; i++) {
          new Thread(concurrentThread).start();
        }

    }

    @Test
    public void ExceptionTest() {
        final Set<Integer> consumer = new HashSet<>();

        executor.execute(() -> consumer.add(1));
        executor.execute(() -> {
            throw new IllegalStateException("Job exception");
        });
        executor.execute(() -> consumer.add(5));

        executor.execute(() ->
                Assert.assertEquals(consumer.size(), 2));
    }
}
