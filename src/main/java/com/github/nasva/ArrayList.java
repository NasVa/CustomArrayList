package com.github.nasva;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Класс представляет реализацию ArrayList через имплементацию интерфейса List. Предназначен для работы с динамическим
 * массивом. Содержит методы доступа, добавления, удаления и изменения элементов массива. Внутри клсса содержится
 * массив, стандартный размер которого равен 10. Также есть возможность создать массив заданного размера. Увеличение
 * и уменьшение размера массива происходит автоматически при изменнии количества элементов элементов.
 */
public class ArrayList<T> implements List<T> {
    /**
     * Массив, содержащий элементы
     */
    private Object[] array;
    /**
     * Переменная, отвечающая за фактический размер массива (количество элементов)
     */
    private int size;

    /**
     * Конструктор без параметров, создающий массив стандартного размера
     */
    public ArrayList() {
        this(10);
    }

    /**
     * @param size - размер создаваемого массива
     *             Конструктор, создающий массив заданного размера
     */
    public ArrayList(int size) {
        array = new Object[size];
    }

    /**
     * @return фактический размер массива (количество элементов)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @return true, если массив не содержит элементов, в противном случае - false
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * @param o элемент, который ищется в массиве
     * @return true, если массив содержит запрашиваемый элемент, false - если нет
     */
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return итератор листа, который содержит метод проверки наличия следующего элемента и метод для получения
     * следующего элемента
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            /**
             * @return true, если перебор массива не завершен, false - в противном случае
             */
            @Override
            public boolean hasNext() {
                return index < array.length;
            }

