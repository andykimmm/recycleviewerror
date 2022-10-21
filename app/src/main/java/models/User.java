package models;

import lombok.Data;

@Data
public class User {
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    private String email;
    private String name;
    private String profileUrl;
    private boolean selection;



}
