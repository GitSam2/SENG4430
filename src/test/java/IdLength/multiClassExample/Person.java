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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getThisIdentifierIsOver30CharactersLong() {
        return thisIdentifierIsOver30CharactersLong;
    }

    public void setThisIdentifierIsOver30CharactersLong(String thisIdentifierIsOver30CharactersLong) {
        this.thisIdentifierIsOver30CharactersLong = thisIdentifierIsOver30CharactersLong;
    }

    
}
