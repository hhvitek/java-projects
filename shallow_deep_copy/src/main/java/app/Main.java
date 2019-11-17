package app;

public class Main {

    /**
     * primitivni type se prekopci ok - treba Stringy
     * Objekty se kopirujou pouze ukazatele
     * <p>
     * řešením je:
     * a) copy kontruktor - subclass issue
     * b) clone() - throw issue
     *
     * @param args
     */
    public static void main(String[] args) {

        Address addressDuhova = new Address("Duhová", "269", "Náchod");
        Address addressLipi = new Address("Lipí", "E188", "Lipí");

        Person personKvitek = new Person("Kvítek", addressDuhova);

        System.out.println(personKvitek);

        Person shallow1_PersonKvitek = new Person(personKvitek);

        personKvitek.getAddress()
                    .setCity("Kocourkov");

        System.out.println(shallow1_PersonKvitek);

    }

}
