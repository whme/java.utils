public class TestClass {

    public static final float VERY_BIG_NUMBER = 54321.09876f;

    private String string;

    public TestClass(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public boolean haveSameString(TestClass testClass) {
        return this.string.equals(testClass.getString());
    }

    @Override
    public String toString() {
        return String.format("%s %f", this.string, VERY_BIG_NUMBER);
    }
    
}
