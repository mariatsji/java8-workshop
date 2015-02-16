import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NeophyteProblemsTest {

    NeophyteProblems neophyteProblems = new NeophyteProblems();

    @Test
    public void should_sum_primes_in_a_list() {
        IntStream range = IntStream.range(1, 10);
        Integer result = neophyteProblems.sumAllPrimeNumbers(range.mapToObj(i -> i));
        assertThat(result, is(18));
    }

    @Ignore
    @Test
    public void should_illustrate_sequentially_prime_search(){
        int from = 100000;
        int to =   150000;
        Integer result = neophyteProblems.sumPrimesOfVeryLargeListSequentially(IntStream.range(from, to).mapToObj(i -> i));
        assertThat(result, is(531620910));
    }

    @Ignore
    @Test
    public void should_illustrate_parallell_prime_search(){
        int from = 100000;
        int to =   150000;
        Integer result = neophyteProblems.sumPrimesOfVeryLargeListInParallell(IntStream.range(from, to).mapToObj(i -> i));
        assertThat(result, is(531620910));
    }

    @Test
    public void should_join_strings() {
        List<String> strings = Arrays.asList("lucid", "streams", "are", "made", "of", "these");
        String joined = neophyteProblems.concatenateAll(strings.stream());
        assertThat(joined, is("lucidstreamsaremadeofthese"));
    }

    @Test
    public void should_collect_doubles_to_a_set() {
        Stream<Double> doubles = Arrays.asList(1d, 2d, 3d, 1d, 4d).stream();
        Set<Double> set = neophyteProblems.collectDoubles(doubles);
        assertTrue(set.containsAll(Arrays.asList(1d, 2d, 3d, 4d)));
    }

    @Test
    public void should_create_map_of_ints_to_square_of_int() {
        List<Integer> integers = Arrays.asList(1, 2, 3);
        Map<Integer, Integer> mapFromIntegerToItsSquare = neophyteProblems.createMapFromIntegerToItsSquare(integers.stream());
        assertThat(mapFromIntegerToItsSquare.get(1), is(1));
        assertThat(mapFromIntegerToItsSquare.get(2), is(4));
        assertThat(mapFromIntegerToItsSquare.get(3), is(9));
    }

    @Test
    public void should_generate_intstream_of_size_ten() {
        List<Integer> collected = neophyteProblems.createAnIntStreamOfLengh10().boxed().collect(toList());
        assertTrue(collected.stream().allMatch(i -> i == 1));
        assertThat(collected.size(), is(10));
    }

    @Test
    public void should_create_map_of_thousand_ints_to_its_successor_int() {
        Map<Integer, Integer> infiniteMap = neophyteProblems.createMapOfNaturalNumbersToItsSuccessor();
        assertThat(infiniteMap.get(999), is(1000));
    }

    @Test
    public void should_sum_list_of_ints_by_reduction() {
        Integer sum = neophyteProblems.sumListByReduction(IntStream.range(0, 10).boxed());
        assertThat(sum, is(45));
    }

    @Test
    public void should_compute_concatenated_string_for_key_1_in_a_map() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "asdf");
        map.put(2, "fdsa");
        String newString = neophyteProblems.doubleStringThatkeyOnePointsTo(map);
        assertThat(newString, is("asdfasdf"));
        assertThat(map.get(1), is("asdfasdf"));
        assertThat(map.get(2), is("fdsa"));
    }

    @Test
    public void should_map_an_iterable_to_its_corresponding_result_of_supplied_function() {
        List<Integer> input = Arrays.asList(1, 2, 3);
        Function<Integer, Integer> function = i -> i * 2;
        Iterator<Integer> output = neophyteProblems.map(input, function).iterator();
        int i = 0;
        while (output.hasNext()) {
            assertThat(output.next(), is(function.apply(input.get(i++))));
        }
    }


}