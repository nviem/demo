package com.xzz.raelm;

import com.xzz.entity.SysUser;
import com.xzz.service.SysMenuService;
import com.xzz.service.SysRoleService;
import com.xzz.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
 * @Date: 2019/3/27 0027
 * @Time: 下午 19:36
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysMenuService sysMenuService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SysUser sysUser = (SysUser) principals.getPrimaryPrincipal();

        List<String> roles = sysRoleService.findRoleByUserId(sysUser.getUserId());

        List<String> perms = sysMenuService.findPermsByUserId(sysUser.getUserId());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(perms);
        info.addRoles(roles);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken u = (UsernamePasswordToken) token;

        String uname = u.getUsername();
        String pass = new String(u.getPassword());

        SysUser sysUser = sysUserService.findByUname(uname);
        if(sysUser==null){
            throw new UnknownAccountException("账号错误");
        }
        if(!sysUser.getPassword().equals(pass)){
            throw new IncorrectCredentialsException("密码错误");
        }
        if(sysUser.getStatus()==0){
            throw new LockedAccountException("账号冻结");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser,pass,this.getName());

        return info;
    }
}
