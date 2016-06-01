package BusinessLogic;

import java.util.ArrayList;

public class Contact {

    private String firstName;
    private String lastName;
    private String number;

    public Contact(String fn, String ln) {
        firstName = fn;
        lastName = ln;
    }

    public void addNumber(String n) {
        number = n;
    }

    public String getString() {
        return firstName + " " + lastName + " " + number;
    }

    public String getName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNumber() {
        return number;
    }
}
