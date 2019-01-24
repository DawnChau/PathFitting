package com.dawnchau.pathfitting.strategy;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicPathFittingWrapperTest {

    private BasicPathFittingWrapper basicPathFittingWrapper = new BasicPathFittingWrapper();

    @Test
    public void pathFitting() {
        JSONArray suobei = new JSONArray();
        JSONArray huawei = new JSONArray();
        try {
            JSONObject object = new JSONObject();
            object.put("x", 10)
                    .put("y", 12)
                    .put("z", 4);
            suobei.put(new JSONObject().put("location",object).put("Timestamp",12));

            JSONObject object1 = new JSONObject();
            object1.put("x", 12)
                    .put("y", 14)
                    .put("z", 4);
            suobei.put(new JSONObject().put("location",object1).put("Timestamp",14));

            JSONObject object2 = new JSONObject();
            object2.put("x", 14)
                    .put("y", 16)
                    .put("z", 4);
            suobei.put(new JSONObject().put("location",object2).put("Timestamp",16));

            // 华为

            JSONObject object4 = new JSONObject();
            object4.put("x", 11)
                    .put("y", 13)
                    .put("z", 4);
            huawei.put(new JSONObject().put("location",object4).put("Timestamp",13));

            JSONObject object5 = new JSONObject();
            object5.put("x", 13)
                    .put("y", 15)
                    .put("z", 4);
            huawei.put(new JSONObject().put("location",object5).put("Timestamp",15));

            JSONObject object6 = new JSONObject();
            object6.put("x", 15)
                    .put("y", 17)
                    .put("z", 4);
            huawei.put(new JSONObject().put("location",object6).put("Timestamp",17));


            log.info("{}",basicPathFittingWrapper.pathFitting(suobei,huawei));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}