
package com.roix.vklikes.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfoResponse {

    @SerializedName("response")
    @Expose
    private List<User> user = null;

    /**
     * 
     * @return
     *     The user
     */
    public List<User> getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(List<User> user) {
        this.user = user;
    }

}
