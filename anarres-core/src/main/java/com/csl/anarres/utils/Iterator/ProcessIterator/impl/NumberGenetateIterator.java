package com.csl.anarres.utils.Iterator.ProcessIterator.impl;

import com.csl.anarres.utils.Iterator.ProcessIterator.ProcessIterator;
import org.springframework.stereotype.Component;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/21 9:58
 * @Description:
 */
@Component(value = "NumberGenetateIterator")
public class NumberGenetateIterator extends ProcessIterator {


    @Override
    public void process(Object o) {
        try {
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
