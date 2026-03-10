import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


class Worker extends Thread
{

    private CountDownLatch latch;
    private int delay;

    public Worker(CountDownLatch latch, int delay, String tname )
    {
        super(tname);
        this.latch = latch;
        this.delay = delay;
    }

    public void run()
    {
        try
        {
            Thread.sleep(delay);
           
            System.out.println("Thread : "+Thread.currentThread().getName()+" finished !");
           
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally
        {
            latch.countDown(); // always right in finally
            System.out.println("Count Value : "+latch.getCount());
        }
    }  
}
class CDLcalling
{
        public void initiateThreads()
        {
            System.out.println("***** COUNTDOWNLATCH ON 4 THREADS *******");

            // we have to provide exact count value with instatiating latch , else if value is bigger
            // and actual threads which are using latch are less then await() will go in infinite
            // and main thread waits countinously.


            CountDownLatch latch = new CountDownLatch(4);
            Worker w1 = new Worker(latch, 1000, "T1");
            Worker w2 = new Worker(latch, 2000, "T2");
            Worker w3 = new Worker(latch, 3000, "T3");
            Worker w4 = new Worker(latch, 4000, "T4");


            w1.start();
            w2.start();
            w3.start();
            w4.start();

            //latch.await();
           
            try{
                boolean isComplete = latch.await(6000, TimeUnit.MILLISECONDS);
                if (!isComplete) {
                    System.out.println("Time out !");
                }
            }
            catch(InterruptedException e)
            {
               
                Thread.currentThread().interrupt();
                System.err.println("Main thread interrupted while waiting");

            }

            System.out.println("Thread : "+Thread.currentThread().getName()+" finished !");
        }
}

class CDLusingExecutor {

    private final int taskCount = 4;
    private final ExecutorService executorService = Executors.newFixedThreadPool(taskCount);
    private final CountDownLatch latch = new CountDownLatch(taskCount);
    private final List<Future<String>> results = new ArrayList<>(taskCount);

    public void initiateThreads() throws TimeoutException {

        System.out.println("***** COUNTDOWNLATCH BY USING EXECUTOR & FUTURE *******");

        for (int i = 1; i <= taskCount; i++) {
            final int id = i;              // make i effectively final
            final int delay = i * 1000;    // capture delay

            Future<String> f = executorService.submit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(delay);
                    System.out.println("Task: " + id + " completed after " + delay + " ms");
                    return "Result of task " + id;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return "Task " + id + " was interrupted";
                } finally {
                    latch.countDown();  // exactly once
                }
            });

            results.add(f);
        }

        boolean isComplete = false;
        try {
            isComplete = latch.await(5, TimeUnit.SECONDS); // longest task is 4s
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted while waiting on latch");
        }

        if (!isComplete) {
            System.out.println("Timeout .... ");
        }

        System.out.println("Printing results ....");
        for (Future<String> f : results)
            {
            try {
               
                System.out.println(f.get(1, TimeUnit.SECONDS));
                //System.out.println(f.get());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Interrupted while getting a future result");
            } catch (ExecutionException e) {
                System.err.println("Task failed: " + e.getCause());
            }
        }

        executorService.shutdown();
    }

 
}
   


public class App {

    public static void main(String[] args) throws Exception
    {
        CDLcalling c = new CDLcalling();
        c.initiateThreads();

        CDLusingExecutor e = new CDLusingExecutor();
        e.initiateThreads();


       
    }
}

