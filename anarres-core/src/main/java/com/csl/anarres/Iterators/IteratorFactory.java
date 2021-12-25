package com.csl.anarres.Iterators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/25 17:41
 * @Description:
 */
@Component
public class IteratorFactory {
    private final Map<String, ProcessIterator> processIteratorMap = new ConcurrentHashMap<>();
    @Autowired
    public IteratorFactory(Map<String, ProcessIterator> processIteratorMap){
        processIteratorMap.forEach(this.processIteratorMap::put);
    }

    public ProcessIterator getIterator(String name){
        switch (name){
            case "NumberGenetate":
                return processIteratorMap.get("NumberGenetate");
            case "TemplateTest":
                return processIteratorMap.get("TemplateTest");
            default:
                throw new RuntimeException("不支持的ProcessIterator！");
        }

    }
}
