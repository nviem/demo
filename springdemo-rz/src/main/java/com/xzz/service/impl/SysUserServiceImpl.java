package com.xzz.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzz.entity.SysUserExample;
import com.xzz.mapper.SysUserMapper;
import com.xzz.service.SysUserService;
import com.xzz.entity.SysUser;
import com.xzz.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
 * @Time: 下午 21:12
 */
@Service(value = "sysUserServiceImpl")
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysuserMapper;

    @Override
    public List<SysUser> findAll() {
        return sysuserMapper.selectByExample(null);
    }

    @Override
    public R login(SysUser sysUser) {
        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(sysUser.getUsername());

        List<SysUser> list = sysuserMapper.selectByExample(example);

        if(list==null||list.size()==0){
            return R.error("账号不存在");
        }
        SysUser u = list.get(0);
        if(!u.getPassword().equals(sysUser.getPassword())){
            return R.error("密码错误");
        }
        if(u.getStatus()==0){
            return R.error("账号被冻结");
        }

        return R.OK().put("u",u);
    }

    @Override
    public SysUser findByUname(String uname) {

        SysUserExample example = new SysUserExample();
        SysUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(uname);

        List<SysUser> list = sysuserMapper.selectByExample(example);

        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public ResultData findByPage(Pager pager, String search, Sorter sorter) {

        PageHelper.offsetPage(pager.getOffset(),pager.getLimit());
        SysUserExample sysUserExample = new SysUserExample();

        if(sorter!=null&&StringUtils.isNotEmpty(sorter.getSort())){
            sysUserExample.setOrderByClause("user_id "+sorter.getOrder());
        }

        SysUserExample.Criteria criteria = sysUserExample.createCriteria();
        if(search!=null&&!"".equals(search)){
            criteria.andUsernameLike("%"+search+"%");
        }
        List<SysUser> list = sysuserMapper.selectByExample(sysUserExample);

        PageInfo info = new PageInfo(list);
        List<SysUser> l = info.getList();
        long total = info.getTotal();
        return new ResultData(total,l);
    }

    @Override
    public R del(List<Long> ids) {
        return null;
    }

    @Override
    public List<SysUser> findLockAccount() {
        return sysuserMapper.findLockAccount();
    }

    @Override
    public int unLockAccount(SysUser user) {
        return sysuserMapper.unLockAccount(user);
    }

    @Override
    public R findPieData() {
        List<Map<String,Object>> list = sysuserMapper.findPieData();

        List list1 = new ArrayList();
        for (Map<String, Object> map : list) {
            String name = map.get("name")+"";
            list1.add(name);
        }
        return R.OK().put("legendData",list1).put("seriesData",list);
    }

    @Override
    public R findBarData() {
        List<Map<String,Object>> list = sysuserMapper.findBarData();
        List xAxisData = new ArrayList();
        List series0Data = new ArrayList();
        List series1Data = new ArrayList();
        for (Map<String, Object> map : list) {
            String xAxisData1 = map.get("name")+"";
            String series0Data1 = map.get("boy")+"";
            String series1Data1 = map.get("girl")+"";
            xAxisData.add(xAxisData1);
            series0Data.add(series0Data1);
            series1Data.add(series1Data1);
        }
        return R.OK().put("xAxisData",xAxisData).put("series0Data",series0Data).put("series1Data",series1Data);
    }

    @Override
    public List<Map<String, Object>> exportExcel() {
        return null;
    }


}
