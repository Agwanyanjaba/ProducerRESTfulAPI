package com.wycliffe.producer.model;

public class Account {
    private String CID;
    private String FirstName;
    private String LastName;
    private int  KES;
    private int USD;
    private int EUR;


    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public int getKES() {
        return KES;
    }

    public void setKES(int KES) {
        this.KES = KES;
    }

    public int getUSD() {
        return USD;
    }

    public void setUSD(int USD) {
        this.USD = USD;
    }

    public int getEUR() {
        return EUR;
    }

    public void setEUR(int EUR) {
        this.EUR = EUR;
    }

    public Account() {

    }

    @Override
    public String toString() {
        return "Account{" +
                "CID='" + CID + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", KES=" + KES +
                ", USD=" + USD +
                ", EUR=" + EUR +
                '}';
    }
}
