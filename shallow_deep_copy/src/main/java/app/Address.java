package app;

public class Address implements Cloneable {

    private String streetName;
    private String streetNo;
    private String city;

    public Address(String streetName, String streetNo, String city) {
        this.streetName = streetName;
        this.streetNo = streetNo;
        this.city = city;
    }

    public Address(Address copiedAddress) {
        streetName = copiedAddress.streetName;
        streetNo = copiedAddress.streetNo;
        city = copiedAddress.city;
    }

    @Override
    public Object clone() {
        try {
            return (Address) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Address(streetName, streetNo, city);
        }
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" + "streetName='" + streetName + '\'' + ", streetNo='" + streetNo + '\'' +
               ", city='" + city + '\'' + '}';
    }

}
