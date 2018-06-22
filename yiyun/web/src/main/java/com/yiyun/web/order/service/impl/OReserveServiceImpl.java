package com.yiyun.web.order.service.impl;

import com.yiyun.constants.CommonConstants;
import com.yiyun.constants.MemberConstants;
import com.yiyun.constants.RedisConstants;
import com.yiyun.constants.ReserveConstants;
import com.yiyun.dao.cluster.ClusterOReserveDao;
import com.yiyun.dao.master.MMemberDao;
import com.yiyun.dao.master.OReserveDao;
import com.yiyun.domain.MMember;
import com.yiyun.domain.OReserve;
import com.yiyun.resp.AddReserveResp;
import com.yiyun.resp.CheckSmsResp;
import com.yiyun.utils.DateUtil;
import com.yiyun.web.common.config.redis.RedisTemplateDAO;
import com.yiyun.web.order.service.OReserveService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OReserveServiceImpl implements OReserveService {
    @Autowired
    private OReserveDao oReserveDao;

    @Autowired
    private ClusterOReserveDao clusterOReserveDao;

    @Autowired
    private MMemberDao mMemberDao;

    @Autowired
    private RedisTemplateDAO redisTemplateDAO;

    @Override
    public OReserve get(Long id) {
        return clusterOReserveDao.get(id);
    }

    @Override
    public List<OReserve> list(Map<String, Object> map) {
        return clusterOReserveDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return clusterOReserveDao.count(map);
    }

    @Override
    public int save(OReserve oReserve) {
        return oReserveDao.save(oReserve);
    }

    @Override
    public int update(OReserve oReserve) {
        return oReserveDao.update(oReserve);
    }

    @Override
    public int remove(Long id) {
        return oReserveDao.remove(id);
    }

    @Override
    public int batchRemove(Long[] ids) {
        return oReserveDao.batchRemove(ids);
    }

    @Override
    public CheckSmsResp checkcode(String phone, String smsInputCode) {
        CheckSmsResp resp = new CheckSmsResp();

        if (StringUtils.isAnyBlank(phone, smsInputCode)) {
            resp.setMessage("手机号或验证码为空");
            return resp;
        }

        String smsCode = redisTemplateDAO.get(RedisConstants.CACHE_KEY_SMS_CODE + phone, 10);//取出验证码

        if (!Objects.equals(smsCode, smsInputCode)) {
            resp.setMessage("验证码不正确");
            return resp;
        }

        resp.setFlag(true);
        resp.setMessage("校验通过");

        //XTODO 记录该手机号 下次弹图片验证码
//        redisTemplateDAO.put(MessageConstants.ERROR_SMS_CODE + phone, phone, null, 1200L);
        return resp;
    }

    private static final Long TIMEMILLIS_2_HOUR = 1000L * 60 * 60 * 2;

    /**
     * 指定用户领取任务
     * 领取规则 :
     *      1 . 领取任务无上限
     *      2 . 领取优先时间最近的
     *      3 . 已领取的时间点(如11点) , 则该天11前后2h的任务不可领取
     *      4 . 已领取多个订单时 , 时间遵循如上
     * @param userId
     */
    @Override
    public AddReserveResp receive(Long userId) {
        AddReserveResp resp = new AddReserveResp();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("type", ReserveConstants.RESERVE_TYPE_PARTNER);
        paramMap.put("reserveStauts", ReserveConstants.RESERVE_STATUS_YTJ);//已提交待处理的单子
        paramMap.put("sort", "reserve_time");//预约时间最近的优先

        List<OReserve> todoReserveList = clusterOReserveDao.list(paramMap);
        if (todoReserveList == null || todoReserveList.isEmpty()) {//没有待领取订单
            resp.setMessage("当前无可领取订单");
            return resp;
        }

        OReserve reserveParam = new OReserve();
        reserveParam.setSalesmanId(userId);
        reserveParam.setReserveStauts(ReserveConstants.RESERVE_STATUS_YSL);//当前手中待处理的单子
        List<OReserve> mineReserveList = clusterOReserveDao.select(reserveParam);

        OReserve firstReserve;
        if (mineReserveList == null || mineReserveList.isEmpty()) {//当前没有进行中的订单 , 直接领取
            firstReserve = todoReserveList.get(0);
        } else {//当前已有订单
            Optional<OReserve> reserveOptional = todoReserveList.stream().filter(todoReserve -> notInMine(todoReserve, mineReserveList)).findFirst();
            firstReserve = reserveOptional.orElse(null);
        }
        if (firstReserve == null) {
            resp.setMessage("当前无可领取订单");
            return resp;
        }
        firstReserve.setReserveStauts(ReserveConstants.RESERVE_STATUS_YSL);
        firstReserve.setSalesmanId(userId);
        oReserveDao.update(firstReserve);

        resp.setFlag(true);
        resp.setMessage("订单领取成功");
        resp.setReserveId(firstReserve.getId());
        resp.setReserveTime(DateUtil.long2String(firstReserve.getReserveTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        return resp;

    }

    @Override
    public int cancel(Long id) {
        return oReserveDao.cancel(id);
    }

    @Override
    public void check(OReserve oReserve) {
        int result = oReserveDao.update(oReserve);
        if (result > 1 && Objects.equals(oReserve.getReserveStauts(), ReserveConstants.RESERVE_STATUS_CG)) {//成功之后创建用户
            OReserve reserve = clusterOReserveDao.get(oReserve.getId());
            if (reserve != null) {
                MMember member = new MMember();
                member.setType(Objects.equals(reserve.getType(), ReserveConstants.RESERVE_TYPE_BORROWER) ? MemberConstants.M_TYPE_KH : MemberConstants.M_TYPE_HHR);
                member.setStatus(CommonConstants.YES);
                member.setPhone(reserve.getUserPhone());
                member.setCreateTime(System.currentTimeMillis());
                mMemberDao.save(member);
            }
        }
    }

    private boolean notInMine(OReserve todoReserve, List<OReserve> mineReserveList) {
        if (todoReserve == null || mineReserveList == null || mineReserveList.isEmpty()) {
            return false;
        }

        Long todoTime = todoReserve.getReserveTime();//待领取的预约时间
        for (OReserve mineReserve : mineReserveList) {//遍历每一个已领取的订单
            Long mineReserveTime = mineReserve.getReserveTime();//提取订单时间
            if (mineReserveTime == null || mineReserveTime == 0) {
                return false;
            }
            if (todoTime > (mineReserveTime - TIMEMILLIS_2_HOUR) && todoTime < (mineReserveTime + TIMEMILLIS_2_HOUR)) {//若在已领取订单的前后2h之内 , 则说明时间冲突
                return false;
            }
        }
        return true;
    }


}
