package org.example.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
    @Embedded
    @NotNull
    private UniqueId entityId;

//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Convert(converter = UUIDConverter.class)
//    private UUID entityId;

    @Version
    private long version;

    public AbstractEntity() {
        this.entityId = new UniqueId();
    }
}
