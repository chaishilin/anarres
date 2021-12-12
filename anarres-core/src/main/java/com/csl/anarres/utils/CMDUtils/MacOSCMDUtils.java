package com.csl.anarres.utils.CMDUtils;

import com.csl.anarres.enums.OsType;
import org.springframework.stereotype.Component;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 21:01
 * @Description:
 */
@Component
public class MacOSCMDUtils extends LinuxCMDUtils {

    @Override
    public OsType systemType() {
        return OsType.MacOs;
    }
}
