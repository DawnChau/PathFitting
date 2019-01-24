package com.dawnchau.pathfitting.strategy;


import com.dawnchau.pathfitting.pojo.PathJson;

import java.util.List;

/**
 * 路径拟合策略接口
 * 1. 数据补全策略
 * 2. 路径拟合策略
 */
public interface IPathFittingStrategy {

    List<List<PathJson>> dataComplementStrategy(List<List<PathJson>> list);

    List<PathJson> pathFittingStrategy(List<List<PathJson>> list);

}
