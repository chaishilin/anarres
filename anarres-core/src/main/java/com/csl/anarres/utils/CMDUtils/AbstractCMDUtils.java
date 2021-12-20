package com.csl.anarres.utils.CMDUtils;

import com.csl.anarres.enums.OsType;
import com.csl.anarres.exception.RunProgramException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @author: Shilin Chai
 * @Date: 2021/10/31 21:46
 * @Description: 对于不同操作系统有不同的具体操作，因此CMDUtils可以作为抽象类，各不同操作系统的CMD命令为子类
 * 该类为抽象类，不能直接生成对象，避免了直接调用父类的execCMD
 */
public abstract class AbstractCMDUtils {
    /**
     * 运行命令行指令，该函数与操作系统类型无关
     * @param path
     * @param command
     * @return
     */
    public final String execCMD(String path, String command){
        StringBuilder result = new StringBuilder();
        StringBuilder errorResult = new StringBuilder();
        try {
            Process process = cmdProcess(path,command);
            if(!process.waitFor(10000, TimeUnit.MILLISECONDS)){
                process.destroy();
                //实际上还是无法kill cmd生成的子进程，windows 环境下放弃杀死子进程了（反正也不拿windows当服务器）
                //linux环境下，通过shell中调用 timeout 10 java Solution，来控制超时。
                throw new RunProgramException("cmd:程序运行超时");
            }
            //如果报错，获得其报错信息
            BufferedReader bufferedErrorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(),"GBK"));
            String errorLine = null;
            while ((errorLine = bufferedErrorReader.readLine()) != null){
                errorResult.append(errorLine).append("\n");
            }

            if(!"".equals(errorResult.toString())) {
                throw new RunProgramException(errorResult.toString());
            }
            //如果不报错，获得其输出
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(),"GBK"));
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                result.append(line).append("\n");
            }
            return result.toString();
        }catch (Exception e){
            e.printStackTrace();
            throw new RunProgramException(e.getMessage());
        }
    }
    /**
     * 运行命令行的具体指令
     * 需要子类覆盖
     * @param path
     * @param command
     * @return
     * @throws IOException
     */
    protected abstract Process cmdProcess(String path, String command) throws IOException;

    /**
     * 获得操作系统类型
     * @return
     */
    public abstract OsType systemType();
}
