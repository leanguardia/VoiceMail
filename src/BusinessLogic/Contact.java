package BusinessLogic;

import java.util.ArrayList;

public class Contact {

    private String firstName;
    private String lastName;
    private ArrayList<String> numbers;

    public Contact(String fn, String ln) {
        firstName = fn;
        lastName = ln;
        numbers = new ArrayList<String>();
    }

    public void addNumber(String n) {
        numbers.add(n);
    }

    public void removeNumber(String n) {
        numbers.remove(n);
    }

    public String getString() {
        String s = new String(firstName + " " + lastName);
        for (String num: numbers) {
            s += " - " + num;
        }
        return s;
    }
}
