import java.io.File;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class IntroProblems {

    /*
     * Should create and return a stream from a List
     */
    public Stream<Integer> createStream(List<Integer> ints) {
        return ints.stream();
    }

    /*
     * Should create and return a stream from an array
     */
    public Stream<Integer> createStream(Integer[] ints) {
        return Stream.of(ints);
    }

    /*
     * Should apply any passed stringify-function to an object of type T, and return the resulting String
     * This is a higher order function (because this function accepts a function as a parameter)
     */
    public <T> String apply(T theThing, Function<T, String> theFunction) {
        return theFunction.apply(theThing);
    }

    /*
     * Should return something that is a Function from Integer to Integer,
     * and that function should double an Integer
     * Hint: a lambda can be used
     */
    public Function<Integer, Integer> doublingFunction() {
        return i -> i * 2;
    }

    /*
     * Should return something that is a Function from Integer To Integer,
     * and that function should triple an Integer
     * Hint: method reference can be used - point to the triplingFunction below
     */
    public Function<Integer, Integer> triplingFunctionSupplier() {
        return this::triplingFunction;
    }

    /*
     * The awesomeness of this method warrants reuse through a method reference..
     */
    private Integer triplingFunction(Integer i) {
        return i * 3;
    }

    /*
     * Should double all the ints. Create a stream from the list, and then use map, and then collect it back in a list
     */
    public List<Integer> doubleAllTheInts(List<Integer> ints) {
        return ints.stream().map(doublingFunction()).collect(toList());
    }

    /*
    * Should create a Stream<File> from Stream<String> where the String can be assumed to be a valid path
    * (Hint java.io.File(String path) creates a File from a path)
    */
    public Stream<File> fileFromPath(Stream<String> paths) {
        return paths.map(File::new);
    }

    /*
     * Should make a stream from the list of strings and do a map on the stream,
     * before collecting the result back in to a list
     */
    public List<String> upperCasifyAllStrings(List<String> strings) {
        return strings.stream().map(String::toUpperCase).collect(toList());
    }

    /*
     * Make the third charachter in any String long enough upper-cased.
     * You can use the helperFunction()-method if you like
     */
    public List<String> uppercasifyThirdCharacterOnAllStrings(List<String> strings) {
        return strings.stream().map(this::helperFunction).collect(toList());
    }

    public String helperFunction(String s) {
        char[] chars = s.toCharArray();
        if (chars.length >= 3) {
            chars[2] = Character.toUpperCase(chars[2]);
        }
        return String.valueOf(chars);
    }

    /*
     * Return a list where all strings longer than 5 characters are removed
     */
    public List<String> removeLongStrings(List<String> strings) {
        return strings.stream().filter(s -> s.length() < 6).collect(toList());
    }

    /*
     * Return a sublist from a list where all elements satisfy a provided Predicate
     */
    public List<Double> customExcludeFromList(List<Double> nums, Predicate<Double> predicate) {
        return nums.stream().filter(predicate).collect(toList());
    }

    /*
     * Return a sublist of something generic where the predicate is FALSE
     * (that is, the negated predicate is TRUE)
     */
    public <T> List<T> keepAllWhereNot(List<T> theThings, Predicate<T> thePredicate) {
        return theThings.stream().filter(thePredicate.negate()).collect(toList());
    }

    /*
     * There are several types of Stream (IntStream, DoubleStream, LongStream)
     * Convert an IntStream to a Stream<Integer>
     */
    public Stream<Integer> convert(IntStream stream) {
        return stream.boxed();
    }

    /*
     * Convert a Stream<Integer> to IntStream, containing values twice as high as the original int in the steram
     */
    public IntStream convertAndDoubleValue(Stream<Integer> integerStream) {
        return integerStream.mapToInt(i -> i * 2);
    }

}
