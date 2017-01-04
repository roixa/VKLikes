package com.roix.vklikes.pojo.firebase;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by roix on 17.12.2016.
 */
@IgnoreExtraProperties
public class FirebaseProfile {
    private String userId;
    private Integer likeCountIn;
    private Integer likeCountOut;
    private Integer likeCountBuy;
    private Double rating;
    boolean isActive=false;
    private List<FirebaseLikeTask> tasks;

    public FirebaseProfile(String userId, Integer likeCountIn, Integer likeCountOut,Integer likeCountBuy,Double rating) {
        this.userId = userId;
        this.likeCountIn = likeCountIn;
        this.likeCountOut = likeCountOut;
        this.likeCountBuy = likeCountBuy;
        this.rating = rating;

    }
    public FirebaseProfile(){

    }


    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("likeCountIn", likeCountIn);
        result.put("likeCountOut", likeCountOut);
        result.put("likeCountBuy", likeCountBuy);
        result.put("rating", rating);
        result.put("isActive", isActive);
        result.put("tasks", tasks);
        return result;
    }

    public void refreshData(){
        if((likeCountOut+likeCountBuy)>likeCountIn){
            setActive(true);
        }
        else {
            setActive(false);
        }
        setRating((double)likeCountIn/(likeCountOut+likeCountBuy));
    }

    public List<FirebaseLikeTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<FirebaseLikeTask> tasks) {
        this.tasks = tasks;
    }
    public void addTasks(List<FirebaseLikeTask> t){
        if(tasks==null) tasks=new ArrayList<>();
        tasks.addAll(t);
    }

    public void removeTask(FirebaseLikeTask t){
        List<FirebaseLikeTask> newTasks=new ArrayList<>();
        for(FirebaseLikeTask task:tasks){
            if(!task.equals(t))newTasks.add(task);
        }
        setTasks(newTasks);
    }
    public FirebaseLikeTask removeTask(){
        return tasks.remove(0);
    }

    public Integer getLikeCountBuy() {
        return likeCountBuy;
    }

    public void setLikeCountBuy(Integer likeCountBuy) {
        this.likeCountBuy = likeCountBuy;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getLikeCountOut() {
        return likeCountOut;
    }

    public void setLikeCountOut(Integer likeCountOut) {
        this.likeCountOut = likeCountOut;
    }

    public Integer getLikeCountIn() {
        return likeCountIn;
    }

    public void setLikeCountIn(Integer likeCountIn) {
        this.likeCountIn = likeCountIn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }



}
