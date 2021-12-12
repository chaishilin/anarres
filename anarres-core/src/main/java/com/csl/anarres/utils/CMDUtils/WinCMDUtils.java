package com.csl.anarres.utils.CMDUtils;

import com.csl.anarres.enums.OsType;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 17:28
 * @Description:
 */
@Component("win")
public class WinCMDUtils extends AbstractCMDUtils {

    protected Process cmdProcess(String path, String command) throws IOException {
        command = "cmd.exe /c " + "cd " + path + "&&" + command;
        return Runtime.getRuntime().exec(command);
    }

    @Override
    public OsType systemType() {
        return OsType.Windows;
    }
}
