import com.github.nasva.ArrayList;
import com.github.nasva.QuickSort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

public class QuickSortTest {
    public static Stream<Arguments> testSortSource() {
        return Stream.of(
                Arguments.arguments(
                        ArrayList.of(1, -4, 2, 7, 12, 3, 4, -20, 17),
                        ArrayList.of(-20, -4, 1, 2, 3, 4, 7, 12, 17)
                ),
                Arguments.arguments(
                        ArrayList.of(1),
                        ArrayList.of(1)
                ),
                Arguments.arguments(
                        ArrayList.of(),
                        ArrayList.of()
                ),
                Arguments.arguments(
                        ArrayList.of(1, 1, 1, 1),
                        ArrayList.of(1, 1, 1, 1)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("testSortSource")
    void testSort(ArrayList<Integer> list, ArrayList<Integer> expected) {
        QuickSort.sort(list);
        Assertions.assertArrayEquals(expected.toArray(), list.toArray());
    }

    @ParameterizedTest
    @MethodSource("testSortSource")
    void testSortWithCustomIterator(ArrayList<Integer> list, ArrayList<Integer> expected) {
        QuickSort.sort(list, Comparator.reverseOrder());
        Collections.reverse(expected);
        Assertions.assertArrayEquals(expected.toArray(), list.toArray());
    }
}
