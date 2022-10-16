package cn.lzscxb.domain.model;

import lombok.Data;

@Data
public class MinioObjectItem {
    private String objectName;
    private Long size;
}
