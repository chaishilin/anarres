package com.csl.anarres.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/24 10:17
 * @Description:
 */
public class JsonUtil {
    public static Object getPath(JSONObject object, String path){
        String[] args = path.split("\\.",2);
        if(object == null){
            return null;
        }
        if(args.length == 1){
            return object.get(path);
        }else{
            Object o = object.get(args[0]);
            if(o != null){
                return getPath((JSONObject) o,args[1]);
            }else{
                return null;
            }
        }
    }

    public static void putPath(JSONObject object, String path, Object value){
        String[] args = path.split("\\.",2);
        if(args.length == 1){
            object.put(path,value);
        }else{
            JSONObject o = getOrBuild(object,args[0]);
            object.put(args[0],o);
            putPath(o,args[1],value);
        }
    }

    private static JSONObject getOrBuild(JSONObject object,String key){
        Object result = object.get(key);
        if(result == null){
            return new JSONObject();
        }else{
            return (JSONObject) result;
        }
    }
}
