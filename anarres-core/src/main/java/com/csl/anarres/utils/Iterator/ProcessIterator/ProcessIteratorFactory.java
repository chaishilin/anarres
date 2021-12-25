package com.csl.anarres.utils.Iterator.ProcessIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/25 22:38
 * @Description:
 */
@Component
public class ProcessIteratorFactory {
    private final Map<String, ProcessIterator> processIteratorMap = new ConcurrentHashMap<>();
    @Autowired
    public ProcessIteratorFactory(Map<String, ProcessIterator> processIteratorMap){
        processIteratorMap.forEach(this.processIteratorMap::put);
    }

    /**
     * 有参数构造
     * @param className
     * @param workingList
     * @return
     */
    public ProcessIterator getProcessIterator(String className, List<?> workingList){
        ProcessIterator processIterator = getProcessIterator(className);
        processIterator.setWorkingList(workingList);
        return processIterator;
    }

    /**
     * 无初始参数
     * @param className
     * @return
     */
    public ProcessIterator getProcessIterator(String className){
        switch (className){
            case "NumberGenerateProcess":
                return processIteratorMap.get("NumberGenetateIterator");
            case "TemplateTestProcess":
                return processIteratorMap.get("TemplateTestIterator");
            default:
                throw new RuntimeException("不支持的ProcessIterator : "+className);
        }

    }
}
