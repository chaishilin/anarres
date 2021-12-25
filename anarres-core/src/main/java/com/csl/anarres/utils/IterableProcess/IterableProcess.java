package com.csl.anarres.utils.IterableProcess;

import com.csl.anarres.utils.Iterator.ProcessIterator.ProcessIteratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/25 21:42
 * @Description: 可返回迭代器的任务集合
 */
@Component
public abstract class IterableProcess implements Iterable {
    private List<?> workingList;

    protected void setWorkingList(List<?> workingList){
        this.workingList = workingList;
    }

    @Autowired
    private ProcessIteratorFactory iteratorFactory;

    /**
     * 用工厂函数生产带拥有workingList的ProcessIterator
     * @return
     */
    public Iterator iterator() {
        return iteratorFactory.getProcessIterator(getClassName(),workingList);
    }

    /**
     * 根据输入参数获得workingList
     * @param params
     */
    protected abstract void generateWorkingList(Object params);

    /**
     * 获得子类类名
     * @return
     */
    protected abstract String getClassName();
}
