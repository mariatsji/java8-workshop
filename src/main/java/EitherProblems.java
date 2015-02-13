import java.util.function.Function;

import no.finntech.lambdacompanion.Either;

/**
 * Either is not a part of the standard java 8 api, but can be found in
 * the lambda-companion project (https://git.finn.no/projects/LIBS/repos/lambda-companion/)
 */
public class EitherProblems {

    interface Account {

        /**
         * Get the balance of the account
         * @return the balance
         */
        Double getBalance();
    }

    interface AccountService {

        /**
         * Get an account by its number
         * @param accountNumber
         * @return either an IllegalStateException or an Account
         */
        Either<IllegalStateException, Account> getAccount(String accountNumber);
    }

    interface Customer {

        /**
         * Get the account number
         * @return null when the customer does not have an account
         */
        String getAccountNumber();
    }

    interface Database {

        /**
         * Get a customer by its id
         * @param id
         * @return either an IllegalStateException or a Customer
         */
        Either<IllegalStateException, Customer> getCustomer(long id);
    }

    private final Database database;

    private final AccountService accountService;

    public EitherProblems(final Database database, final AccountService accountService) {
        this.database = database;
        this.accountService = accountService;
    }

    // ----- TASKS BELOW ----

    /*
     * Complete the nonsense wtf()-method that returns either a String, or an Integer.
     * Q: When would you create such a method?
     * A: You would never create such a method.
     * Implement it however you like.
     * (Remember: An Either is one of two things, not both things at the same time.)
     */
    public Either<String, Integer> wtf() {
        return null;
    }

    /*
     * Extract the right (pun-intended) value from an Either if it is right
     * Provide a defaultValue() if the Either is left
     * See if you can use a fold on the Either. That way, you don't have to do any if-else'ing.
     *
     * The fold-method takes to lambdas, one for left and one for right
     */
    public String extractValueFrom(Either<Exception, String> either) {
        return null;
    }

    public static String defaultValue() {
        return "A failure happened.";
    }

    /*
     * Return Either an Integer value multiplied by 2 if the Either is right
     * , or the defaultIntValue() if the Either is left
     *
     */
    public Integer multiplyByTwo(Either<Exception, Integer> either) {
        return null;
    }

    public static Integer defaultIntValue() {
        return -1;
    }

    /*
     * Return the customer account number or an empty string given its id
     * Use database#getCustomer(id)
     */
    public String getAccountNumber(final Integer customerId) {
        return null;
    }

    /*
     * Return the customer account balance or 0.0 given its customerId
     * Use database#getCustomer(id) and accountService#getAccount(accountNumber)
     */
    public Double getBalance(final Integer customerId) {
        return null;
    }

}
