package ro.politiaromana.petitie.mobile.android.model;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by andrei.
 */

public class RealmString extends RealmObject {

    @Required
    public String val;


    public RealmString(){
    }

    public RealmString(String val){
        this.val = val;
    }

}
