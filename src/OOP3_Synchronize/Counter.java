package OOP3_Synchronize;

public class Counter
{
    private int count = 0;


    //public synchronized void increment()
    public void increment()
    {
        synchronized(this) // synchronize only this block of method code
        {
            count++;
        }

    }

    public int getCount()
    {
        return this.count;
    }


}
