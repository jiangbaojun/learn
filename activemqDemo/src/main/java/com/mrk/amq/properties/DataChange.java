package com.mrk.amq.properties;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName DataChange
 * @Description: data change
 * @Author alan.zhang
 * @Date 2019/10/27
 * @Version V1.0
 **/
@Data
@Accessors(chain = true)
public class DataChange<T> extends FromModel {
    private T data;

}
