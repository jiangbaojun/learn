package com.mrk.amq.properties;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName DataChange
 * @Description: data change
 * @Author alan.zhang
 * @Date 2019/10/27
 * @Version V1.0
 **/
@Data
@Accessors(chain = true)
public class DataChange<T> implements Serializable {
    private T data;
    private Integer sourceSystemId;
    private Integer sponsorId;
    private Integer studyId;

}
