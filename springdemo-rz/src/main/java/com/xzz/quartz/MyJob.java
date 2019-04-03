package com.xzz.quartz;

import com.alibaba.fastjson.JSON;
import com.xzz.entity.ScheduleJob;
import com.xzz.entity.ScheduleJobLog;
import com.xzz.service.ScheduleJobLogService;
import com.xzz.utils.Lg;
import com.xzz.utils.SpringContextUtils;
import com.xzz.utils.StringUtils;
import com.xzz.utils.SysConstant;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

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
 * @Date: 2019/3/31 0031
 * @Time: 下午 18:44
 */
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ScheduleJobLog log = new ScheduleJobLog();
        long start = System.currentTimeMillis();
        try{

            String json = (String)context.getJobDetail().getJobDataMap().get(SysConstant.SCHEDULE_DATA_KEY);
            ScheduleJob scheduleJob = JSON.parseObject(json,ScheduleJob.class);
            String beanName = scheduleJob.getBeanName();
            String methodName = scheduleJob.getMethodName();
            String params = scheduleJob.getParams();

            Object object = SpringContextUtils.getBean(beanName);
            Class clazz = object.getClass();
            Method method = null;
            if(StringUtils.isEmpty(params)){
                method = clazz.getDeclaredMethod(methodName);
                method.invoke(object);
            }else{
                method = clazz.getDeclaredMethod(methodName,String.class);
                method.invoke(object,params);
            }
            log.setBeanName(beanName);
            log.setMethodName(methodName);
            log.setJobId(scheduleJob.getJobId());
            log.setCreateTime(new Date());
            log.setParams(params);
            log.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());

        }catch(Exception e){
            e.printStackTrace();
            log.setError(e.getMessage());
        }
        long end = System.currentTimeMillis();
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService)SpringContextUtils.getBean("scheduleJobLogServiceImpl");
        log.setTimes(end-start);
        scheduleJobLogService.insertLog(log);
        Lg.log("定时任务日志记录成功！");
    }
}
