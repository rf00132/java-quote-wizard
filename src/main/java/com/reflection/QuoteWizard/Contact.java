package main.java.com.reflection.QuoteWizard;

public class Contact {
    private int contactId;
    public int getId(){ return contactId; }

    private String name;
    public String getName(){ return name; }
    public void setName(String newData){ name = newData; }

    private String addressLine1;
    public String getLine1(){ return addressLine1; }
    public void setLine1(String newData){ addressLine1 = newData; }

    private String addressLine2;
    public String getLine2(){ return addressLine2; }
    public void setLine2(String newData){ addressLine2 = newData; }

    private String city;
    public String getCity(){ return city; }
    public void setCity(String newData){ city = newData; }

    private String country;
    public String getCountry(){ return country; }
    public void setCountry(String newData){ country = newData; }

    private String postcode;
    public String getPostcode(){ return postcode; }
    public void setPostcode(String newData){ postcode = newData; }

    private String phoneNumber;
    public String getNumber(){ return phoneNumber; }
    public void setNumber(String newData){ phoneNumber = newData; }


    public Contact(){
        contactId = IdManager.GetNextContactId();
    }
}
