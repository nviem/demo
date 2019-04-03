package com.xzz.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;

import javax.sql.DataSource;
import java.util.Properties;

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
 * @Time: 下午 20:00
 */
@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier(value="druidDatasource")DataSource dataSource){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        Properties p = new Properties();
        p.setProperty("org.quartz.scheduler.instanceName","MyQuartzScheduler");
        p.setProperty("org.quartz.scheduler.instanceid","AUTO");
        p.setProperty("org.quartz.threadPool.class","org.quartz.simpl.SimpleThreadPool");
        p.setProperty("org.quartz.threadPool.threadCount","10");
        p.setProperty("org.quartz.jobStore.class","org.quartz.impl.jdbcjobstore.JobStoreTX");
        p.setProperty(" org.quartz.jobStore.tablePrefix","QRTZ_");
        schedulerFactoryBean.setQuartzProperties(p);

        schedulerFactoryBean.setAutoStartup(true);

        schedulerFactoryBean.setOverwriteExistingJobs(true);

        schedulerFactoryBean.setStartupDelay(5);

        schedulerFactoryBean.setDataSource(dataSource);

        return schedulerFactoryBean;

    }

}
