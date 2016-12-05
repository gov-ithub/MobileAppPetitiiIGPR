package ro.politiaromana.petitie.mobile.android.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Required;


public class Profile extends RealmObject implements Serializable {

    @Required
    public String firstName;
    @Required
    public String lastName;
    @Required
    public String email;
    @Required
    public String cnp;
    @Required
    public String address;
    @Required
    public String county;
    public String phone;

    public Profile() {}

    public Profile(String firstName, String lastName, String email, String cnp, String address, String county, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cnp = cnp;
        this.address = address;
        this.county = county;
        this.phone = phone;
    }
}
