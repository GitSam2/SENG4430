package ccTest.singleton;

class Singleton {

    private static Singleton single_instance = null;

    public String s;

    private Singleton()
    {
        s = "This is a string part of Singleton class";
    }
    // here a private constructor is used

    // Method
    public static Singleton Singleton()
    {
        if (single_instance == null) {
            single_instance = new Singleton();
        }
        return single_instance;
    }
}