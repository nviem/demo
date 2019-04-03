package com.xzz.controller;

import com.google.code.kaptcha.Producer;
import com.xzz.dto.SysUserDTO;
import com.xzz.entity.SysUser;
import com.xzz.log.MyLog;
import com.xzz.service.SysUserService;
import com.xzz.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
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
 * @Date: 2019/3/25 0025
 * @Time: 下午 21:16
 */
@RestController
public class SysUserController {

    @Resource
    private SysUserService sysUserService;
    @Resource
    private Producer producer;

    @RequestMapping("/findAll")
    public List<SysUser> findAll(){
        return sysUserService.findAll();
    }

    @RequestMapping("/sys/login")
    public R login(@RequestBody SysUserDTO sysUser){

        String code = ShiroUtils.getCaptcha();

        String c = sysUser.getCaptcha();
        if(c!=null&&!code.equalsIgnoreCase(c)){
            return R.error("验证码错误！！");
        }
        //return sysUserService.login(sysUser);

        String s = null;
        try{
            Subject subject = SecurityUtils.getSubject();

            String pwd = sysUser.getPassword();
            Md5Hash md5Hash = new Md5Hash(pwd,sysUser.getUsername(),1024);
            pwd = md5Hash.toString();
            UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getUsername(),pwd);

            if(sysUser.isRememberMe()){
                token.setRememberMe(true);
            }

            subject.login(token);

            return R.OK();
        }catch(Exception e){
            s = e.getMessage();
        }
        return R.error(s);
    }

    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response){
        try{
            String text = producer.createText();

            ShiroUtils.setAttribute("code",text);

            BufferedImage bufferedImage = producer.createImage(text);
            OutputStream os = response.getOutputStream();
            ImageIO.write(bufferedImage,"jpg",os);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @MyLog(value = "查询用户信息",description = "显示用户详情")
    @RequestMapping("/sys/user/info")
    public R userInfo(){
        //SecurityUtils.getSubject().getPrincipal();
        SysUser user = ShiroUtils.getSysUser();
        return R.OK().put("user",user);
    }

    @MyLog(value = "用户列表",description = "分页显示用户详情")
    @RequiresPermissions("sys:user:list")
    @RequestMapping("/sys/user/list")
    public ResultData menuList(Pager pager, String search, Sorter sorter){
        return sysUserService.findByPage(pager, search, sorter);
    }


    @RequiresPermissions("sys:user:delete")
    @RequestMapping("/sys/user/del")
    public R del(@RequestBody List<Long> ids){
        return sysUserService.del(ids);
    }


}
