import java.util.Random;

public class Main extends Thread
{
    public static final int MAX_PASSWORD = 999;
    public static void main(String[] args)
    {
       
        Random random = new Random();
        Voult voult = new Voult(random.nextInt(MAX_PASSWORD));

         
            new AscendingHackerThread(voult).start();
            new DescendingHackerThread(voult).start();  
            new PoliceThread().start();
       
       
    }

    private static class Voult
    {
        private int password;
        Voult(int password)
        {
            this.password = password;
        }

        public boolean isPasswordCorrect(int guess)
        {
            try{
                Thread.sleep(100);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            //return true if the guess is correct
            return this.password == guess;
        }
    }

    private static class HackerThread extends Thread
    {
        protected Voult voult;

        public HackerThread(Voult voult)
        {
            this.voult = voult;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }
        @Override
        public void start()
        {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread
    {
        public AscendingHackerThread(Voult voult)
        {
            super(voult);
        }

        @Override
        public void run()
        {
            for(int g=0; g<MAX_PASSWORD; g++)
            {
                if(this.voult.isPasswordCorrect(g))
                {
                    System.out.println("Password is guessed by " + this.getName() + " is " + g);
                    System.exit(0);
                }
            }
        }
    }
    private static class DescendingHackerThread extends HackerThread
    {
        public DescendingHackerThread(Voult voult)
        {
            super(voult);
        }

        @Override
        public void run()
        {
            for(int g=MAX_PASSWORD; g>=0; g--)
            {
                if(this.voult.isPasswordCorrect(g))
                {
                    System.out.println("Password is guessed by " + this.getName() + " is " + g);
                    System.exit(0);
                }
            }
        }
    }
    private static class PoliceThread extends Thread
    {
        @Override
        public void run()
        {
            for(int i=10; i>0; i--)
            {
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }
                System.out.println(i+" Police is searching...");
            }
            System.out.println("Police found the hacker. Game over!");
            System.exit(0);
        }
    }
}

