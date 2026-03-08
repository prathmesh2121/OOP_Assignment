package OOP3_Synchronize;

public class ThreadManagement extends Thread
{

    private Counter counter;

    public ThreadManagement(Counter counter)
    {
        this.counter = counter;
    }

    @Override
    public void run(){
        for(int i=0; i<1000; i++)
        {
            counter.increment();
        }
    }



}
