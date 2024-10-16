package org.example.model;


import jakarta.persistence.*;
import java.io.Serializable;


@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Version
    private long version;

    public AbstractEntity() {
    }
}
