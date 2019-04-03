package com.xzz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzz.entity.SysMenu;
import com.xzz.entity.SysMenuExample;
import com.xzz.mapper.SysMenuMapper;
import com.xzz.service.SysMenuService;
import com.xzz.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * @Time: 下午 12:11
 */
@Service(value = "sysMenuServiceImpl")
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public ResultData findByPage(int limit, int offset) {
        PageHelper.offsetPage(offset,limit);
        List<SysMenu> list = sysMenuMapper.selectByExample(null);
        PageInfo info = new PageInfo(list);
        List<SysMenu> l = info.getList();
        long total = info.getTotal();
        return new ResultData(total,l);
    }

    @Override
    public ResultData findByPage(int limit, int offset, String search) {
        PageHelper.offsetPage(offset,limit);
        SysMenuExample example =null;
        if(search!=null&&!search.equals("")){
            example = new SysMenuExample();
            SysMenuExample.Criteria criteria = example.createCriteria();
            criteria.andNameLike("%"+search+"%");
        }
        List<SysMenu> list = sysMenuMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        List<SysMenu> l = info.getList();
        long total = info.getTotal();
        return new ResultData(total,l);
    }

    @Override
    public ResultData findByPage(Pager pager, String search,Sorter sorter) {
        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria criteria = example.createCriteria();
        if(search!=null&&!search.equals("")){
            criteria.andNameLike("%"+search+"%");
        }
        if(sorter.getSort()!=null&&sorter.getSort().equals("menuId")){
            example.setOrderByClause("menu_Id "+sorter.getOrder());
        }

        List<SysMenu> list = sysMenuMapper.selectByExample(example);
        PageInfo info = new PageInfo(list);
        List<SysMenu> l = info.getList();
        long total = info.getTotal();
        return new ResultData(total,l);
    }

    @Override
    public R del(List<Long> ids) {
        SysMenuExample example = new SysMenuExample();
        SysMenuExample.Criteria criteria = example.createCriteria();

        for(Long id:ids){
            if(id<28){
                return R.error("系统菜单，不能删除！请核对");
            }
        }
        criteria.andMenuIdIn(ids);

        int i = sysMenuMapper.deleteByExample(example);
        if (i>0) {
            return R.OK();
        }
            return R.error("删除失败！！");
    }

    @Override
    public R selectMenu() {

        List<SysMenu> list = sysMenuMapper.findMenuNotButton();
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuId(0l);
        sysMenu.setParentId(-1l);
        sysMenu.setName("一级目录");
        sysMenu.setOrderNum(0);
        list.add(sysMenu);
        return R.OK().put("menuList",list);
    }

    @Override
    public R saveMenu(SysMenu sysMenu) {
        int i = sysMenuMapper.insert(sysMenu);
        return i>0?R.OK("新增成功!!!"):R.error("新增失败!!!");
    }

    @Override
    public R findMenu(long menuId) {
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(menuId);
        return R.OK().put("menu",sysMenu);
    }

    @Override
    public R update(SysMenu sysMenu) {
        int i = sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
        if(i>0){
            return R.OK();
        }
        return R.error("修改失败！！");
    }

    @Override
    public List<String> findPermsByUserId(long userId) {

        List<String> list = sysMenuMapper.findPermsByUserId(userId);
        Set set = new HashSet<String>();
        for (String s : list) {
            if(StringUtils.isNotEmpty(s)){
                String ss[] = s.split(",");
                for (String s1 : ss) {
                    set.add(s1);
                }
            }
        }
        List<String> newList = new ArrayList<>();
        newList.addAll(set);
        return newList;
    }

    @Override
    public R findUserMenu() {

        List<SysMenu> dir = sysMenuMapper.findDir(ShiroUtils.getUserId());
        for (SysMenu sysMenu : dir) {
            List<SysMenu> menu = sysMenuMapper.findMenu(sysMenu.getMenuId(),ShiroUtils.getUserId());
            sysMenu.setList(menu);
        }

        List<String> perms = this.findPermsByUserId(ShiroUtils.getUserId());

        return R.OK().put("menuList",dir).put("permissions",perms);
    }
}
