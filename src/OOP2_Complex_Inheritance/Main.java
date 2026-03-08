package OOP2_Complex_Inheritance;

public class Main
{
    public static void main(String[] args)
    {
        PaymentMethod p1 = new CreditCardPayment("T101","Prathmesh");
        p1.processPayment(5000);
    }
}
