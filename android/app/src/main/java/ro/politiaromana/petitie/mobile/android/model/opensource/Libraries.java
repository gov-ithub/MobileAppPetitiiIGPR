package ro.politiaromana.petitie.mobile.android.model.opensource;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by andrei.
 */

public class Libraries {
    @JsonProperty("dependencies")
    private List<Dependency> dependencies = null;

    @JsonProperty("dependencies")
    public List<Dependency> getDependencies() {
        return dependencies;
    }

    @JsonProperty("dependencies")
    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }
}
