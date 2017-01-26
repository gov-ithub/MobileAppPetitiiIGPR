package ro.politiaromana.petitie.mobile.android.data.model;

/**
 * Created by andrei.
 */

public class DataError implements IDataModel {
    public String errorId;
    public int httpStatus;
    public String message;
    public String errorType;
}
