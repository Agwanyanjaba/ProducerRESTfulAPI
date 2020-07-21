package com.wycliffe.services.model;

public class Account {
    private String CID;
    private String PID;
    private String FirstName;
    private String LastName;
    private String email;
    private String MSISDN;
    private String IMEI;
    private String TOKEN;
    private double balance;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
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

    public String getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    @Override
    public String toString() {
        return "Account{" +
                "CID ='" + CID + '\'' +
                ",PID ='" + PID + '\'' +
                ", FirstName ='" + FirstName + '\'' +
                ", LastName ='" + LastName + '\'' +
                ", email ='" + email + '\'' +
                ", MSISDN =" + MSISDN +
                ", IMEI =" + IMEI +
                ", TOKEN =" + TOKEN +
                ", Balance =" + balance +
                '}';
    }
}
