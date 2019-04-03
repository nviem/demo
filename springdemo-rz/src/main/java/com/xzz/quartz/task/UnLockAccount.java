package com.xzz.quartz.task;

import com.xzz.entity.SysUser;
import com.xzz.service.SysUserService;
import com.xzz.utils.Lg;
import com.xzz.utils.SysConstant;
import org.springframework.stereotype.Component;

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
 * @Date: 2019/4/1 0001
 * @Time: 下午 17:33
 */
@Component(value = "unLockAccount")
public class UnLockAccount {

    @Resource
    private SysUserService sysUserService;

    public void unLock(){
        Lg.log("解封账户！");

        List<SysUser> list = sysUserService.findLockAccount();
        for (SysUser sysUser : list) {
            Date date = sysUser.getLockdate();
            Date now = new Date();
            long time = now.getTime()-date.getTime();
            long day = time/(1000*60*60*24);

            if(day>=3){
                Lg.log("准备解封账户！");
                SysUser user = new SysUser();
                user.setUserId(sysUser.getUserId());
                user.setStatus(SysConstant.ScheduleStatus.PAUSE.getValue());
                sysUserService.unLockAccount(user);
                Lg.log("解封账户成功");
            }else{
                Lg.log("-->未到解封时间");
            }
        }
    }
}
