package com.dawnchau.pathfitting.strategy;

import com.dawnchau.pathfitting.pojo.PathJson;
import java.util.List;

import com.dawnchau.pathfitting.pojo.SVAPathJson;
import com.dawnchau.pathfitting.pojo.SuoBeiPathJson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicPathFittingStrategyTest {

    @Autowired
    private BasicPathFittingStrategy basicPathFittingStrategy;

    @Test
    public void dataComplementStrategy() {
        List<PathJson> suobeiPathJson = new ArrayList<>();
        suobeiPathJson.add(new SuoBeiPathJson("10","13","4","10"));
        suobeiPathJson.add(new SuoBeiPathJson("15","15","4","12"));
        suobeiPathJson.add(new SuoBeiPathJson("20","17","4","14"));

        List<PathJson> huaweiPathJson = new ArrayList<>();
        huaweiPathJson.add(new SVAPathJson("12","14","4","11"));
        huaweiPathJson.add(new SVAPathJson("17","16","4","13"));
        huaweiPathJson.add(new SVAPathJson("19","18","4","14"));

        List<List<PathJson>> list = new ArrayList<>();
        list.add(suobeiPathJson);
        list.add(huaweiPathJson);

        List<List<PathJson>> res = basicPathFittingStrategy.dataComplementStrategy(list);
        log.info("{}",res.size());
        log.info("{}",res.get(0));
        log.info("{}",res.get(1));
        log.info("{}",basicPathFittingStrategy.pathFittingStrategy(res));
    }

    @Test
    public void pathFittingStrategy() {
    }
}