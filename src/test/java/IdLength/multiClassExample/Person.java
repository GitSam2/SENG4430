package IdLength.multiClassExample;

public class Person {
    private String name;
    private int age;
    private String thisIdentifierIsOver30CharactersLong; // triggers exceedsMaxLength (this one is 36 characters long)

    public Person(String name, int age, String thisIdentifierIsOver30CharactersLong) {
        this.name = name;
        this.age = age;
        this.thisIdentifierIsOver30CharactersLong = thisIdentifierIsOver30CharactersLong;
    }

    public void printInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}
