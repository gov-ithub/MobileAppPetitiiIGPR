package ro.politiaromana.petitie.mobile.android.model;

import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Required;


@SuppressWarnings("SimplifiableIfStatement")
public class Profile extends RealmObject {

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

    public boolean isEqualTo(@Nullable Profile profile) {
        if (this == profile) return true;
        if (profile == null) return false;

        if (!firstName.equals(profile.firstName)) return false;
        if (!lastName.equals(profile.lastName)) return false;
        if (!email.equals(profile.email)) return false;
        if (!cnp.equals(profile.cnp)) return false;
        if (!address.equals(profile.address)) return false;
        if (!county.equals(profile.county)) return false;
        return phone != null ? phone.equals(profile.phone) : profile.phone == null;
    }
}
