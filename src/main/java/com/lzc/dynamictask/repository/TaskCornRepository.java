package com.lzc.dynamictask.repository;

import com.lzc.dynamictask.entity.TaskCorn;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TaskCornRepository extends MongoRepository<TaskCorn, String> {
    List<TaskCorn> findBySchoolIdAndShopId(String school,String shopId);

}
