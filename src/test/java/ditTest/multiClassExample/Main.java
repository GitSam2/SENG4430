package ditTest.multiClassExample;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // printing dog sound
        Pet myDog = new Dog();
        System.out.println(myDog.noise());

        // printing cat sound
        Pet myCat = new Cat();
        System.out.println(myCat.noise());
    }
}

