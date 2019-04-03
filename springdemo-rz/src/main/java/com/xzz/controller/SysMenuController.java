package com.xzz.controller;

import com.xzz.entity.SysMenu;
import com.xzz.log.MyLog;
import com.xzz.service.SysMenuService;
import com.xzz.utils.Pager;
import com.xzz.utils.R;
import com.xzz.utils.ResultData;
import com.xzz.utils.Sorter;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

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
 * @Date: 2019/3/26 0026
 * @Time: 下午 12:02
 */
@RestController
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;

    @MyLog(value = "查询菜单列表",description = "分页查询并且按照名称查询菜单详情")
    @RequiresPermissions("sys:menu:list")
    @RequestMapping("/sys/menu/list")
    public ResultData menuList(Pager pager, String search, Sorter sorter){
        return sysMenuService.findByPage(pager,search,sorter);
    }

    @MyLog(value = "删除菜单",description = "根据菜单编号删除菜单")
    @RequiresPermissions("sys:menu:delete")
    @RequestMapping("/sys/menu/del")
    public R del(@RequestBody List<Long> ids){

        return sysMenuService.del(ids);
    }

    @MyLog(value = "查询菜单和目录",description = "查询菜单和目录")
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/select")
    public R selectMenu(){
        return sysMenuService.selectMenu();
    }

    @MyLog(value = "新增菜单、目录、按钮",description = "新增菜单、目录、按钮")
    @RequiresPermissions("sys:menu:save")
    @RequestMapping("/sys/menu/save")
    public R saveMenu(@RequestBody SysMenu sysMenu){
        return sysMenuService.saveMenu(sysMenu);
    }

    @MyLog(value = "查询菜单",description = "查询菜单")
    @RequiresPermissions("sys:menu:select")
    @RequestMapping("/sys/menu/info/{menuId}")
    public R findmenu(@PathVariable long menuId){
        return sysMenuService.findMenu(menuId);
    }

    @MyLog(value = "修改菜单",description = "根据用户编号修改菜单")
    @RequiresPermissions("sys:menu:update")
    @RequestMapping("/sys/menu/update")
    public R update(@RequestBody SysMenu sysMenu){
        return sysMenuService.update(sysMenu);
    }

    @MyLog(value = "查询用户能访问的菜单",description = "根据用户编号查询用户能访问的菜单")
    @RequestMapping("/sys/menu/user")
    public R menuUser(){

        return sysMenuService.findUserMenu();
    }

}
