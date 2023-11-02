package com.github.nasva;

import java.util.Comparator;

/**
 * Класс представляет реализацию сортировки QuickSort. Содержит два публичных метода, которые используется для
 * сортировки переданного листа.
 */
public class QuickSort {
    /**
     * @param array лист, который необходимо отсортировать
     * @param <T> тип элементов массива
     * Метод сортирует массив, используя компаратор переданного типа
     */
    public static <T> void sort(ArrayList<T> array) {
        recourseSort(array, 0, array.size() - 1, (Comparator<T>) Comparator.naturalOrder());
    }

    /**
     * @param array лист, который необходимо отсортировать
     * @param comparator компаратор, который используется для сравнения элементов
     * @param <T> тип элементов массива
     */
    public static <T> void sort(ArrayList<T> array, Comparator<T> comparator) {
        recourseSort(array, 0, array.size() - 1, comparator);
    }

    private static <T> void recourseSort(ArrayList<T> array, int left, int right, Comparator<T> comparator) {
        if (left >= right) {
            return;
        }
        var pivot = array.get((left + right) / 2);
        System.out.println(left + " " + right + " " + pivot);
        int leftPointer = left - 1;
        int rightPointer = right + 1;
        while (leftPointer < rightPointer) {
            do {
                leftPointer++;
            } while (comparator.compare(pivot, array.get(leftPointer)) > 0);
            do {
                rightPointer--;
            } while (comparator.compare(pivot, array.get(rightPointer)) < 0);
            if (leftPointer < rightPointer) {
                T temp = array.get(rightPointer);
                array.set(rightPointer, array.get(leftPointer));
                array.set(leftPointer, temp);
            }
        }
        recourseSort(array, left, rightPointer, comparator);
        recourseSort(array, rightPointer + 1, right, comparator);
    }

}
