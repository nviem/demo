package com.xzz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzz.entity.ScheduleJob;
import com.xzz.entity.ScheduleJobExample;
import com.xzz.mapper.ScheduleJobMapper;
import com.xzz.service.ScheduleService;
import com.xzz.utils.*;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

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
 * @Date: 2019/3/30 0030
 * @Time: 下午 17:30
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    private ScheduleJobMapper scheduleJobMapper;

    @Resource
    private Scheduler scheduler;

    @Override
    public ResultData scheduleList(Pager pager, String search) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        ScheduleJobExample example = new ScheduleJobExample();
        if (StringUtils.isNotEmpty(search)){
            ScheduleJobExample.Criteria criteria = example.createCriteria();
            criteria.andBeanNameLike("%"+search+"%");
        }
        List<ScheduleJob> list = scheduleJobMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        ResultData resultData = new ResultData(info.getTotal(),info.getList());
        return resultData;
    }

    @Override
    public R save(ScheduleJob scheduleJob) {
        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());
        scheduleJob.setCreateTime(new Date());
        int i = scheduleJobMapper.insert(scheduleJob);
//2,真正定时任务的创建
        ScheduleUtils.createScheduleTask(scheduler,scheduleJob);

        return i>0?R.OK():R.error();
    }

    @Override
    public R update(ScheduleJob scheduleJob) {

        int i = scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
//2,修改真正的定时任务
        ScheduleUtils.updateScheduleTask(scheduler,scheduleJob);
        return i>0?R.OK():R.error();
    }

    @Override
    public R delete(List<Long> jobIds) {

        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);
        int  i = scheduleJobMapper.deleteByExample(example);
//2,删除真正的定时任务
        for (Long jobId : jobIds) {
            ScheduleUtils.deleteScheduleTask(scheduler,jobId);
        }
        return i>0?R.OK():R.error();
    }

    //暂停
    @Override
    public R pause(List<Long> jobIds) {
        ScheduleJobExample example = new ScheduleJobExample();

        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);

        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.PAUSE.getValue());

        int i = scheduleJobMapper.updateByExampleSelective(scheduleJob,example);

        //2,真正暂停任务
        for (Long jobId : jobIds) {
            ScheduleUtils.pause(scheduler,jobId);
        }

        return i>0?R.OK():R.error();
    }

    //恢复
    @Override
    public R resume(List<Long> jobIds) {

        ScheduleJobExample example = new ScheduleJobExample();
        ScheduleJobExample.Criteria criteria = example.createCriteria();
        criteria.andJobIdIn(jobIds);
        ScheduleJob scheduleJob = new ScheduleJob();
        scheduleJob.setStatus(SysConstant.ScheduleStatus.NOMAL.getValue());
        int i =scheduleJobMapper.updateByExampleSelective(scheduleJob,example);

        //2,真正恢复任务
        for (Long jobId : jobIds) {
            ScheduleUtils.resume(scheduler,jobId);
        }

        return i>0?R.OK():R.error();
    }

    //立即执行
    @Override
    public R run(List<Long> jobIds) {

        for (Long jobId:jobIds){
            ScheduleUtils.runOnce(scheduler,jobId);
        }
        return R.OK();
    }

    @Override
    public R scheduleInfo(long id) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectByPrimaryKey(id);
        return R.OK().put("scheduleJob",scheduleJob);
    }
}
