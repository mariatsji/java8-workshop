import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class IntroProblemsTest {

    private IntroProblems introProblems = new IntroProblems();

    @Test
    public void should_create_stream_from_list_of_int() {
        List<Integer> ints = Arrays.asList(1, 2, 3);
        Stream<Integer> stream = introProblems.createStream(ints);

        List<Integer> collected = stream.collect(toList());
        assertThat(collected, is(ints));
    }

    @Test
    public void should_create_stream_from_array_of_int() {
        Integer[] ints = {1, 2, 3};
        Stream<Integer> stream = introProblems.createStream(ints);

        List<Integer> collected = stream.collect(toList());
        assertThat(collected, is(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void should_return_string_from_stringify_function_on_object_of_type_t() {
        String s = introProblems.apply(1, Object::toString);
        assertThat(s, is("1"));
    }

    @Test
    public void should_return_a_doubling_function() {
        Function<Integer, Integer> doublingFunction = introProblems.doublingFunction();
        Integer result = doublingFunction.apply(13);
        assertThat(result, is(26));
    }

    @Test
    public void should_return_a_tripling_function() {
        Function<Integer, Integer> triplingFunction = introProblems.triplingFunctionSupplier();
        Integer result = triplingFunction.apply(7);
        assertThat(result, is(21));
    }

    @Test
    public void should_double_all_ints() {
        List<Integer> integers = introProblems.doubleAllTheInts(Arrays.asList(1, 3, 5));
        assertThat(integers, is(Arrays.asList(2, 6, 10)));
    }

    @Test
    public void should_create_stream_of_file_from_stream_of_path() {
        Stream<File> fileStream = introProblems.fileFromPath(Arrays.asList(File.separator + "asdf.txt", File.separator + "home" + File.separator + "crocodile").stream());
        List < File > files = fileStream.collect(toList());
        assertThat(files.get(1).getPath(), is(File.separator +"home" + File.separator +"crocodile"));

    }

    @Test
    public void should_upper_casify_all_strings() {
        List<String> strings = Arrays.asList("awesome", "list", "of", "strings");
        List<String> result = introProblems.upperCasifyAllStrings(strings);
        assertThat(result, is(Arrays.asList("AWESOME", "LIST", "OF", "STRINGS")));
    }

    @Test
    public void should_upper_casify_third_char_in_string_if_long_enough() {
        List<String> strings = Arrays.asList("cat", "oh", "turtles", "");
        List<String> result = introProblems.uppercasifyThirdCharacterOnAllStrings(strings);
        assertThat(result, is(Arrays.asList("caT", "oh", "tuRtles", "")));
    }

    @Test
    public void should_filter_out_strings_that_are_too_long() {
        List<String> strings = Arrays.asList("this", "is", "waytolong", "fine");
        List<String> result = introProblems.removeLongStrings(strings);
        assertThat(result, is(Arrays.asList("this", "is", "fine")));
    }

    @Test
    public void should_filter_out_doubles_according_to_custom_predicate() {
        List<Double> doubles = Arrays.asList(1d, 2d, 3d, 4d);
        List<Double> result = introProblems.customExcludeFromList(doubles, d -> d > 2);
        assertThat(result, is(Arrays.asList(3d, 4d)));
    }

    @Test
    public void should_filter_out_doubles_that_are_even() {
        List<Double> doubles = Arrays.asList(1d, 2d, 3d, 4d, 5d, 17d, 30d);
        List<Double> result = introProblems.customExcludeFromList(doubles, d -> d % 2 != 0);
        assertThat(result, is(Arrays.asList(1d, 3d, 5d, 17d)));
    }

    @Test
    public void should_keep_doubles_that_are_odd_when_predicate_sais_even() {
        List<Double> doubles = Arrays.asList(1d, 2d, 3d, 4d, 5d, 17d, 30d);
        List<Double> result = introProblems.keepAllWhereNot(doubles, d -> d % 2 == 0);
        assertThat(result, is(Arrays.asList(1d, 3d, 5d, 17d)));
    }

    @Test
    public void should_convert_instream_to_stream() {
        IntStream stream = IntStream.of(1, 2, 3, 4);
        Stream<Integer> result = introProblems.convert(stream);
        List<Integer> collected = result.collect(toList());
        assertThat(collected, is(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    public void should_convert_stream_to_intstream() {
        Stream<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6).stream();
        IntStream stream = introProblems.convertAndDoubleValue(ints);
        assertThat(stream.sum(), is(42));
    }

}
