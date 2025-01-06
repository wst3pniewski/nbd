package org.example.model;


import java.io.Serializable;
import java.util.UUID;


public abstract class AbstractEntity implements Serializable {
    //    @BsonProperty("_id")
//    private long entityId;
//
//    public long getEntityId() {
//        return entityId;
//    }
//
//    public AbstractEntity(long entityId) {
//        this.entityId = entityId;
//    }

    private UUID entityId;

    public UUID getEntityId() {
        return entityId;
    }

    public AbstractEntity() {
        this.entityId = UUID.randomUUID();
    }

    public AbstractEntity(UUID id) {
        this.entityId = id;
    }


}
