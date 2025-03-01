package com.model;

import java.util.UUID;
/**
 * Class representing an umodifiable UUID
 */
public final class ID {
    public final String uuid;
    /**
     * Constructs a UUID object from the given id
     * @param uiid
     */
    public ID(String uiid){
        this.uuid = uiid;
    }
    /**
     * Constructs a UUID object with a random UUID
     */
    public ID(){
        uuid = UUID.randomUUID().toString();
    }
}
