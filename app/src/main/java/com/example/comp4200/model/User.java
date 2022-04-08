package com.example.comp4200.model;

public class User {

    private String id;
    private String displayName;
    private String handle;
    private String description;

    public User(){

    }

    public User(String uid, String displayName, String handle, String description){
        this.id = uid;
        this.displayName = displayName;
        this.handle = handle;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

}
