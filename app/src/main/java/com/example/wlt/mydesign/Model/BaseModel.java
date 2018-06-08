package com.example.wlt.mydesign.Model;

/**
 * Created by 25122 on 2018/6/7.
 */

public abstract class BaseModel<T> {

    //数据请求参数
    protected String[] params;

    /**
     * 请求参数
     * @param arg
     * @return
     */
    public BaseModel params(String...arg){
        params = arg;
        return this;
    }

    public abstract void execute();
}
