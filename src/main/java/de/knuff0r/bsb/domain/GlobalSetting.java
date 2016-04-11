package de.knuff0r.bsb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by sebastian on 08.01.16.
 */
@Entity(name = "bsb_globalSetting")
public class GlobalSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String name;

    @Column(nullable = false)
    private String value;

    protected GlobalSetting() {
    }

    public GlobalSetting(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
