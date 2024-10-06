package org.example.model;


import jakarta.persistence.Embedded;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
//    @Embedded
//    @NotNull
//    private UniqueId entityId;
}
