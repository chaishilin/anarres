//package com.csl.anarres.aspect.CacheAspect;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.csl.anarres.dto.ProgramDto;
//import com.csl.anarres.utils.ArgsUtil;
//import com.csl.anarres.utils.JoinPointUtil;
//import com.csl.anarres.utils.LoginUtil;
//import com.csl.anarres.utils.RedisUtil;
//import com.csl.anarres.utils.ResponseUtil;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.lang.reflect.Method;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author: Shilin Chai
// * @Date: 2021/11/23 16:18
// * @Description:
// */
////@Aspect
////@Component
//public class CacheAspectProgramImpl extends CacheAspectTemplate {
//    //这种大东西不适合放在缓存，因为查询逻辑复杂，不好缓存
//    @Autowired
//    private LoginUtil loginUtil;
//
//    @Override
//    public Object paserFromCache(String path, ProceedingJoinPoint joinPoint, Method method) {
//        if (method.getName().equals("programList")) {
//            //对于programList方法
//            Object cacheResult = null;
//            String[] args = path.split("\\.");
//            String userId = loginUtil.getCurrentUserOrPublic().getUserId();
//            cacheResult = RedisUtil.hgetFromPath(userId, path);
//
//            if (cacheResult != null) {
//                //如果有缓存，则在该子类中解析缓存中数据
//                JSONObject requestBody = ArgsUtil.paserJSONObject(method, joinPoint.getArgs());
//                String programId = requestBody.getString("programId");
//
//                if ("".equals(programId) || programId == null) {
//                    //如果不指定特定的programId
//                    //返回程序列表
//                    logger.info("从缓存中响应programList请求");
//                    return ResponseUtil.success(cacheResult);
//                } else {
//                    //返回特定的程序
//                    logger.info("从缓存中响应programId请求");
//                    List<ProgramDto> programDtoList = JSONArray.parseArray(cacheResult.toString(), ProgramDto.class);
//                    List<ProgramDto> result = programDtoList.stream().filter(
//                            p ->programId.equals(p.getProgramId())).collect(Collectors.toList());
//                    return ResponseUtil.success(result);
//                }
//            } else {
//                //如果无缓存，则继续响应
//                return JoinPointUtil.doRequest(joinPoint);
//            }
//        } else {
//            //不属于自己的不进行处理
//            return JoinPointUtil.doRequest(joinPoint);
//        }
//    }
//}
