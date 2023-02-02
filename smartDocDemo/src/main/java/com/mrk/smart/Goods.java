package com.mrk.smart;

import lombok.Data;

/**
 * 商品信息
 * @author jiangbaojun
 * @date 2023/1/31 15:48
 * @version v1.0
 */
@Data
public class Goods {
    /** 编号 */
    private Integer code;
    /** 商品名称 */
    private String name;
    /** 商品类别1 */
    private TypeEnum typeEnum;
    /** 商品类别 */
    private Integer type;
}
