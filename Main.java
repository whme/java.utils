public class Main {
    public static void main(String[] args) {
        List<TestClass> testList = new List<>();
        for (int i=0; i<10; i++) {
            testList.add(new TestClass(String.format("Test-%d", i)));
        }
        System.out.println(testList.toString());
        System.out.println(testList.getLength());
        testList.remove(5);
        System.out.println(testList.toString());
        System.out.println(testList.getLength());
        System.out.println(new List<Integer>(1,2,3,4,5));
        for (TestClass element: testList) {
            System.out.println(element);
        }
        TestClass testClass = new TestClass("Test-1");
        System.out.println(testList.contains(testClass));
        System.out.println(testList.contains(testClass, testClass::haveSameString));
    }
}
