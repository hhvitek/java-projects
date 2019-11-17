package app;


public class Main {
    public static void main(String[] args) {

        String first = "";
        String second = null;
        String output;

        output = Annoted.concatenate(first, second);

        System.out.println(output);

        if (output.isBlank()) {
            System.out.println("isBlank");
        } else {
            System.out.println("NotIsBlank");
        }


    }
}
