// package tests;

// import utils.util.DictItem;
// import utils.util.List;

// import tests.*;

// import java.lang.Class;
// import java.lang.reflect.InvocationTargetException;
// import java.lang.reflect.Method;

// public class Tester {

//     protected static List<DictItem<Object, List<Method>>> tests = new List<>();
//     private static List<Object> registeredTests = new List<>();

//     public Tester() {
//         ;
//     }

//     public Method[] getMethods() {
//         Method[] methods = this.getClass().getMethods();
//         Method[] result = new Method[methods.length];
//         int actualSize = 0;
//         // for (int i=0; i<methods.length;i++) {
//         //     if (methods[i].getClass() != this.getClass())
//         // }
//         return this.getClass().getMethods();
//     }

//     public static void register(Class clazz) {
//         try {
//             Tester.registeredTests.add(clazz.getDeclaredConstructor().newInstance());
//         } catch (InstantiationException | InvocationTargetException | IllegalAccessException | IllegalArgumentException
//                 | NoSuchMethodException | SecurityException e) {
//             e.printStackTrace();
//         }
//     }

//     public void runTests() {
//         for (DictItem<Object, List<Method>> testPair : Tester.tests) {
//             for (Method test: testPair.value) {
//                 try {
//                     test.invoke(testPair.key, null);
//                 } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//                     e.printStackTrace();
//                 }
//             }
//         }
//     }

//     public static void main(String[] args) {
//         Tester tester = new Tester();
//         tester.register(DictTest.class);
//         tester.runTests();
//     }
    
// }
