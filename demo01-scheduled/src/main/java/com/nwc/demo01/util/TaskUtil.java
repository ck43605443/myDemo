package com.nwc.demo01.util;


import com.nwc.demo01.bojo.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskUtil {

    public static List<Task> tasks = new ArrayList<>();

    public static void addTask(Integer id, String name, String cron) {
        Task task = new Task(id, name, cron);
        tasks.add(task);
    }
    public  void deleteTask(Integer id) {
        //findById -->deleteTask
    }
    public static void toDo1() {

    }
     public static void toDo2() {
        System.out.println("关灯任务:"+new Date());
    }

}
