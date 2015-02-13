import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

final class OptionalProblems {

    interface SomeService {
        String getDefaultValue();
        Optional<Integer> tryMakeAnInteger(String value);
        void printOut(String value);
    }

    interface Account {

        /**
         * Get the balance of the account
         * @return null when no account history
         */
        Double getBalance();
    }

    interface AccountService {

        /**
         * Get an account by its number
         * @param accountNumber
         * @return null when account cannot be found
         */
        Account getAccount(String accountNumber);
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
         * @return null when the customer cannot be found
         */
        Customer getCustomer(long id);
    }

    private final SomeService someService;

    private final Database database;

    private final AccountService accountService;

    OptionalProblems(final SomeService someService, final Database database, final AccountService accountService) {
        this.someService = someService;
        this.database = database;
        this.accountService = accountService;
    }

    // ----- TASKS BELOW -----

    public Optional<Integer> createOptionalOfNullable(final Integer nullableValue) {
        return null;
    }

    public Optional<Integer> createOptionalOfNonNullable(final Integer nonNullableValue) {
        return null;
    }

    public Optional<Integer> createEmptyOptional() {
        return null;
    }

    public String returnValueWithinOptionalOrDefaultValue(final Optional<String> optional, final String defaultValue) {
        return null;
    }

    public String returnValueWithinOptionalOrFetchDefaultValue(final Optional<String> optional,
                                                               final Supplier<String> defaultValueSupplier) {
        return null;
    }

    /*
     * Use someService#getDefaultValue() as a Supplier<String> for the default value
     */
    public String returnValueWithinOptionalOrUseDefaultValueMethod(final Optional<String> optional) {
        return null;
    }

    public String returnValueWithinOptionalOrThrowRuntimeException(final Optional<String> optional) {
        return null;
    }

    /*
     * Hint: when you want to transform a value inside an Optional, use Optional#map(Function)
     */
    public Optional<String> turnOptionalIntegerIntoOptionalString(final Optional<Integer> optionalInteger) {
        return null;
    }

    /*
     * Hint: combine Optional#map(Function) and Optional#orElse(T)
     */
    public String turnIntegerIntoStringOrEmptyString(final Optional<Integer> integer) {
        return null;
    }

    /*
     * Use someService#tryMakeAnInteger(String) and flatten the result of the mapping
     *
     * Hint: to flatten and map at the same time, use Optional#flatMap(Function)
     */
    public Optional<Integer> tryMakeAnIntegerOutOfAnOptionalString(final Optional<String> optionalValue) {
        return null;
    }

    /*
     * Make the given value an Optional that will be empty if the String was null or an empty String
     *
     * Hint: combine Optional#ofNullable(T) and Optional#filter(Predicate)
     *
     * @param value may be null
     * @return an Optional that will be empty if the String was null or an empty String
     */
    public Optional<String> keepNonEmptyNullableString(final String value) {
        return null;
    }

    /*
     * Execute the side effect someService#printOut(String) only when the value is present
     *
     * Hint: side-effects can be executed through Optional#ifPresent(Consumer)
     */
    public void executeSideEffectWhenValueIsPresent(Optional<String> optional) {
        ;
    }

    /*
     * Keep all the integers from the present (non-empty) optionals and return them as a List
     *
     * Hint: use List#stream() to then use Stream#flatMap(Function) where the mapping function
     *       turns each Optional in the list into a Stream of one or an empty Stream (Optional#map(Function))
     */
    public List<Integer> retainAllIntegers(List<Optional<Integer>> list) {
        return null;
    }

    /*
     * Use database and accountService to retrieve the balance of the account of the client given its id
     * Return a balance of 0.0 in case the account does not have a balance (no operations history)
     *
     * @param customerId may be null
     * @return the balance of the account, or 0.0 in case the account does not have a balance
     */
    public Double getBalance(final Integer customerId) {
        // the unsafe way to do it
//        return accountService.getAccount(database.getCustomer(customerId).getAccountNumber()).getBalance();

        // the null-check way to do it
//        Double balance = 0.0;
//        if(customerId != null) {
//            final Customer customer = database.getCustomer(customerId);
//            if(customer != null) {
//                final String accountNumber = customer.getAccountNumber();
//                if(accountNumber != null) {
//                    final Account account = accountService.getAccount(accountNumber);
//                    if(account != null) {
//                        balance = account.getBalance();
//                    }
//                }
//            }
//        }
//        return balance;
        
        return null;
    }

    /*
     * Find the first customer in the list whose account number starts with "NO" and return that account number.
     * If no account is matching, return an empty String.
     *
     * Hint: for each customer (List#stream()), check if the account number starts with "NO" (Stream#filter(Predicate))
     *       and then find the first of them (Stream#findFirst()) which you will transform into its account number
     *       (Optional#map(Function)) or return an empty String (Optional#orElse(T)).
     *
     * @param customers can be empty
     * @return first customer account number starting with "NO" or an empty String
     */
    public String getFirstCustomerAccountNumberStartingWithNO(final List<Customer> customers) {
        return null;
    }

}
