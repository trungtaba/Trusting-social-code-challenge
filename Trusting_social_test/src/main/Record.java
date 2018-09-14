package main;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Record implements Comparable<Record> {

    private String phone;
    private Date activationDate;
    private Date deactivationDate;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public Record(String phone, Date activationDate, Date deactivationDate) {
        this.phone = phone;
        this.activationDate = activationDate;
        this.deactivationDate = deactivationDate;
    }

    public Record(String phone, String activationDate, String deactivationDate) {
        this.phone = phone;
        this.activationDate = parseDate(activationDate);
        this.deactivationDate = parseDate(deactivationDate);
    }

    public Record(String phone, String activationDate) {
        this.phone = phone;
        this.activationDate = parseDate(activationDate);
    }

    private Date parseDate(String dateStr) {
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Error when parsing date");
            System.out.println(e.toString());
            return null;
        }
    }

    public String getPhone() {
        return phone;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public Date getDeactivationDate() {
        return deactivationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public void setDeactivationDate(Date deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public String getPrintString() {
        String date = format.format(this.getActivationDate());
        return this.phone + "," + date;
    }

    @Override
    public int compareTo(Record other) {
        if (!this.phone.equals(other.phone)) {
            return -other.phone.compareTo(this.phone);
        }

        if (!this.activationDate.equals(other.activationDate)) {
            return -other.activationDate.compareTo(this.activationDate);
        }

        return -other.deactivationDate.compareTo(this.deactivationDate);
    }

    @Override
    public String toString() {
        String string = this.phone + "," + format.format(this.activationDate);
        if (this.deactivationDate != null) {
            string += "," + format.format(this.deactivationDate);
        }
        return string;
    }
    
    public String format(Date date){
        return format.format(date);
    }
}
