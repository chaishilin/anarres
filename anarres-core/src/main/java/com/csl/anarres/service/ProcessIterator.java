package com.csl.anarres.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/21 9:52
 * @Description:
 */
public abstract class ProcessIterator implements Iterator {
    private int index;
    private List<? extends Object> workingList = new ArrayList<>();

    public ProcessIterator(){
        setWorkingList();
    }

    @Override
    public boolean hasNext(){
        return index<getWorkingList().size();
    }

    /**
     * 返回处理进度
     * @return
     */
    @Override
    public String next() {
        List<Object> workingList = getWorkingList();
        Object result = workingList.get(index);
        index++;
        process(result);
        int rate = index*100/workingList.size();
        return rate+"";
    }

    /**
     * 获得任务队列
     * @return
     */
    public List<Object> getWorkingList(){
        return new ArrayList<>(this.workingList);//返回一份拷贝
    }
    /**
     * 设置任务队列
     * @param
     */
    private void setWorkingList(){
        this.workingList = buildWorkingList();
    }

    /**
     * 生成任务队列
     * @return
     */
    public abstract List<? extends Object> buildWorkingList();
    /**
     * 迭代时对每个元素的处理
     * @param o
     */
    public abstract void process(Object o);
}
