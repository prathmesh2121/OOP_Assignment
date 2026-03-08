package OOP2_Complex_Inheritance;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.logging.Logger;

public class CreditCardPayment extends AbstractPayment
{
    protected String cardNum;
    protected int cvv;

    CreditCardPayment(String transactionId, String accountHolderName)
    {
        super(transactionId, accountHolderName);
    }

    @Override
    public void validateDetails() {

        Scanner s = new Scanner(System.in);
        System.out.println("Enter card num : ");
        String cardNum = s.next();

        if(cardNum.length() != 4)
        {
            System.out.println("Please enter 4 digits credit card number...");
            System.exit(0);
        }
        else
        {
            System.out.println("Proceeding with credit card...");
        }
    }


    @Override
    public boolean processPayment(double amount) {
        this.validateDetails();
        if(amount < 1 || amount > 50000)
        {
            System.out.println("Enter a valid amount...");
            try {
                throw new Exception("Incorrect value entered");
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        }
        else
        {
            Logger logger = Logger.getLogger(CreditCardPayment.class.getName());
            logger.info("Details verified , Transaction Under Processing ...");

            System.out.println("Payment successful !");
            this.printReceipt(amount);
            this.calculateProcessingFee(amount);
            return true;
        }
    }

    @Override
    protected double calculateProcessingFee(double amount)
    {
        amount = (amount*2)/100;
        System.out.println("applied processing fee : "+ (amount * 2)/100);
        return amount;
    }
}
