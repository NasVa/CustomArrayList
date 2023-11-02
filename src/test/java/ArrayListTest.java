import com.github.nasva.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class ArrayListTest {
    @Test
    void createArrayList() {
        var list = new ArrayList<>(30);
        Assertions.assertEquals(0, list.size());
    }

    @Test
    void testNull() {
        var list = new ArrayList<Integer>();
        Assertions.assertFalse(list.contains(null));
    }

    @Test
    void testIsEmpty() {
        var list = new ArrayList<Integer>();
        list.add(4);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void testContains() {
        var list = new ArrayList<Integer>();
        list.add(4);
        list.add(3);
        Assertions.assertTrue(list.contains(3));
    }

    @Test
    void testIterator() {
        var list = new ArrayList<Integer>();
        list.add(4);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        Assertions.assertEquals(4, iterator.next());
    }

    @Test
    void testToArray() {
        var list = new ArrayList<Integer>();
        for (int i = 1; i < 4; i++) {
            list.add(i);
        }
        var arr = list.toArray();
        Assertions.assertArrayEquals(new Object[]{1, 2, 3}, arr);
    }

    @Test
    void testToArrayGeneric() {
        var list = new ArrayList<Integer>();
        for (int i = 1; i < 4; i++) {
            list.add(i);
        }
        var temp = list.toArray(new Integer[0]);
        Assertions.assertArrayEquals(new Integer[]{1, 2, 3}, temp);
    }

    @Test
    void testAdd() {
        var list = new ArrayList<Integer>();
        for (int i = 1; i < 4; i++) {
            list.add(i);
        }
        Assertions.assertEquals(list.get(1), 2);
    }

    @Test
    void testRemove() {
        var list = new ArrayList<Integer>();
        for (int i = 1; i < 5; i++) {
            list.add(i);
        }
        list.remove((Object) 3);
        Assertions.assertEquals(list.get(2), 4);
    }

    @Test
    void testContainsAll() {
        var list = new ArrayList<Integer>();
        for (int i = 1; i < 5; i++) {
            list.add(i);
        }
        var c = new ArrayList<Integer>();
        list.add(1);
        list.add(4);
        Assertions.assertTrue(list.containsAll(c));
    }

    @Test
    void testAddAll() {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }
        var c = new ArrayList<Integer>();
        for (int i = 0; i < 211; i++) {
            list.add(i);
        }
        list.addAll(c);
        Assertions.assertEquals(list.size(), list.size() + c.size());
    }

    @Test
    void testRemoveAll() {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }
        list.add(0);
        list.add(0);
        var c = new java.util.ArrayList<Integer>();
        c.add(0);
        list.removeAll(c);
        Assertions.assertEquals(list.size(), 10);
    }

    @Test
    void testRetainAll() {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }
        list.add(0);
        list.add(0);
        var c = new java.util.ArrayList<Integer>();
        c.add(0);
        list.retainAll(c);
        Assertions.assertEquals(list.size(), 3);
    }

    @Test
    void testClear() {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }
        list.clear();
        Assertions.assertEquals(list.size(), 0);
    }

    @Test
    void testGet() {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 1100; i++) {
            list.add(i);
        }
        Assertions.assertEquals(list.get(1099), 1099);
    }

    @Test
    void testSet() {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 1100; i++) {
            list.add(i);
        }
        list.set(1000, -100);
        Assertions.assertEquals(list.get(1000), -100);
    }

    @Test
    void testAddByIndex() {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 1100; i++) {
            list.add(i);
        }
        list.add(102, -88);
        Assertions.assertEquals(list.get(102), -88);
    }

    @Test
    void testRemoveByIndex() {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 1100; i++) {
            list.add(i);
        }
        list.remove(102);
        Assertions.assertEquals(list.get(102), 103);
    }

    @Test
    void testIndexOf() {
        var list = new ArrayList<Integer>();
        for (int i = 1; i < 1100; i++) {
            list.add(i);
        }
        list.add(102);
        Assertions.assertEquals(list.indexOf(102), 101);
    }

    @Test
    void testLastIndexOf() {
        var list = new ArrayList<Integer>();
        for (int i = 1; i < 1100; i++) {
            list.add(i);
        }
        list.add(102);
        Assertions.assertEquals(list.lastIndexOf(102), 1099);
    }

    @Test
    void testListIterator() {
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 1100; i++) {
            list.add(i);
        }
        var sublist = (ArrayList<Integer>)list.subList(100, 1000);
        var list2 = new ArrayList<Integer>();
        for (int i = 100; i < 1000; i++) {
            list2.add(i);
        }
        Assertions.assertArrayEquals(sublist.toArray(), list2.toArray());
    }

    @Test
    void addElementByIndex(){
        var list = new ArrayList<Integer>();
        for (int i = 0; i < 1100; i++) {
            list.add(i, 0);
        }
        Assertions.assertEquals(list.size(), 1100);
    }
}
