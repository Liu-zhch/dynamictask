package com.lzc.dynamictask.entity;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "taskcorn")
public class TaskCorn {
    private String id;
    private String corn;
    private String schoolId;
    private String shopId;
    private String cornStatus;
    private long   cornTime;
}
