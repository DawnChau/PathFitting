package com.dawnchau.pathfitting.strategy;


import com.dawnchau.pathfitting.pojo.PathJson;
import com.dawnchau.pathfitting.pojo.SVAPathJson;
import com.dawnchau.pathfitting.pojo.SuoBeiPathJson;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础的路径拟合策略
 */
@Service
public class BasicPathFittingStrategy implements IPathFittingStrategy {


    /**
     * 数据补全
     *
     * @param list
     * @return
     */
    @Override
    public List<List<PathJson>> dataComplementStrategy(List<List<PathJson>> list) {
        //1. 拿到索贝的路径数据和华为的路径数据
        List<PathJson> suoBeiPathJsons = list.get(0);
        List<PathJson> svaPathJsons = list.get(1);

        //2. 补全索贝数据
        List<PathJson> newSuoBeiPathJsons = new ArrayList<PathJson>();
        int suobeiIndex = 0;
        int svaIndex = 0;
        while (suobeiIndex < suoBeiPathJsons.size()  || svaIndex < svaPathJsons.size() ) {

            if(suobeiIndex==suoBeiPathJsons.size() && svaIndex == svaPathJsons.size())
                break;


            SuoBeiPathJson suoBeiPathJson = suobeiIndex==suoBeiPathJsons.size()?null:(SuoBeiPathJson) suoBeiPathJsons.get(suobeiIndex);
            SVAPathJson svaPathJson = svaIndex==svaPathJsons.size()?null:(SVAPathJson) svaPathJsons.get(svaIndex);

            // 索贝遍历完了
            if(suobeiIndex == suoBeiPathJsons.size()){
                while(svaIndex!=svaPathJsons.size()){
                    newSuoBeiPathJsons.add(new SuoBeiPathJson(svaPathJson.getX(), svaPathJson.getY(),
                            svaPathJson.getZ(), svaPathJson.getTime()));
                    svaIndex++;
                    svaPathJson = svaIndex==svaPathJsons.size()?null:(SVAPathJson) svaPathJsons.get(svaIndex);
                }
            }else if(svaIndex == svaPathJsons.size()){
                // 华为遍历完了
                while(suobeiIndex != suoBeiPathJsons.size()){
                    newSuoBeiPathJsons.add(suoBeiPathJson);
                    suobeiIndex++;
                    suoBeiPathJson = suobeiIndex==suoBeiPathJsons.size()?null:(SuoBeiPathJson) suoBeiPathJsons.get(suobeiIndex);
                }

            }else if (Long.parseLong(suoBeiPathJson.getTime()) < Long.parseLong(svaPathJson.getTime())) {
                // 索贝时间早
                newSuoBeiPathJsons.add(suoBeiPathJson);
                suobeiIndex++;
            } else if (Long.parseLong(suoBeiPathJson.getTime()) > Long.parseLong(svaPathJson.getTime())) {
                // 华为时间早
                newSuoBeiPathJsons.add(new SuoBeiPathJson(svaPathJson.getX(), svaPathJson.getY(),
                        svaPathJson.getZ(), svaPathJson.getTime()));
                svaIndex++;
            } else {
                // 时间相同，不补
                newSuoBeiPathJsons.add(suoBeiPathJson);
                suobeiIndex++;
                svaIndex++;
            }
        }

        // 3. 补全华为数据
        List<PathJson> newSVAPathJsons = new ArrayList<PathJson>();
        suobeiIndex = 0;
        svaIndex = 0;
        while (suobeiIndex < suoBeiPathJsons.size() || svaIndex < svaPathJsons.size() ) {

            if(suobeiIndex==suoBeiPathJsons.size() && svaIndex == svaPathJsons.size())
                break;

            SuoBeiPathJson suoBeiPathJson = suobeiIndex==suoBeiPathJsons.size()?null:(SuoBeiPathJson) suoBeiPathJsons.get(suobeiIndex);
            SVAPathJson svaPathJson = svaIndex==svaPathJsons.size()?null:(SVAPathJson) svaPathJsons.get(svaIndex);

            // 索贝遍历完了
            if(suobeiIndex == suoBeiPathJsons.size()){
                while(svaIndex!=svaPathJsons.size()){
                    newSVAPathJsons.add(svaPathJson);
                    svaIndex++;
                    svaPathJson = svaIndex==svaPathJsons.size()?null:(SVAPathJson) svaPathJsons.get(svaIndex);
                }
            }else if(svaIndex == svaPathJsons.size()){
                // 华为遍历完了
                while(suobeiIndex != suoBeiPathJsons.size()){
                    newSVAPathJsons.add(new SVAPathJson(suoBeiPathJson.getX(), suoBeiPathJson.getY(),
                            suoBeiPathJson.getZ(), suoBeiPathJson.getTime()));
                    suobeiIndex++;
                    suoBeiPathJson = suobeiIndex==suoBeiPathJsons.size()?null:(SuoBeiPathJson) suoBeiPathJsons.get(suobeiIndex);
                }

            }else if (Long.parseLong(suoBeiPathJson.getTime()) < Long.parseLong(svaPathJson.getTime())) {
                // 索贝时间早
                newSVAPathJsons.add(new SVAPathJson(suoBeiPathJson.getX(), suoBeiPathJson.getY(),
                        suoBeiPathJson.getZ(), suoBeiPathJson.getTime()));
                suobeiIndex++;
            } else if (Long.parseLong(suoBeiPathJson.getTime()) > Long.parseLong(svaPathJson.getTime())) {
                // 华为时间早
                newSVAPathJsons.add(svaPathJson);
                svaIndex++;
            } else {
                // 时间相同 不补
                newSVAPathJsons.add(svaPathJson);
                suobeiIndex++;
                svaIndex++;
            }
        }

        //4. 返回
        List<List<PathJson>> res = new ArrayList<List<PathJson>>();
        res.add(newSuoBeiPathJsons);
        res.add(newSVAPathJsons);
        return res;
    }

    @Override
    public List<PathJson> pathFittingStrategy(List<List<PathJson>> list) {
        // 1. 取出索贝数据和华为数据
        List<PathJson> suoBeiPathJsons = list.get(0);
        List<PathJson> svaPathJsons = list.get(1);

        // 2. 路径拟合
        List<PathJson> res = new ArrayList<PathJson>();
        for(int i = 0;i < suoBeiPathJsons.size();i++){
            SuoBeiPathJson suoBeiPathJson = (SuoBeiPathJson) suoBeiPathJsons.get(i);
            SVAPathJson svaPathJson = (SVAPathJson) svaPathJsons.get(i);

            res.add(new SuoBeiPathJson(
                    new BigDecimal(suoBeiPathJson.getX()).add(new BigDecimal(svaPathJson.getX())).divide(new BigDecimal(2)) + "",
                    new BigDecimal(suoBeiPathJson.getY()).add(new BigDecimal(svaPathJson.getY())).divide(new BigDecimal(2)) + "",
                    suoBeiPathJson.getZ(), suoBeiPathJson.getTime()));
        }
        return res;

    }


}
