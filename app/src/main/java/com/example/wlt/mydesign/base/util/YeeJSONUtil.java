/*
 * Copyright (c) 2014,CSII.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wlt.mydesign.base.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangLuTao on 2017-04-12.
 * json解析封装
 */

public class YeeJSONUtil {


    public static String getString(JSONObject jsonObject, String name) {
        String value = "";
        try {
            if (jsonObject != null) {
                value = jsonObject.getString(name);
            }
        } catch (JSONException e) {
            value = "";
        }
        return value;
    }

    public static String getString(JSONArray jsonArray, int position) {
        String value = "";
        try {
            if (jsonArray != null) {
                value = jsonArray.getString(position);
            }
        } catch (JSONException e) {
            value = "";
        }
        return value;
    }

    public static String getString(JSONArray jsonArray, int i, String name) {
        String value = "";
        JSONObject jsonObject = getJSONObject(jsonArray, i);
        try {
            if (jsonObject != null) {
                value = jsonObject.getString(name);
            }
        } catch (JSONException e) {
            value = "";
        }
        return value;
    }


    public static Double getDouble(JSONObject jsonObject, String name) {
        Double value = 0.0;
        try {
            if (jsonObject != null) {
                value = jsonObject.getDouble(name);
            }
        } catch (JSONException e) {
            value = 0.0;
        }
        return value;
    }


    public static Boolean getBoolean(JSONObject jsonObject,
                                     String name) {
        Boolean value = null;
        try {
            if (jsonObject != null) {
                value = jsonObject.getBoolean(name);
            }
        } catch (JSONException e) {
            value = null;
        }
        return value;
    }


    public static int getInt(JSONObject jsonObject, String name) {
        int value = 0;
        try {
            if (jsonObject != null) {
                value = jsonObject.getInt(name);
            }
        } catch (JSONException e) {
            value = 0;
        }
        return value;
    }


    public static long getLong(JSONObject jsonObject, String name) {
        long value = 0;
        try {
            if (jsonObject != null) {
                value = jsonObject.getLong(name);
            }
        } catch (JSONException e) {
            value = 0;
        }
        return value;
    }


    public static JSONObject getJSONObject(JSONArray jsonArray, int i) {
        JSONObject value = new JSONObject();
        try {
            if (jsonArray != null) {
                value = jsonArray.getJSONObject(i);
            }
        } catch (JSONException e) {
        }
        return value;
    }

    public static JSONObject getJSONObject(JSONObject jsonObject, String name) {
        JSONObject value = new JSONObject();
        try {
            if (jsonObject != null) {
                value = jsonObject.getJSONObject(name);
            }
        } catch (JSONException e) {
        }
        return value;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject, String name) {
        JSONArray value = new JSONArray();
        try {
            if (jsonObject != null) {
                value = jsonObject.getJSONArray(name);
            }
        } catch (JSONException e) {
        }
        return value;
    }


    public static String getString(JSONObject jsonObject, String name1, String name2) {
        return getString(getJSONObject(jsonObject, name1), name2);
    }


    public static void setString(JSONObject jsonObject, String name, String value) {
        try {
            jsonObject.put(name, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
    }

    public static void setJsonObject(JSONObject jsonObject, String name, JSONObject value) {
        try {
            jsonObject.put(name, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
    }

    public static void setDouble(JSONObject jsonObject, String name, Double value) {
        try {
            jsonObject.put(name, value);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
        }
    }

    public static void setJsonObject(JSONArray jsonArray, JSONObject value) {
        jsonArray.put(value);
    }

    public static void setJsonObject(JSONArray jsonArray, int index, JSONObject value) {
        try {
            jsonArray.put(index, value);
        } catch (JSONException e) {
        }
    }

    public static String getJArrayName(JSONArray jsonArray, String code) {
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jo = getJSONObject(jsonArray, i);
                String key = jo.keys().next();
                if (code.equals(key)) {
                    return jo.getString(key);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static String getSlectCode(Object selectItem) {
        if (selectItem instanceof JSONObject) {
            return ((JSONObject) selectItem).keys().next();
        }
        return selectItem.toString();
    }


    public static List<JSONObject> jsonArr2List(JSONArray jsonArray) {
        List<JSONObject> deptList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jo = getJSONObject(jsonArray, i);
            deptList.add(jo);
        }
        return deptList;
    }
}
