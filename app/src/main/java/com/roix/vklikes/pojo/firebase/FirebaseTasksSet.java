package com.roix.vklikes.pojo.firebase;

import java.util.List;
import java.util.Map;

/**
 * Created by roix on 04.01.2017.
 */

public class FirebaseTasksSet {
    private List<FirebaseLikeTask> tasks;

    public FirebaseTasksSet(List<FirebaseLikeTask> tasks) {
        this.tasks = tasks;
    }

    public List<FirebaseLikeTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<FirebaseLikeTask> tasks) {
        this.tasks = tasks;
    }
}
