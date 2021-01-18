import utils.util.*;

public class Main {
    public static void main(String[] args) {
        testList();
        testDict();
        List<Integer> first = new List<>(1,2,3,4,5);
        List<Integer> second = first.clone();
        System.out.println(first);
        System.out.println(second);
        for (Integer element: first) {
            element *= 10;
        }
        System.out.println(first);
        System.out.println(second);

        List<Integer> list = new List<>(1,2,3,4,5);
        System.out.println(list);
        for(int i=0;i<5;i++) {
            for(Integer currentInt=list.initForLoop();list.hasNext();currentInt=list.next()) {
                System.out.println(currentInt);
            }
        }
    }

    public static void testDict() {
        Dict<String, Integer> testDict = new Dict<>("Test", 55);
        System.out.println(testDict);
        System.out.println(testDict.hasKey("Test"));
        for (int i = 0; i < 10; i++) {
            testDict.set(String.format("Test-%d", i), i);
        }
        System.out.println(testDict);
        testDict.remove("Test");
        System.out.println(testDict);
        System.out.println(testDict.values());
        for (DictItem<String, Integer> item : testDict.dictItems()) {
            item.value *= 10;
        }
        System.out.println(testDict);

    }

    public static void testList() {
        List<TestClass> testList = new List<>();
        for (int i = 0; i < 10; i++) {
            testList.add(new TestClass(String.format("Test-%d", i)));
        }
        System.out.println(testList.toString());
        System.out.println(testList.getLength());
        testList.remove(5);
        System.out.println(testList.toString());
        System.out.println(testList.getLength());
        System.out.println(new List<Integer>(1, 2, 3, 4, 5));
        for (TestClass element : testList) {
            System.out.println(element);
        }
        TestClass testClass = new TestClass("Test-1");
        System.out.println(testList.contains(testClass));
        System.out.println(testList.contains(testClass::haveSameString));
    }
}
