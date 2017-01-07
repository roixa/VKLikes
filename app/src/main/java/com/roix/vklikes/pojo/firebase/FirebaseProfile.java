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
    private Integer showCountBuy;
    private Integer showCountIn;
    private Integer showCountOut;
    private Double rating;
    boolean isActive=false;
    private List<FirebaseLikeTask> tasks;

    public FirebaseProfile(String userId) {
        this.userId = userId;
        this.likeCountIn = 0;
        this.likeCountOut = 0;
        this.showCountBuy = 0;
        this.showCountIn = 0;
        this.showCountOut = 0;
        this.rating = 0.;

    }
    public FirebaseProfile(){
        this.likeCountIn = 0;
        this.likeCountOut = 0;
        this.showCountBuy = 0;
        this.showCountIn = 0;
        this.showCountOut = 0;
        this.rating = 0.;

    }


    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("likeCountIn", likeCountIn);
        result.put("likeCountOut", likeCountOut);
        result.put("showCountBuy", showCountBuy);
        result.put("showCountIn", showCountIn);
        result.put("showCountOut", showCountOut);
        result.put("rating", rating);
        result.put("isActive", isActive);
        result.put("tasks", tasks);
        return result;
    }

    //@TODO make correct rating
    public void refreshData(){

        //if((showCountBuy+showCountOut)>likeCountIn){
        if((tasks!=null&&tasks.size()>0)){
            setActive(true);
        }
        else {
            setActive(false);
        }
        setRating((showCountBuy+showCountOut)==0?0:(double)likeCountIn/(showCountBuy+showCountOut));
    }

    public void addLikeIn(){
        likeCountIn++;
    }
    public void addLikeOut(){
        likeCountOut++;
    }
    public void addShowsIn(int i){
        showCountIn+=i;
    }
    public void addShowsOut(int i){
        showCountOut+=i;
    }
    public void addShowsBuy(int i){
        showCountBuy+=i;
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

    public Integer getShowCountBuy() {
        return showCountBuy;
    }

    public void setShowCountBuy(Integer showCountBuy) {
        this.showCountBuy = showCountBuy;
    }

    public Integer getShowCountIn() {
        return showCountIn;
    }

    public void setShowCountIn(Integer showCountIn) {
        this.showCountIn = showCountIn;
    }

    public Integer getShowCountOut() {
        return showCountOut;
    }

    public void setShowCountOut(Integer showCountOut) {
        this.showCountOut = showCountOut;
    }
}
