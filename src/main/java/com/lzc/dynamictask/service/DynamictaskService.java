package com.lzc.dynamictask.service;


import com.alibaba.fastjson.JSON;
import com.lzc.dynamictask.entity.TaskCorn;
import com.lzc.dynamictask.repository.TaskCornRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Component(value = "dynamictaskService")
@Scope(value = "prototype")
@Slf4j
public class DynamictaskService {
    @Autowired
    TaskCornRepository taskCornRepository;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;


    private ScheduledFuture<?> future;

    private boolean taskRunning = false;

    public boolean isTaskRunning() {
        return taskRunning;
    }

    public void setTaskRunning(boolean taskRunning) {
        this.taskRunning = taskRunning;
    }

    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    private String schoolId;

    private boolean enableTask = true;

    private String corn;

    public void enableTask() {
        enableTask = true;
    }

    public void unableTask() {
        this.enableTask = false;
    }

    public void stopTask() {
        if (future != null) {
            future.cancel(true);
        }
    }

    public void startTask(){
        if (StringUtils.isEmpty(schoolId) || StringUtils.isEmpty(shopId)){
            log.info("not init shop school");
        }
        enableTask();
        List<TaskCorn> taskCorns = taskCornRepository.findBySchoolIdAndShopId(schoolId,shopId);
        log.info(JSON.toJSONString(taskCorns));
        future = threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                taskRunning = true;
                log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                try{
                    Thread.sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                if (enableTask){
                    CronTrigger trigger = new CronTrigger("0/30 * * * * ? ");
                    Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                    taskRunning = false;
                    return nextExecDate;
                }else {
                    CronTrigger trigger = new CronTrigger("0/10 * * * * ? ");
                    Date nextExecDate = trigger.nextExecutionTime(triggerContext);
                    taskRunning = false;
                    return nextExecDate;
                }

            }
        });
    }
    private void setNewCornList(String corn){
        this.corn = corn;
    }

    private String getCurrentCorn(){
        return "";
    }
}
