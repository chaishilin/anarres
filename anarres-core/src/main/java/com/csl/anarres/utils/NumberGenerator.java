package com.csl.anarres.utils;

import com.csl.anarres.entity.TableNumGeneratorEntity;
import com.csl.anarres.enums.TableIdEnum;
import com.csl.anarres.mapper.TableNumGeneratorMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Shilin Chai
 * @Date: 2021/11/3 9:28
 * @Description:
 */
@Component
public class NumberGenerator {
    static final int offset = 3;
    static int generatorCount = 0;


    @PostConstruct
    public void scanTable(){
        System.out.println("auto scan table enum");
        for(TableIdEnum item : TableIdEnum.values()){
            RedisUtil.getInstance().del("NumGenerator_"+item.getName());
            TableNumGeneratorEntity entity = mapper.selectById(item.getCode());
            if(entity == null){
                entity = new TableNumGeneratorEntity();
                entity.setTableName(item.getName());
                entity.setCode(item.getCode());
                entity.setCount(1);
                entity.setCreateTime(new Date());
                entity.setLastModifiedTime(new Date());
                mapper.insert(entity);
            }
        }
    }

    @Autowired
    private TableNumGeneratorMapper mapper;
    public static String getIdFromTableId(TableIdEnum tableId){
        //todo 多机分布式系统id生成器
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tableId.getCode());
        stringBuilder.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        stringBuilder.append(String.format("%05d",RedisUtil.getInstance().incr(stringBuilder.toString())));
        return stringBuilder.toString();
    }

    //todo 拿到的count如何存，如何对比自己当前count有没有到，要不要去取，是不是要static map
    public String getId(TableIdEnum tableId){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tableId.getCode());
        stringBuilder.append(new SimpleDateFormat("yyyyMMdd").format(new Date()));
        String countStr = RedisUtil.getInstance().get("NumGenerator_"+tableId.getName());//取出这次要用的count
        if(countStr == null){
            //如果redis中还没有关于该table的count记录
            getTableNumCount(tableId,offset);//申请count区间
            countStr = RedisUtil.getInstance().get("NumGenerator_"+tableId.getName());//再次取出这次要用的count
        }
        int count = Integer.parseInt(countStr);
        stringBuilder.append(countStr);
        generatorCount += 1;
        if(generatorCount%offset == 0){
            //如果取模为0，说明一个offset用完了，需要再去拿一个
            //删除redis中储存的count
            RedisUtil.getInstance().del("NumGenerator_"+tableId.getName());
        }else{
            RedisUtil.getInstance().set("NumGenerator_"+tableId.getName(),""+(count+1));//存入下次要用的count
        }
        System.out.println(count);
        return countStr;
    }



    @Transactional
    private void getTableNumCount(TableIdEnum tableId, int offset){
        //System.out.println("getTableNumCount "+tableId.getName());
        TableNumGeneratorEntity entity = mapper.selectById(tableId.getCode());
        assert entity != null;
        int count;
        if(DateUtils.isSameDay(entity.getLastModifiedTime(),new Date())){
            //如果是同一天，则继续当前偏移量
            count = entity.getCount();
        }else{
            //如果是新的一天，设置count为1，重新开始
            count = 1;
        }
        entity.setCount(count+offset);//一次取offset个编号区间
        entity.setLastModifiedTime(new Date());
        mapper.updateById(entity);
        RedisUtil.getInstance().set("NumGenerator_"+tableId.getName(),""+count);
    }

}
