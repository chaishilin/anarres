package com.csl.anarres.Iterators;

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
    private boolean workingListInited = false; //是否已经生成过workingList
    private List<? extends Object> workingList;

    @Override
    public boolean hasNext(){
        if(!this.workingListInited){
            //首次要初始化workingList
            setWorkingList();
        }
        if(index<getWorkingList().size()){
            return true;
        }else{
            //当一次循环结束时，初始化Iterator状态
            index = 0;
            workingListInited = false;
            return false;
        }
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
        this.workingListInited = true;
    }

    /**
     * 生成任务队列
     * @return 任务队列
     */
    public abstract List<? extends Object> buildWorkingList();
    /**
     * 迭代时对每个元素的处理
     * @param o
     */
    public abstract void process(Object o);
}
