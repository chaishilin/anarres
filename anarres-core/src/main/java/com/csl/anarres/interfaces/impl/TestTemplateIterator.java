package com.csl.anarres.interfaces.impl;

import com.csl.anarres.service.ProcessIterator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/21 9:58
 * @Description:
 */
@Service
public class TestTemplateIterator extends ProcessIterator {


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
