package College.People;

import java.io.Serializable;

public class Person implements Serializable {
    private String fname;
    private String lname;
    private String mail;

    public Person(final String fname, final String lname, final String mail) {
        this.fname = fname;
        this.lname = lname;
        this.mail = mail;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public void setFname(final String fname) {
        this.fname = fname;
    }

    public void setLname(final String lname) {
        this.lname = lname;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(final String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return String.format("%s %s (email: %s)", fname, lname, mail);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Person && obj.toString().equals(toString());
    }

    public String getName() {
        return String.format("%s %s", lname, fname);
    }
}
