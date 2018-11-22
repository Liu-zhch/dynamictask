package com.lzc.dynamictask.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "student")
public class StudentInfo {
    private String id;
    private String shoolId;
    private String shopId;
    private String sutdentId;
    private int    mathScord;
}
