package ro.politiaromana.petitie.mobile.android.data.model;

/**
 * Created by andrei.
 */

public final class DataTicket extends BaseDataModel {
    public String ticketCountyId;
    public String address;
    public String cnp;
    public String ip;
    public String description;
    public String type;
    public int userId;
    public String deviceId;
    public ResponseType responseType;
    public String phone;
    public int countyId;
    public String name;
    public int id;
    public String email;

    public enum ResponseType{
        postal,
        email
    }

    /**
     * Created by andrei.
     */

    public static final class Message extends BaseDataModel {
        public String subject;
        public String name;
        public String message;
        public int ticketId;
    }

    /**
     * Created by andrei.
     */

    public static final class Attachment extends BaseDataModel {
        public String originalFileName;
        public String contentType;
        public String url;
    }
}
