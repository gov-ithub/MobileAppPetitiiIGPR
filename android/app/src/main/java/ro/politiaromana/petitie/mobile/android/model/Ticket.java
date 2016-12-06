package ro.politiaromana.petitie.mobile.android.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Ticket extends RealmObject {

    @Required
    public String typeStringValue;
    @Required
    public String description;

    public String address;
    public RealmList<RealmString> attachmentPathList;

    public Ticket() {
    }

    public Ticket(String pTypeStringValue, String pDescription) {
        typeStringValue = pTypeStringValue;
        description = pDescription;
    }

    public Ticket(String pTypeStringValue, RealmList<RealmString> pAttachmentPathList,
                  String pAddress, String pDescription) {
        typeStringValue = pTypeStringValue;
        attachmentPathList = pAttachmentPathList;
        address = pAddress;
        description = pDescription;
    }
}
