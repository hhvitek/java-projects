package app;

public class Person implements Cloneable {

    private String  name;
    private Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Person(Person copiedPerson) {
        name = copiedPerson.name;
        address = new Address(copiedPerson.address);
    }

    @Override
    public Object clone() {
        Person person;
        try {
            person = (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            person = new Person(name, address);
        }
        person.address = (Address) address.clone();
        return person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" + "name='" + name + '\'' + ", address=" + address + '}';
    }

}
