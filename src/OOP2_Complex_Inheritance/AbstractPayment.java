    package OOP2_Complex_Inheritance;

    public abstract class AbstractPayment implements PaymentMethod
    {
        protected String transactionId;
        protected String accountHolderName;

        AbstractPayment(String transactionId, String accountHolderName)
        {
            this.transactionId = transactionId;
            this.accountHolderName = accountHolderName;
        }

        protected abstract double calculateProcessingFee(double amount);

        public void printReceipt(double amount)
        {
            System.out.println("******* Transaction Details ********");
            System.out.println("Amount : "+amount);
            System.out.println("Account Holder name : "+this.accountHolderName);
            System.out.println("Transaction id : "+this.transactionId);

        }

    }

