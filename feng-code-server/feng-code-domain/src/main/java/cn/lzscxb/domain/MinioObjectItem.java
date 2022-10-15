package cn.lzscxb.domain;

import lombok.Data;

@Data
public class MinioObjectItem {
    private String objectName;
    private Long size;
}
