package org.example.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.example.model.converters.UUIDConverter;

import java.util.UUID;

@Embeddable
@Access(AccessType.FIELD)
public class UniqueId {
    @Convert(converter = UUIDConverter.class)
    @GeneratedValue(strategy = GenerationType.UUID)
    private final UUID uuid;

    public UniqueId() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", uuid)
                .toString();
    }
}
