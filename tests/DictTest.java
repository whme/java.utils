package tests;

import java.lang.reflect.Method;

import utils.util.Dict;
import utils.util.DictItem;
import utils.util.List;

public class DictTest extends Tester {

    public DictTest() {
        super();
        Tester.tests.add(
            new DictItem<Object,List<Method>>(
                this,
                new List<Method>(this.getMethods())
            )
        );
    }

    public void Dict_NoArgs_test() {
        Dict<String, Integer> dict = new Dict<>();
        List<Method> methods = new List<>(dict.getClass().getMethods());
        System.out.print(methods);
    }
}
