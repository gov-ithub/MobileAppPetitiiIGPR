package ro.politiaromana.petitie.mobile.android.data.model;

/**
 * Created by andrei.
 */

public final class DataToken extends BaseDataModel {
    public String expiryDate;
    public String entityId;
    public Type type;
    public String value;

    public enum Type{
        resetPassword,
        bearer,
        device
    }
}
