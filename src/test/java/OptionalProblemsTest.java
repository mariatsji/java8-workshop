import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public final class OptionalProblemsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final OptionalProblems.SomeService someService = mock(OptionalProblems.SomeService.class);

    private final OptionalProblems.Database database = mock(OptionalProblems.Database.class);

    private final OptionalProblems.AccountService accountService = mock(OptionalProblems.AccountService.class);

    private final OptionalProblems optionalProblems = new OptionalProblems(someService, database, accountService);

    @Test
    public void create_an_optional_of_nullable_value() {
        // given
        final Integer nullValue = null;

        // when
        final Optional<Integer> optional = optionalProblems.createOptionalOfNullable(nullValue);

        // then
        assertNotNull(optional);
        assertFalse(optional.isPresent());
    }

    @Test
    public void create_an_optional_of_non_nullable_value() {
        // given
        final Integer nonNullValue = 42;

        // when
        final Optional<Integer> optional = optionalProblems.createOptionalOfNonNullable(nonNullValue);

        // then
        assertNotNull(optional);
        assertTrue(optional.map(x -> true).orElse(false));
    }

    @Test
    public void create_an_empty_optional() {
        // when
        final Optional<Integer> optional = optionalProblems.createEmptyOptional();

        // then
        assertNotNull(optional);
        assertFalse(optional.isPresent());
    }

    @Parameters({"true,value", "false,"})
    @Test
    public void return_value_within_optional_or_default_value(final boolean present, final String value) {
        // given
        final Optional<String> optional = present ? Optional.of(value) : Optional.<String>empty();

        // when
        final String stringValue = optionalProblems.returnValueWithinOptionalOrDefaultValue(optional, "default");

        // then
        assertNotNull(stringValue);
        final String expected = present ? String.valueOf(value) : "default";
        assertEquals(expected, stringValue);
    }

    @Parameters({"true,value,0", "false,,1"})
    @Test
    public void return_value_within_optional_or_fetch_default_value(final boolean present, final String value, final int nrOfInvocations) {
        // given
        final Optional<String> optional = present ? Optional.of(value) : Optional.<String>empty();

        // when
        final Supplier<String> defaultValueSupplier = mock(Supplier.class);
        when(defaultValueSupplier.get()).thenReturn("default");
        final String stringValue = optionalProblems.returnValueWithinOptionalOrFetchDefaultValue(optional, defaultValueSupplier);

        // then
        assertNotNull(stringValue);
        final String expected = present ? String.valueOf(value) : "default";
        assertEquals(expected, stringValue);
        verify(defaultValueSupplier, times(nrOfInvocations)).get();
    }

    @Parameters({"true,value,0", "false,,1"})
    @Test
    public void return_value_within_optional_or_use_default_value_method(final boolean present, final String value, final int nrOfInvocations) {
        // given
        final Optional<String> optional = present ? Optional.of(value) : Optional.<String>empty();

        // when
        when(someService.getDefaultValue()).thenReturn("default");
        final String stringValue = optionalProblems.returnValueWithinOptionalOrUseDefaultValueMethod(optional);

        // then
        assertNotNull(stringValue);
        final String expected = present ? String.valueOf(value) : "default";
        assertEquals(expected, stringValue);
        verify(someService, times(nrOfInvocations)).getDefaultValue();
    }

    @Parameters({"true,value,0", "false,,1"})
    @Test
    public void return_value_within_optional_or_throw_a_runtime_exception(final boolean present, final String value, final int nrOfInvocations) {
        // exception
        if(!present) {
            thrown.expect(RuntimeException.class);
        }

        // given
        final Optional<String> optional = present ? Optional.of(value) : Optional.<String>empty();

        // when
        final String stringValue = optionalProblems.returnValueWithinOptionalOrThrowRuntimeException(optional);

        // then
        assertNotNull(stringValue);
        if(present) {
            assertEquals(value, stringValue);
        }
    }

    @Parameters({"true,42", "false,0"})
    @Test
    public void turn_integer_into_string(final boolean present, final int value) {
        // given
        final Optional<Integer> optional = present ? Optional.of(value) : Optional.<Integer>empty();

        // when
        final Optional<String> optionalValue = optionalProblems.turnOptionalIntegerIntoOptionalString(optional);

        // then
        assertNotNull(optionalValue);
        final Optional<String> expected = present ? Optional.of(String.valueOf(value)) : Optional.empty();
        assertEquals(expected, optionalValue);
    }

    @Parameters({"true,42", "false,0"})
    @Test
    public void turn_integer_into_string_or_empty_string(final boolean present, final int value) {
        // given
        final Optional<Integer> optional = present ? Optional.of(value) : Optional.<Integer>empty();

        // when
        final String stringValue = optionalProblems.turnIntegerIntoStringOrEmptyString(optional);

        // then
        assertNotNull(stringValue);
        final String expected = present ? String.valueOf(value) : "";
        assertEquals(expected, stringValue);
    }

    @Parameters({"true,42,1", "false,,0"})
    @Test
    public void try_make_an_integer_out_of_an_optional_string(final boolean present, final String value, final int nrOfInvocations) {
        // given
        final Optional<String> optional = present ? Optional.of(value) : Optional.<String>empty();

        // when
        when(someService.tryMakeAnInteger(anyString())).thenAnswer(invocation -> Optional.of(Integer.valueOf((String) invocation.getArguments()[0])));
        final Optional<Integer> optionalValue = optionalProblems.tryMakeAnIntegerOutOfAnOptionalString(optional);

        // then
        assertNotNull(optionalValue);
        final Optional<Integer> expected = present ? Optional.of(Integer.valueOf(value)) : Optional.empty();
        assertEquals(expected, optionalValue);
        verify(someService, times(nrOfInvocations)).tryMakeAnInteger(anyString());
    }

    @Parameters({"false,value,true", "false,,false", "false,   ,false", "true,,false"})
    @Test
    public void keep_non_empty_nullable_string(final boolean nullValue, final String value, final boolean expectPresentResult) {
        // given
        final String input = nullValue ? null : value;

        // when
        final Optional<String> notBlank = optionalProblems.keepNonEmptyNullableString(input);

        // then
        assertEquals(expectPresentResult, notBlank.isPresent());
    }

    @Parameters({"true,value,1", "false,,0"})
    @Test
    public void execute_side_effect_when_value_is_present(final boolean present, final String value, final int nrOfInvocations) {
        // given
        final Optional<String> optional = present ? Optional.of(value) : Optional.<String>empty();

        // when
        doAnswer(invocation -> {
            System.out.println("Here is the value that was within the Optional : " + value);
            return null;
        }).when(someService).printOut(value);
        optionalProblems.executeSideEffectWhenValueIsPresent(optional);

        // then
        verify(someService, times(nrOfInvocations)).printOut(anyString());
    }

    @Test
    public void retain_all_present_integers_from_list_of_optional_integers() {
        List<Integer> integers = optionalProblems.retainAllIntegers(Arrays.asList(Optional.of(1), Optional.empty(), Optional.of(10)));
        assertThat(integers.get(0), is(1));
        assertThat(integers.get(1), is(10));
    }


    @Parameters({
        "false, false, false, false, false, 0.0",
        "true, false, false, false, false, 0.0",
        "true, true, false, false, false, 0.0",
        "true, true, true, false, false, 0.0",
        "true, true, true, true, false, 0.0",
        "true, true, true, true, true, 250000.0",
    })
    @Test
    public void getBalance(final boolean withId, final boolean withCustomer, final boolean withAccountNumber, final boolean withAccount, final boolean withBalance, final Double expected) {
        // given
        final Integer customerId;
        final String accountNumber = "NO255415684";
        final Double balance = 250000.0;

        // when
        if(withId) {
            customerId = 42;
            final OptionalProblems.Customer customer;
            if (withCustomer) {
                customer = mock(OptionalProblems.Customer.class);
                if (withAccountNumber) {
                    when(customer.getAccountNumber()).thenReturn(accountNumber);
                }
            } else {
                customer = null;
            }
            when(database.getCustomer(customerId)).thenReturn(customer);
        } else {
            customerId = null;
        }
        final OptionalProblems.Account account;
        if (withAccount) {
            account = mock(OptionalProblems.Account.class);
            if (withBalance) {
                when(account.getBalance()).thenReturn(balance);
            }
        } else {
            account = null;
        }
        when(accountService.getAccount(accountNumber)).thenReturn(account);

        final Double actual = optionalProblems.getBalance(customerId);

        // then
        assertEquals(expected, actual);
    }

    @Parameters
    @Test
    public void getFirstCustomerAccountNumberStartingWithNO(final List<String> numbers, final String expected) {
        // given
        final List<OptionalProblems.Customer> customers = numbers.stream()
                                                                 .map(number -> (OptionalProblems.Customer) () -> number)
                                                                 .collect(Collectors.toList());

        // when
        final String actual = optionalProblems.getFirstCustomerAccountNumberStartingWithNO(customers);

        // then
        assertEquals(expected, actual);
    }

    public Object[][] parametersForGetFirstCustomerAccountNumberStartingWithNO() {
        return new Object[][]{{Collections.emptyList(), ""},
                {Arrays.asList("FR123456", "NO123456", "NO098765"), "NO123456"},
                {Arrays.asList("FR123456", null, "NO123456", "NO098765"), "NO123456"}};
    }

}
