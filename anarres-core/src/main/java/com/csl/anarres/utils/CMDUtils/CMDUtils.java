package com.csl.anarres.utils.CMDUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 19:07
 * @Description:
 */
@Component
public class CMDUtils {
    private final Map<String, AbstractCMDUtils> cmdUtilsMap = new ConcurrentHashMap<>();

    /**
     * Autowired当使用在Collection里时，会将所申明类的所有实现类都放在那个指定的Collection里；
     * 该函数将所有实现了AbstractCMDUtils的子类都放在了cmdUtilsMap里面，Map中的String为component的value
     * @param cmdUtilsMap
     */
    @Autowired
    public CMDUtils(Map<String, AbstractCMDUtils> cmdUtilsMap){
        cmdUtilsMap.forEach(this.cmdUtilsMap::put);
    }

    /**
     * 工厂函数，根据不同的操作系统，返回不同的操作系统CMDUtils子类实例
     * @return
     */
    public AbstractCMDUtils createInstance(){
        String osName = System.getProperty("os.name");
        if(osName.contains("win") || osName.contains("Win")){
            return cmdUtilsMap.get("win");
        }else{
            return cmdUtilsMap.get("linux");
        }
    }

}
