package com.doppler.soufyane.smackup;

/**
 * Created by stackingcoder on 21-3-16.
 */
public class Contact {
    String naam;
    String nummer;

    public Contact(String naam, String nummer) {
        this.naam   = naam;
        this.nummer = nummer;
    }

    public String getName() {
        return this.naam;
    }

    public String getNumber() {
        return this.nummer;
    }

    public String toString() {
        return getName() + " " + "("+ getNumber() + ")";
    }

}
