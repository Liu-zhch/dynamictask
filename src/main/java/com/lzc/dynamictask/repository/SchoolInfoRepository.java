package com.lzc.dynamictask.repository;

import com.lzc.dynamictask.entity.SchoolInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolInfoRepository extends MongoRepository<SchoolInfo,String> {
}
