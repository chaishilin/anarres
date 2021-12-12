package com.csl.anarres.utils.CMDUtils;

import com.csl.anarres.entity.UserEntity;
import com.csl.anarres.enums.OsType;
import com.csl.anarres.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author: Shilin Chai
 * @Date: 2021/12/12 17:28
 * @Description:
 */
@Component("win")
public class WinCMDUtils extends AbstractCMDUtils {
    @Autowired
    private UserMapper mapper;

    protected Process cmdProcess(String path, String command) throws IOException {
        System.out.println(mapper);
        List<UserEntity> entities = mapper.userInfoList();
        entities.forEach(System.out::println);
        command = "cmd.exe /c " + "cd " + path + "&&" + command;
        return Runtime.getRuntime().exec(command);
    }

    @Override
    public OsType systemType() {
        return OsType.Windows;
    }
}
