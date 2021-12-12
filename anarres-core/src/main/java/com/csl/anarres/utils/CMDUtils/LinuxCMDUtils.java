package com.csl.anarres.utils.CMDUtils;

import com.csl.anarres.enums.OsType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 17:28
 * @Description:
 */
@Component("linux")
public class LinuxCMDUtils extends AbstractCMDUtils {
    @Override
    protected Process cmdProcess(String path, String command) throws IOException {
        command = "cd " + path + " && " + command;
        return Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command});
    }

    @Override
    public OsType systemType() {
        return OsType.Linux;
    }

}
