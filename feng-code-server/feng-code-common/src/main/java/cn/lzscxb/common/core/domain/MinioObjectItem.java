package cn.lzscxb.common.core.domain;

import lombok.Data;

@Data
public class MinioObjectItem {
    private String objectName;
    private Long size;
}
