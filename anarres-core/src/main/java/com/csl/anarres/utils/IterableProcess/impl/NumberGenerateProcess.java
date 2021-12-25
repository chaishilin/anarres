package com.csl.anarres.utils.IterableProcess.impl;

import com.csl.anarres.utils.IterableProcess.IterableProcess;
import com.csl.anarres.utils.Iterator.ProcessIterator.ProcessIteratorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/25 21:54
 * @Description:
 */
@Component
public class NumberGenerateProcess extends IterableProcess {
    @Autowired
    private ProcessIteratorFactory iteratorFactory;

    @Override
    public void generateWorkingList(Object o) {
        List<Integer> result = new ArrayList<>();
        for(int i = 0 ; i < 50;i++){
            result.add(i);
        }
        super.setWorkingList(result);
    }

    @Override
    protected String getClassName() {
        return this.getClass().getSimpleName();
    }
}
