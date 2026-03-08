class Pen
{
    public synchronized void writeWithPenandPaper(Paper paper)
    {
        System.out.println(Thread.currentThread().getName() + " is writing with pen and trying to use "+paper.getClass());
        paper.finishWriting();
    }

    public synchronized void finishWriting()
    {
        System.out.println("Finishing writing with Pen and Paper");
    }
}

class Paper
{
    public synchronized void writeWithPaperandPen(Pen pen)
    {
        System.out.println(Thread.currentThread().getName() + " is writing with paper and trying to use "+pen.getClass());
        pen.finishWriting();
    }

    public synchronized void finishWriting()
    {
        System.out.println("Finishing writing with Paper and Pen");
    }
}

class Task1 extends Thread
{
    private Pen pen;
    private Paper paper;

    public Task1(Pen pen, Paper paper)
    {
        this.paper = paper;
        this.pen = pen;
    }

    @Override
    public void run()
    {
        paper.writeWithPaperandPen(pen);
    }
}

class Task2 extends Thread
{
    private Pen pen;
    private Paper paper;

    public Task2(Pen pen, Paper paper)
    {
        this.paper = paper;
        this.pen = pen;
    }

    @Override
    public void run()
    {
        pen.writeWithPenandPaper(paper);
    }

}

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Deadlock Implementation ****");

        Pen pen = new Pen();
        Paper paper = new Paper();

        Thread t1 = new Thread(  new Task1(pen, paper), "Thread-1");
        Thread t2 = new Thread(  new Task2(pen, paper), "Thread-2");

        t1.start();
        t2.start();
    }
}

//When you use synchronized on instance methods, the lock is acquired on the entire object, not just the individual method.

//Eg
//If any synchronized method M1 of class-A is accessed by an outside object, then for that
//Object ALL other synchronized methods of class-A are also blocked.

// Step 1: Thread-1 calls paper.writeWithPaperandPen(pen)
//         → Thread-1 acquires lock on the ENTIRE Paper object
//         → ALL synchronized methods of Paper object are now blocked for other threads

// Step 2: Thread-2 calls pen.writeWithPenandPaper(paper)  
//         → Thread-2 acquires lock on the ENTIRE Pen object
//         → ALL synchronized methods of Pen object are now blocked for other threads

// Step 3: Thread-1 (inside writeWithPaperandPen) tries to call pen.finishWriting()
//         → Thread-1 needs lock on Pen object
//         → BUT Thread-2 already holds the lock on Pen object
//         → Thread-1 gets BLOCKED

// Step 4: Thread-2 (inside writeWithPenandPaper) tries to call paper.finishWriting()
//         → Thread-2 needs lock on Paper object  
//         → BUT Thread-1 already holds the lock on Paper object
//         → Thread-2 gets BLOCKED
