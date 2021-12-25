package com.csl.anarres.Iterators.impl;

import com.csl.anarres.Iterators.ProcessIterator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/21 9:58
 * @Description:
 */
@Component(value = "NumberGenetate")
public class NumberGenetateIterator extends ProcessIterator {


    @Override
    public List<? extends Object> buildWorkingList() {
        List<String> temp = new ArrayList<>();
        for(int i = 0 ; i < 68;i++){
            temp.add(i+"");
        }
        return temp;
    }

    @Override
    public void process(Object o) {
        try {
            Thread.sleep(100);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
