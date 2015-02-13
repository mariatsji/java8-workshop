import java.util.List;
import java.util.Optional;
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
        return Optional.ofNullable(nullableValue);
    }

    public Optional<Integer> createOptionalOfNonNullable(final Integer nonNullableValue) {
        return Optional.of(nonNullableValue);
    }

    public Optional<Integer> createEmptyOptional() {
        return Optional.empty();
    }

    public String returnValueWithinOptionalOrDefaultValue(final Optional<String> optional, final String defaultValue) {
        return optional.orElse(defaultValue);
    }

    public String returnValueWithinOptionalOrFetchDefaultValue(final Optional<String> optional,
                                                               final Supplier<String> defaultValueSupplier) {
        return optional.orElseGet(defaultValueSupplier);
    }

    /*
     * Use someService#getDefaultValue() as a Supplier<String> for the default value
     */
    public String returnValueWithinOptionalOrUseDefaultValueMethod(final Optional<String> optional) {
//        // 1st possibility
//        return optional.orElseGet(new Supplier<String>() {
//            @Override
//            public String get() {
//                return someService.getDefaultValue();
//            }
//        });
//
//        // 2nd possibility
//        return optional.orElseGet(() -> someService.getDefaultValue());

        // 3rd possibility
        return optional.orElseGet(someService::getDefaultValue);
    }

    public String returnValueWithinOptionalOrThrowRuntimeException(final Optional<String> optional) {
//        // 1st possibility
//        return optional.orElseThrow(new Supplier<RuntimeException>() {
//            @Override
//            public RuntimeException get() {
//                return new RuntimeException();
//            }
//        });
//
//        // 2nd possibility
//        return optional.orElseThrow(() -> new RuntimeException());

        // 3rd possibility
        return optional.orElseThrow(RuntimeException::new);
    }

    /*
     * Hint: when you want to transform a value inside an Optional, use Optional#map(Function)
     */
    public Optional<String> turnOptionalIntegerIntoOptionalString(final Optional<Integer> optionalInteger) {
//        // 1st possibility
//        return optionalInteger.map(new Function<Integer, String>() {
//            @Override
//            public String apply(final Integer value) {
//                return value.toString();
//            }
//        });
//
//        // 2nd possibility
//        return optionalInteger.map(value -> value.toString());

        // 3rd possibility
        return optionalInteger.map(Object::toString);
    }

    /*
     * Hint: combine Optional#map(Function) and Optional#orElse(T)
     */
    public String turnIntegerIntoStringOrEmptyString(final Optional<Integer> integer) {
//        // 1st possibility
//        return integer.map(new Function<Integer, String>() {
//            @Override
//            public String apply(final Integer value) {
//                return value.toString();
//            }
//        })
//                      .orElse("");
//
//        // 2nd possibility
//        return integer.map(value -> value.toString()).orElse("");

        // 3rd possibility
        return integer.map(Object::toString).orElse("");
    }

    /*
     * Use someService#tryMakeAnInteger(String) and flatten the result of the mapping
     *
     * Hint: to flatten and map at the same time, use Optional#flatMap(Function)
     */
    public Optional<Integer> tryMakeAnIntegerOutOfAnOptionalString(final Optional<String> optionalValue) {
//        // 1st possibility
//        return optionalValue.flatMap(new Function<String, Optional<Integer>>() {
//            @Override
//            public Optional<Integer> apply(final String value) {
//                return someService.tryMakeAnInteger(value);
//            }
//        });
//
//        // 2nd possibility
//        return optionalValue.flatMap(value -> someService.tryMakeAnInteger(value));

        // 3rd possibility
        return optionalValue.flatMap(someService::tryMakeAnInteger);
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
        // 1st possibility
//        return Optional.ofNullable(value)
//                       .filter(new Predicate<String>() {
//                           @Override
//                           public boolean test(final String str) {
//                               return !str.trim().isEmpty();
//                           }
//                       });

        // 2nd possibility
        return Optional.ofNullable(value).filter(str -> !str.trim().isEmpty());
    }

    /*
     * Execute the side effect someService#printOut(String) only when the value is present
     *
     * Hint: side-effects can be executed through Optional#ifPresent(Consumer)
     */
    public void executeSideEffectWhenValueIsPresent(Optional<String> optional) {
//        // 1st possibility
//        optional.ifPresent(new Consumer<String>() {
//            @Override
//            public void accept(final String value) {
//                someService.printOut(value);
//            }
//        });
//
//        // 2nd possibility
//        optional.ifPresent(value -> someService.printOut(value));

        // 3rd possibility
        optional.ifPresent(someService::printOut);
    }

    /*
     * Keep all the integers from the present (non-empty) optionals and return them as a List
     *
     * Hint: use List#stream() to then use Stream#flatMap(Function) where the mapping function
     *       turns each Optional in the list into a Stream of one or an empty Stream (Optional#map(Function))
     */
    public List<Integer> retainAllIntegers(List<Optional<Integer>> list) {
        //alternative 1
//        return list.stream().filter(Optional::isPresent).map(Optional::get).collect(toList());

        //alternative 2
//        return list.stream().flatMap(opt -> opt.map(Stream::of).orElseGet(Stream::empty)).collect(toList());

        //alternative 3
        return list.stream().flatMap(OptionalProblems::stream).collect(toList());
    }

    private static <T> Stream<T> stream(final Optional<T> optional) {
        return optional.map(Stream::of).orElseGet(Stream::empty);
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

        return Optional.ofNullable(customerId)
                       .map(database::getCustomer)
                       .map(Customer::getAccountNumber)
                       .map(accountService::getAccount)
                       .map(Account::getBalance)
                       .orElse(0.0);
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
        return customers.stream()
                        .filter(customer -> customer.getAccountNumber().startsWith("NO"))
                        .findFirst()
                        .map(customer -> customer.getAccountNumber())
                        .orElse("");
    }


}
