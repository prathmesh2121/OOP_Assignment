package OOP3_Synchronize;

public class Main
{
    public static void main(String[] args)
    {
        Counter counter = new Counter();
        ThreadManagement t1 = new ThreadManagement(counter);
        ThreadManagement t2 = new ThreadManagement(counter);

        t1.start();
        t2.start();

        try{

            t1.join();

            t2.join();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Final counter value: " + counter.getCount());

    }
}
