import no.finntech.lambdacompanion.Either;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class EitherProblemsTest {

    private final EitherProblems.Database database = mock(EitherProblems.Database.class);

    private final EitherProblems.AccountService accountService = mock(EitherProblems.AccountService.class);

    EitherProblems eitherProblems = new EitherProblems(database, accountService);

    @Test
    public void should_return_either_a_string_or_integer() {
        Either<String, Integer> wtf = eitherProblems.wtf();
        assertNotNull(wtf);
    }

    @Test
    public void should_return_either_happy_string_or_failure_string() {
        assertThat(
                eitherProblems.extractValueFrom(Either.left(new Exception())), is(EitherProblems.defaultValue())
        );
        assertThat(
                eitherProblems.extractValueFrom(Either.right("yo")), is("yo")
        );
    }

    @Test
    public void should_return_either_doubled_or_failure_code() {
        assertThat(
                eitherProblems.multiplyByTwo(Either.right(3)), is(6)
        );
        assertThat(
                eitherProblems.multiplyByTwo(Either.left(new Exception())), is(-1)
        );
    }

    @Parameters({"true,",
                 "false,NO255415684"})
    @Test
    public void should_return_account_number(final boolean withFailure, final String expected) {
        // given
        final Integer customerId = 42;
        final EitherProblems.Customer customer = () -> "NO255415684";
        final Either<IllegalStateException, EitherProblems.Customer> success = Either.right(customer);
        final Either<IllegalStateException, EitherProblems.Customer> failure = Either.left(new IllegalStateException("Not found"));

        // when
        if(withFailure) {
            when(database.getCustomer(customerId)).thenReturn(failure);
        } else {
            when(database.getCustomer(customerId)).thenReturn(success);
        }
        final String accountNumber = eitherProblems.getAccountNumber(customerId);

        // then
        assertEquals(expected, accountNumber);
    }

    @Parameters({"true,true,0.0",
                 "false,true,0.0",
                 "false,false,250000.0"})
    @Test
    public void should_return_balance(final boolean withDatabaseFailure, final boolean withServiceFailure, final Double expected) {
        // given
        final Integer customerId = 42;
        final String accountNumber = "NO255415684";
        final EitherProblems.Customer customer = () -> accountNumber;
        final Either<IllegalStateException, EitherProblems.Customer> databaseSuccess = Either.right(customer);
        final Either<IllegalStateException, EitherProblems.Customer> databaseFailure = Either.left(new IllegalStateException("Not found"));
        final EitherProblems.Account account = () -> 250000.0;
        final Either<IllegalStateException, EitherProblems.Account> accountSuccess = Either.right(account);
        final Either<IllegalStateException, EitherProblems.Account> accountFailure = Either.left(new IllegalStateException("Not found"));

        // when
        if(withDatabaseFailure) {
            when(database.getCustomer(customerId)).thenReturn(databaseFailure);
        } else {
            when(database.getCustomer(customerId)).thenReturn(databaseSuccess);
        }
        if(withServiceFailure) {
            when(accountService.getAccount(accountNumber)).thenReturn(accountFailure);
        } else {
            when(accountService.getAccount(accountNumber)).thenReturn(accountSuccess);
        }
        final Double actual = eitherProblems.getBalance(customerId);

        // then
        assertEquals(expected, actual);
    }

}
