package com.lzc.dynamictask.config;

import com.alibaba.fastjson.JSON;
import com.lzc.dynamictask.entity.SchoolInfo;
import com.lzc.dynamictask.entity.TaskCorn;
import com.lzc.dynamictask.repository.SchoolInfoRepository;
import com.lzc.dynamictask.repository.TaskCornRepository;
import com.lzc.dynamictask.service.DynamictaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class LoadSchoolCache  implements CommandLineRunner {

    @Autowired
    SchoolInfoRepository schoolInfoRepository;

    @Autowired
    TaskCornRepository taskCornRepository;

    @Autowired
    TaskBeanUtil taskBeanUtil;

    public static Vector<SchoolInfo> schoolInfoVector = new Vector<>();

    public static ConcurrentHashMap<String,DynamictaskService> beanMap = new ConcurrentHashMap<>();

    @Override
    public void run(String... args) throws Exception {
        List<SchoolInfo> schoolInfos = schoolInfoRepository.findAll();
        if (schoolInfos.size() == 0){
            for (int i = 0; i < 3; i++){
                SchoolInfo info = new SchoolInfo();
                info.setId(UUID.randomUUID().toString().replace("-",""));
                info.setShoolId(""+i+1);
                info.setShopId("123456");
                info.setStatus("1");
                schoolInfos.add(info);
            }
        }
        schoolInfoVector.addAll(schoolInfos);
        log.info(JSON.toJSONString(schoolInfos));
        initTaskBean();
    }

    private void createCorn(){
        TaskCorn corn = new TaskCorn();
        corn.setCorn("0/30 * * * * ? ");
    }

    private void initTaskBean(){
        for (SchoolInfo info : schoolInfoVector){
            String key = info.getShopId() + info.getShoolId();
            DynamictaskService service = TaskBeanUtil.getBean(DynamictaskService.class);
            if (beanMap.get(key) == null){
                beanMap.put(info.getId(),service);
                service.setShopId(info.getShopId());
                service.setSchoolId(info.getShoolId());
                service.startTask();
                log.info("beanid:"+service);
            }

        }
    }
    static void createTaskBean(String shopId, String schoolId){
        DynamictaskService service = beanMap.get(shopId+schoolId);
        if (service == null){
            service = TaskBeanUtil.getBean(DynamictaskService.class);
            beanMap.put(shopId+schoolId,service);
            service.startTask();
        }else {
            if (service.isTaskRunning()){
                service.isTaskRunning();
            }
            service.setShopId(shopId);
            service.setSchoolId(schoolId);
        }
    }
    @Scheduled(fixedDelay = 25000)
    public void test(){
        log.info("unableTask");
        for (DynamictaskService service : beanMap.values()) {
            service.unableTask();
        }
    }
    @Scheduled(fixedDelay = 51000)
    public void test1(){
        log.info("ableTask");
        for (DynamictaskService service : beanMap.values()) {
            service.enableTask();
        }
    }
}
