import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NeophyteProblems {

    /*
     * In a stream of ints, find all the prime numbers, and return the sum of them
     */
    public Integer sumAllPrimeNumbers(Stream<Integer> ints) {
        return 0;
    }

    /*
     * Helper method
     */
    public Boolean isPrime(int i) {
        return i < 4 || IntStream.range(2, ((i / 2) + 1)).noneMatch(x -> i % x == 0);
    }

    /*
     * With a single call to parallell() we can multi-thread the prime search.
     * Compare this method to the one below:
     */
    public Integer sumPrimesOfVeryLargeListSequentially(Stream<Integer> ints) {
        long before = System.currentTimeMillis();
        Integer result = sumAllPrimeNumbers(ints);
        long after = System.currentTimeMillis();
        System.out.println(String.format("Took %d ms to sum all those primes sequentially", after - before));
        return result;
    }

    public Integer sumPrimesOfVeryLargeListInParallell(Stream<Integer> ints) {
        long before = System.currentTimeMillis();
        Integer result = sumAllPrimeNumbers(ints.parallel());
        long after = System.currentTimeMillis();
        System.out.println(String.format("Took %d ms to sum all those primes in parallell", after - before));
        return result;
    }

    /*
     * join all strings (concatenate)
     */
    public String concatenateAll(Stream<String> stringStream) {
        return "";
    }

    /*
     * convert stream<Double> to Set<Double>
     */
    public Set<Double> collectDoubles(Stream<Double> doubles) {
        return Collections.emptySet();
    }

    /*
     * The input to this method is the stream of ordered natural numbers (1,2,3,4,...)
     * They should be transformed to a map where each number points to it's square :
     * {1,2,3} should be transformed to the map {1 -> 1, 2 -> 4, 3 -> 9}
     */
    public Map<Integer, Integer> createMapFromIntegerToItsSquare(Stream<Integer> ints) {
        return Collections.emptyMap();
    }

    /*
     * Create an IntStream of only the number 1, with a limit of 10 (length 10)
     */
    public IntStream createAnIntStreamOfLengh10() {
        return IntStream.empty();
    }

    /*
     * Create a map {1 -> 2, 2 -> 3, .., 1000 -> 1001}
     * You can use IntStream.iterate, limit and the collect(toMap())
     * The collect call forces evaluation of the stream, so it can't be of infinite size
     *
     * Hint : the boxed()-method creates a Stream<Integer> from an IntStream (handy if you want to use the Collectors.toMap() utility)
     */
    public Map<Integer, Integer> createMapOfNaturalNumbersToItsSuccessor() {
        return Collections.emptyMap();
    }

    /*
     * Use the reduce method to find the sum of a list
     */
    public Integer sumListByReduction(Stream<Integer> integerStream) {
        return 0;
    }

    /**
     * Use the new compute() method in java.util.Map
     * We would like to change the value with key 1
     * The new value should be the String concatenated with itself
     */
    public String doubleStringThatkeyOnePointsTo(Map<Integer, String> map) {
        return "";
    }

    /*
     * Create a function that takes an iterable, and a function. It should return an iterable, where all the elements have been
     * run through the function. Such an operation is often called map.
     */
    public <T, S> Iterable<S> map(Iterable<T> list, Function<T, S> function) {
        ArrayList<S> retVal = new ArrayList<>();
        return retVal;
    }

}
