package models;

public class Transactions {
    protected double sum;
    protected String type;
    protected String currency;

    protected String day;
    protected String month;
    protected String year;
    protected String week;

    public Transactions(double sum, String type, String currency, String year, String month, String day, String week) {
        this.sum = sum;
        this.type = type;
        this.currency = currency;
        this.year = year;
        this.month = month;
        this.day = day;
        this.week = week;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
    public double getSum() {
        return sum;
    }
    public String getDay() {
        return day;
    }
    public String getYear(){
        return year;
    }
    public String getMonth() {
        return month;
    }
    public String getWeek() {
        return week;
    }
    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
       return   sum +
                " " + currency +
                ", Type: '" + type + '\'' +
                ", Date: " + day + "/" + month + "/" + year +
                ", Week: " + week;
    }
}

