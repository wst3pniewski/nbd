package org.example.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

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
//    @BsonId
    @BsonId
    @BsonProperty("_id")
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
