package com.htlord.assignment.model;

import java.util.UUID;

// POJO
public class PhoneCall {
    private UUID id;
    private Priority level;

    public PhoneCall(UUID id, Priority level){
        this.id = id;
        this.level = level;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Priority getLevel() {
        return level;
    }

    public void setLevel(Priority level) {
        this.level = level;
    }

    public String toString(){
        return "E("+this.level+"):["+this.id.toString()+"]";
    }
}
