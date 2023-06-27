package com.mrk.amq.properties;

import lombok.Data;

import java.io.Serializable;

/**
 * 标识消息来源
 **/
@Data
public class FromModel implements Serializable {
    private Integer systemId;
    private Integer sponsorId;
    private Integer studyId;

}
