package R16A_Group_6_A2;

public class CreditCard {

    private String name;
    private String number;

    CreditCard(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return this.name;
    }

    public String getNumber() {
        return this.number;
    }

    public boolean verify(String name, String number) {
        return (this.name.equals(name) && this.number.equals(number));
    }

}