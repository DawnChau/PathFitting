package com.dawnchau.pathfitting.strategy;

import com.dawnchau.pathfitting.pojo.PathJson;
import com.dawnchau.pathfitting.pojo.SVAPathJson;
import com.dawnchau.pathfitting.pojo.SuoBeiPathJson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BasicPathFittingWrapper {

    private static final String LOCATION = "location";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String Z = "z";
    private static final String TIMESTAMP = "Timestamp";

    private static IPathFittingStrategy pathFittingStrategy;

    public BasicPathFittingWrapper() {
        this.pathFittingStrategy = new BasicPathFittingStrategy();
    }

    public static JSONArray pathFitting(JSONArray suobeiPath, JSONArray svaPath) {
        List<PathJson> suobeiList = new ArrayList<PathJson>();
        List<PathJson> svaList = new ArrayList<PathJson>();
        List<List<PathJson>> list = new ArrayList<>();
        JSONArray resJson = new JSONArray();

        for (int i = 0; i < suobeiPath.length(); i++) {
            try {
                JSONObject outObject = suobeiPath.getJSONObject(i);
                JSONObject object = outObject.getJSONObject(LOCATION);

                suobeiList.add(
                        new SuoBeiPathJson(String.valueOf(object.getDouble(X)),
                                String.valueOf(object.getDouble(Y)),
                                String.valueOf(object.getDouble(Z)),
                                String.valueOf(outObject.getLong(TIMESTAMP)))
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < svaPath.length(); i++) {
            try {
                JSONObject outObject = svaPath.getJSONObject(i);
                JSONObject object = outObject.getJSONObject(LOCATION);

                svaList.add(
                        new SVAPathJson(String.valueOf(object.getDouble(X)),
                                String.valueOf(object.getDouble(Y)),
                                String.valueOf(object.getDouble(Z)),
                                String.valueOf(outObject.getLong(TIMESTAMP)))
                );
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        list.add(suobeiList);
        list.add(svaList);


        list = pathFittingStrategy.dataComplementStrategy(list);
        List<PathJson> res = pathFittingStrategy.pathFittingStrategy(list);


        for(PathJson pathJson:res){
            JSONObject object = new JSONObject();
            JSONObject outObject = new JSONObject();
            try {
                object.put(X,Double.parseDouble(pathJson.getX()))
                        .put(Y,Double.parseDouble(pathJson.getY()))
                        .put(Z,Double.parseDouble(pathJson.getZ()));
                outObject.put(LOCATION,object)
                        .put(TIMESTAMP,Long.parseLong(pathJson.getTime()));
                resJson.put(outObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return resJson;
    }
}
