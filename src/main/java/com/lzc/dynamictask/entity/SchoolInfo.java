package com.lzc.dynamictask.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "school")
public class SchoolInfo {
    private String id;
    private String shoolId;
    private String shopId;
    private String status;
}
