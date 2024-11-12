package org.example.model;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.io.Serializable;


public abstract class AbstractEntity implements Serializable {

    @BsonProperty("_id")
    private long entityId;

    public long getEntityId() {
        return entityId;
    }

    public AbstractEntity(long entityId) {
        this.entityId = entityId;
    }

}