            /**
             * @return следующий элемент, если перебор массива не завершен, null - в противном случае
             */
            @Override
            public T next() {
                return hasNext() ? (T) array[index++] : null;
            }
        };
    }

    /**
     * @return массив объектов класса Object, содержащих элементы листа
     * Если массив не полностью заполнен (размер массива не равен фактическому размеру), создается новый массив, размер
     * которого равен фактическому, в который и копируются все элементы.
     */
    @Override
    public Object[] toArray() {
        if (size == array.length) {
            return array;
        }
        Object[] newArr = new Object[size];
        System.arraycopy(array, 0, newArr, 0, size);
        return newArr;
    }

    /**
     * @param a    массив, в который элементы листа сохраняются. Если его размер меньше фактичекого размера листа,
     *             создается новый массив того же типа, который был передан
     * @param <T1> тип массива, к которому приводятся элементы
     * @return массив элементов типа, который был передан
     */
    @Override
    public <T1> T1[] toArray(T1[] a) {
        T1[] newArr = a;
        if (a.length < size) {
            var newType = a.getClass();
            newArr = (T1[]) Array.newInstance(newType.getComponentType(), size);
        }
        for (int i = 0; i < size; i++) {
            newArr[i] = (T1) array[i];
        }
        return (T1[]) newArr;
    }

    /**
     * @param t элемент, который добавляется в лист
     * @return true, если элемент успешно добавлен
     */
    @Override
    public boolean add(T t) {
        tryIncreaseCapacity(1);
        array[size++] = t;
        return true;
    }

    /**
     * @param o eэлемент, который удаляется из листа
     * @return true, если элемент успешно удален
     * Метод удаляет только первое вхождение элемента в лист
     */
    @Override
    public boolean remove(Object o) {
        int removed = 0;
        for (int i = 0; i < array.length; i++) {
            if (o.equals(array[i]) && removed < 1) {
                removed++;
                size--;
            } else {
                array[i - removed] = array[i];
            }
        }
        tryDecreaseCapacity(removed);
        return true;
    }

    /**
     * @param c cколлекция, наличие элементов которой должно быть отслежено
     * @return true, если каждый из элементов переданной коллекции содержится в листе, false - если хотя бы один элемент
     * из переданной коллекции не содержится в листе
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (int i = 0; i < c.size(); i++) {
            if (!contains(c.toArray()[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param c коллекция, все элементы которой должны быть добавлены в конц листа
     * @return true при успешном добавлении элементов
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        addAll(size, c);
        return true;
    }

    /**
     * @param index индекс элемента, начиная с которого должны быть вставлены элементы
     * @param c     коллекция, содержащая элементы, которые должны быть вставлены в лист начиная с заданного индекса и
     *              в том порядке, в котором они содержатся в коллекции
     * @return true, если вставка произошла успешно
     * Метод автоматически расширяет массив, если места для новых элементов не хватает
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        tryIncreaseCapacity(c.size());
        Iterator<T> iterator = (Iterator<T>) c.iterator();
        for (int i = index + size - (index + 1); i > index - 1; i--) {
            array[size - 1 + c.size() - i] = array[size - 1 - i];
        }
        for (int i = index; i < index + c.size(); i++) {
            array[i] = iterator.next();
            size++;
        }
        return true;
    }

    /**
     * @param c коллекция, содержащая элементы, все вхождения которых должны быть удалены из листа
     * @return true, если удаление произошло успешно
     * Метод автоматически уменьшает массив, если это необходимо
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        int removed = 0;
        for (int i = 0; i < array.length; i++) {
            if (c.contains(array[i])) {
                removed++;
                size--;
            } else {
                array[i - removed] = array[i];
            }
        }
        tryDecreaseCapacity(removed);
        return true;
    }

    /**
     * @param c коллекция, содержащая элементы, все вхождения которых должны быть сохранены в коллекции
     * @return true при успешном сохранении элементов
     * Метод удаляет все элементы, не входящие в переданную коллекцию и уменьшает массив, если это необходимо
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        int removed = 0;
        for (int i = 0; i < array.length; i++) {
            if (!c.contains(array[i])) {
                removed++;
                size--;
            } else {
                array[i - removed] = array[i];
            }
        }
        tryDecreaseCapacity(removed);
        return true;
    }

    /**
     * Очищает лист, удаляя все элементы и делая размер массива равным 0
     */
    @Override
    public void clear() {
        array = new Object[0];
        size = 0;
    }

    /**
     * @param index индекс элемента, который должен быть возвращен
     * @return элемент листа по заданному индексу, если элемент не найден - null
     */
    @Override
    public T get(int index) {
        return index < size ? (T) array[index] : null;
    }

    /**
     * @param index   индекс элемента, который должен быть заменен
     * @param element новое значение заменяемого элемента
     * @return старое значение замененного элемента, если элемент не найден - bull
     */
    @Override
    public T set(int index, T element) {
        for (int i = 0; i < array.length; i++) {
            if (i == index) {
                Object previously = array[i];
                array[i] = element;
                return (T) previously;
            }
        }
        return null;
    }

    /**
     * @param index   индекс элемента, на место которого должен быть вставлен новый элемент
     * @param element элемент, который должен быть вставлен
     * Метод вставляет (не заменяет другой) новый элемент на заданную позицию. Предусмотрено увеличие массива при
     *                необходимости
     */
    @Override
    public void add(int index, T element) {
        tryIncreaseCapacity(1);
        for (int i = index + size - (index + 1); i > index - 1; i--) {
            array[size - i] = array[size - 1 - i];
        }
        for (int i = index; i < index + 1; i++) {
            array[i] = element;
            size++;
        }
    }

    /**
     * @param index индекс удаляемого элемента
     * @return значение удаленного элемента или null, если элемент не был найден
     * Метод уменьшает размер массива при необходимости
     */
    @Override
    public T remove(int index) {
        if (index > size){
            return null;
        }
        Object removed = array[index];
        for (int i = index; i < array.length - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        tryDecreaseCapacity(1);
        return (T) removed;
    }

    /**
     * @param o элемент, индекс которого необходимо найти
     * @return индекс переданного элемента, -1 в противном случае
     */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < array.length; i++) {
            if (o == array[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param o элемент, индекс последнего вхождения которого необходимо найти
     * @return индекс последнего вхождения переданного элемента, -1 в противном случае
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (o == array[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    /**
     * @param fromIndex левая граница подсписка
     * @param toIndex   правая граница подсписка
     * @return подсписок листа
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        List<T> subList = new ArrayList<T>();
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add((T) array[i]);
        }
        return subList;
    }

    private void tryIncreaseCapacity(int n) {
        if (size + n > array.length) {
            Object[] newArr = new Object[Math.max(size + n, 10)];
            System.arraycopy(array, 0, newArr, 0, array.length);
            array = newArr;
        }
    }

    private void tryDecreaseCapacity(int n) {
        if (size - n < array.length / 2) {
            Object[] newArr = new Object[array.length % 2 == 0 ? array.length / 2 : array.length / 2 + 1];
            if (size >= 0) System.arraycopy(array, 0, newArr, 0, size);
            array = newArr;
        }
    }

    /**
     * @param array массив элементов создаваемого листа
     * @param <T> тип элементов листа
     * @return лист элементов переданного массива
     */
    @SafeVarargs
    public static <T> ArrayList<T> of(T... array) {
        ArrayList<T> ts = new ArrayList<>(array.length);
        Collections.addAll(ts, array);
        return ts;
    }

}