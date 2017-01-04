package com.roix.vklikes.pojo.firebase;

/**
 * Created by roix on 02.01.2017.
 */

public class FirebaseLikeTask {
    private String photoID;
    private String ownerID;
    private String photoUrl;
    private boolean isPhoto=true;


    public FirebaseLikeTask(){}
    public FirebaseLikeTask(boolean isPhoto, String photoID, String ownerID, String photoUrl) {
        this.isPhoto = isPhoto;
        this.photoID = photoID;
        this.ownerID = ownerID;
        this.photoUrl=photoUrl;
    }

    public boolean equals(FirebaseLikeTask t) {
        return photoID.equals(t.getPhotoID())&&ownerID.equals(t.getOwnerID());
    }

    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public boolean isPhoto() {
        return isPhoto;
    }

    public void setPhoto(boolean photo) {
        isPhoto = photo;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

}
