package ro.politiaromana.petitie.mobile.android.model.opensource;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by andrei.
 */

public class Dependency {
    @JsonProperty("name")
    private String name;
    @JsonProperty("file")
    private String file;
    @JsonProperty("licenses")
    private List<License> licenses = null;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("file")
    public String getFile() {
        return file;
    }

    @JsonProperty("file")
    public void setFile(String file) {
        this.file = file;
    }

    @JsonProperty("licenses")
    public List<License> getLicenses() {
        return licenses;
    }

    @JsonProperty("licenses")
    public void setLicenses(List<License> licenses) {
        this.licenses = licenses;
    }

}
