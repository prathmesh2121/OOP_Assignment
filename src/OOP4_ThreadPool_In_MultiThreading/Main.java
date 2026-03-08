package OOP4_ThreadPool_In_MultiThreading;
import java.math.BigInteger;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Demonstration of factorial calculation using two approaches:
 * 1. Without ThreadPool - Creating individual threads manually
 * 2. With ThreadPool - Using ExecutorService for thread management
 * Shows performance comparison and thread management differences.
 */

public class Main
{
    /**
     * Calculates factorial using manual thread creation (no thread pool)
     * Creates a separate thread for each multiplication operation
     */
    public static BigInteger factorialWithoutThreadPool(int value)
    {
        System.out.println(" ******* FactorialWithOutThreadPool ******* ");
        long startTime = System.currentTimeMillis(); // Start timing
        
        // Use array to allow modification from lambda expressions
        // Lambda can only access final/effectively final variables
        final BigInteger[] ans = {BigInteger.ONE};
        Thread[] threads = new Thread[value]; // Array to hold all threads
        
        // Create and start a thread for each multiplication
        for(int i=1; i<=value; i++)
        {
            long finalI = i; // Must be effectively final for lambda
            threads[i-1] = new Thread( ()->{
                // Multiply current result by current number
                ans[0] = ans[0].multiply(BigInteger.valueOf(finalI));
                System.out.println("factorial of "+ finalI + " is = "+ ans[0]);
            });
            threads[i-1].start(); // Start the thread
        }

        // Wait for all threads to complete using join()
        for(Thread t : threads)
        {
            try{
                t.join(); // Block until thread finishes
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt(); // Restore interrupt flag
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Required time for computation is = "+(endTime - startTime) + " milliseconds");
        return ans[0];
    }
    /**
     * Calculates factorial using ThreadPool (ExecutorService)
     * Reuses threads from a fixed pool instead of creating new ones
     * @param value The number to calculate factorial for
     * @return BigInteger factorial result
     */
    public static BigInteger factorialWithThreadPool(int value)
    {
        System.out.println(" ******* FactorialWithThreadPool ******* ");
        long startTime = System.currentTimeMillis();
        // Create thread pool with 5 thread (can be increased for parallel processing)
        ExecutorService executor = Executors.newFixedThreadPool(5);

        final BigInteger[] ans = {BigInteger.ONE}; // Array for lambda modification
        
        // Submit tasks to thread pool instead of creating threads manually
        for(int i=1; i<=value; i++)
        {
            long finalI = i; // Must be effectively final for lambda
            executor.submit(
                    ()->{
                        ans[0] = ans[0].multiply(BigInteger.valueOf(finalI));
                        System.out.println("factorial of "+ finalI + " is = "+ ans[0]);
                    }
            );
        }
        
        // Shutdown executor - no new tasks will be accepted
        executor.shutdown();

        // Wait for all submitted tasks to complete (timeout: 100 seconds)
        try {
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Required time for computation is = "+(endTime - startTime) + " milliseconds");
        return ans[0];
    }

    //Main method Compares performance between manual thread creation and thread pool

    public static void main(String s[])
    {
        int x = 10;
        factorialWithoutThreadPool(x);
        factorialWithThreadPool(x);
    }

}
