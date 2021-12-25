package com.csl.anarres.utils.Iterator.ProcessIterator;

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
    private List<? extends Object> workingList;

    public void setWorkingList(List<?> workingList){
        this.workingList = workingList;
    }

    @Override
    public boolean hasNext(){
        //因为从工厂中返回的都是同一个 ProcessIterator ，因此循环结束一次后需要修改其index，让其从头开始循环
        if(index < this.workingList.size()){
            return true;
        }else {
            //当循环结束时，设index为0，从头开始
            index = 0;
            return false;
        }
    }

    /**
     * 返回处理进度
     * @return
     */
    @Override
    public String next() {
        Object result = this.workingList.get(index);
        index++;
        process(result);
        int rate = index*100/this.workingList.size();
        return rate+"";
    }

    /**
     * 获得任务队列
     * @return
     */
    private List<Object> getWorkingList(){
        return new ArrayList<>(this.workingList);//返回一份拷贝
    }
    /**
     * 迭代时对每个元素的处理
     * @param o
     */
    public abstract void process(Object o);
}
