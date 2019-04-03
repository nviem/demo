package com.xzz.log;

import com.alibaba.fastjson.JSON;
import com.xzz.entity.SysLog;
import com.xzz.service.SysLogService;
import com.xzz.utils.HttpContextUtils;
import com.xzz.utils.IPUtils;
import com.xzz.utils.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                       / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                      \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //                ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                 \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                            `=---='
 * //
 * //         .............................................
 * //                  佛祖镇楼                  BUG辟易
 * //          佛曰:
 * //                  写字楼里写字间，写字间里程序员；
 * //                  程序人员写程序，又拿程序换酒钱。
 * //                  酒醒只在网上坐，酒醉还来网下眠；
 * //                  酒醉酒醒日复日，网上网下年复年。
 * //                  但愿老死电脑间，不愿鞠躬老板前；
 * //                  奔驰宝马贵者趣，公交自行程序员。
 * //                  别人笑我忒疯癫，我笑自己命太贱；
 * //                  不见满街漂亮妹，哪个归得程序员？
 *
 * @Description:
 * @Company:
 * @Author: jzx
 * @Date: 2019/3/29 0029
 * @Time: 下午 20:52
 */
@Aspect
@Component
public class MyLogAspect {

    @Resource
    private SysLogService sysLogService;

    @Pointcut(value = "@annotation(com.xzz.log.MyLog)")
    public void myPointcut(){}

    @AfterReturning(pointcut = "myPointcut()")
    public void after(JoinPoint joinPoint){
        SysLog sysLog = new SysLog();
        String uname = ShiroUtils.getSysUser().getUsername();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyLog mylog = method.getAnnotation(MyLog.class);
        String opration = mylog.value();
        String methodName = joinPoint.getTarget().getClass()+"."+joinPoint.getSignature().getName();
        String params = JSON.toJSONString(joinPoint.getArgs());
        String ip = IPUtils.getIpAddr(HttpContextUtils.getHttpServletRequest());

        sysLog.setIp(ip);
        sysLog.setCreateDate(new Date());
        sysLog.setMethod(methodName);
        sysLog.setParams(params);
        sysLog.setUsername(uname);
        sysLog.setOperation(opration);

        int i = sysLogService.saveSysLog(sysLog);
        System.out.println(i>0?"保存日志成功":"失败");

    }
}
