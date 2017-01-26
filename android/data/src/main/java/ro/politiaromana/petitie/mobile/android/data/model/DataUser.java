package ro.politiaromana.petitie.mobile.android.data.model;

/**
 * Created by andrei.
 */

public final class DataUser extends BaseDataModel {
    public String address;
    public Role role;
    public String cnp;
    public String deviceId;
    public String password;
    public String phone;
    public int countyId;
    public String name;
    public int id;
    public String email;
    public Status status;

    public enum Status{
        active,
        inactive
    }

    public enum Role{
        admin,
        user,
        device
    }
}
