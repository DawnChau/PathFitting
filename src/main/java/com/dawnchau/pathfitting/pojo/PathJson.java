package com.dawnchau.pathfitting.pojo;

import lombok.Data;

/**
 * 路径拟合基类
 */
@Data
public abstract class PathJson {
    protected String x;
    protected String y;
    protected String z;
    protected String time;

    public PathJson(String x, String y, String z, String time) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.time = time;
    }
}
