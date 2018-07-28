package com.yiyun.web.liumo.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yiyun.domain.LmOrder;
import com.yiyun.domain.LmProduct;
import com.yiyun.domain.LmUser;
import com.yiyun.utils.PageUtil;
import com.yiyun.web.common.utils.Query;
import com.yiyun.web.common.utils.R;
import com.yiyun.web.liumo.service.LmOrderService;
import com.yiyun.web.liumo.service.LmProductService;
import com.yiyun.web.liumo.service.LmUserService;
import com.yiyun.web.liumo.util.RedisUtil;
import com.yiyun.web.liumo.util.SnowflakeIdWorker;

import lombok.extern.slf4j.Slf4j;

/**
 * @title 六漠订单相关
 * @author wangyudong
 * @date 2018年7月14日下午12:39:04
 */
@Slf4j
@RestController
@RequestMapping("/liumo/app/order")
public class LmAppOrderController {

	@Autowired
	private LmOrderService lmOrderService;
	@Autowired
	private LmProductService lmProductService;
	@Autowired
	private LmUserService lmUserService;

	/**
	 * 查询订单列表
	 * 
	 * @param params
	 * @return
	 */
	@PostMapping("/list")
	public R list(@RequestParam Map<String, Object> params) {
		try {
			String token = (String) params.get("token");

			LmUser lmUser = RedisUtil.getLmUser(token);
			if (null == lmUser) {
				return R.error("用户未登录，请返回后重试");
			}
			// 状态判断
			String state = (String) params.get("state");
			if (StringUtils.equals("X", state)) { // 服务中
				params.put("state", "'01','02','03','04'");
			} else if (StringUtils.isNotBlank(state)) { // 其他状态
				params.put("state", "'" + state + "'");
			}

			params.put("userId", lmUser.getId());
			Query query = new Query(params);
			List<LmOrder> records = lmOrderService.list(query);

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(16);
			if (!CollectionUtils.isEmpty(records)) {
				Map<String, String> proMap = lmProductMap();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				Map<String, Object> map = null;
				for (LmOrder record : records) {
					map = new HashMap<String, Object>(16);
					map.put("id", record.getId()); // 订单编号
					map.put("username", record.getUsername()); // 借款人
					map.put("orderType", record.getOrderType()); // 订单类型
					map.put("orderTypeName", proMap.get(record.getOrderType())); // 订单类型名称
					map.put("createTime", sdf.format(record.getCreateTime())); // 报单时间
					map.put("state", record.getState()); // 状态
					if (StringUtils.equals("05", record.getState())) {
						map.put("stateName", "已放款"); // 状态名称
					} else if (StringUtils.equals("06", record.getState())) {
						map.put("stateName", "失败"); // 状态名称
					} else {
						map.put("stateName", "服务中 "); // 状态名称
					}
					list.add(map);
				}
			}

			int total = lmOrderService.count(query);
			PageUtil pageUtil = new PageUtil(list, total);
			return R.ok().put("page", pageUtil);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error();
		}
	}

	/**
	 * 查询在途订单
	 * 
	 * @return
	 */
	@PostMapping("/current")
	public R current(@RequestParam Map<String, String> param) {
		try {
			String token = param.get("token");

			LmUser lmUser = RedisUtil.getLmUser(token);
			if (null == lmUser) {
				return R.error("用户未登录，请返回后重试");
			}

			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("userId", lmUser.getId());
			params.put("state", "'01','02','03','04'");
			List<LmOrder> lmOrderList = lmOrderService.list(params);

			if (CollectionUtils.isEmpty(lmOrderList)) {
				return R.ok().put("record", null);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			LmOrder record = lmOrderList.get(0);
			Map<String, Object> map = new HashMap<String, Object>(16);
			map.put("id", record.getId()); // 订单编号
			map.put("username", record.getUsername()); // 借款人
			map.put("orderType", record.getOrderType()); // 订单类型
			map.put("createTime", sdf.format(record.getCreateTime())); // 报单时间
			map.put("state", record.getState()); // 状态
			return R.ok().put("record", map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error();
		}
	}

	/**
	 * 创建订单
	 * 
	 * @return
	 */
	@PostMapping("/create")
	public R create(@RequestParam Map<String, String> param) {
		try {
			String token = param.get("token");
			String orderType = param.get("orderType");

			LmUser lmUser = RedisUtil.getLmUser(token);
			if (null == lmUser) {
				return R.error("用户未登录，请返回后重试");
			}
			lmUser = lmUserService.get(lmUser.getId());
			if (null == lmUser) {
				return R.error("用户未登录，请返回后重试");
			}
			// 实名认证标志（1是0否2认证中）
			if (StringUtils.equals(lmUser.getCertSign(), "0")) {
				return R.error("用户未实名认证，请实名认证后重试");
			}
			if (StringUtils.equals(lmUser.getCertSign(), "2")) {
				return R.error("用户正在实名认证中，请实名认证通过后重试");
			}
			// if (StringUtils.isBlank(orderType)) {
			// return R.error("请选择产品类型");
			// }

			Map<String, Object> params = new HashMap<String, Object>(3);
			params.put("userId", lmUser.getId());
			params.put("state", "'01','02','03','04'");
			List<LmOrder> lmOrderList = lmOrderService.list(params);
			if (!CollectionUtils.isEmpty(lmOrderList)) {
				return R.error("用户已经有在途订单");
			}

			LmOrder lmOrder = new LmOrder();
			lmOrder.setId(String.valueOf(SnowflakeIdWorker.ID_WORKER.nextId()));
			lmOrder.setUserId(lmUser.getId());
			lmOrder.setMobile(lmUser.getMobile());
			lmOrder.setUsername(lmUser.getUsername());
			lmOrder.setCertType(lmUser.getCertType());
			lmOrder.setCertNo(lmUser.getCertNo());
			lmOrder.setOrderType(orderType);
			lmOrder.setState("01");
			lmOrder.setCreateTime(new Date());
			lmOrder.setOrderDate01(new Date());
			lmOrder.setLastUpdateTime(new Date());
			lmOrderService.save(lmOrder);
			return R.ok();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return R.error();
		}
	}

	/**
	 * 获取产品Map集合
	 * 
	 * @return
	 */
	private Map<String, String> lmProductMap() {
		Map<String, String> map = new HashMap<String, String>(16);

		Map<String, Object> paramMap = new HashMap<String, Object>(8);
		paramMap.put("state", "1");
		List<LmProduct> lmProductList = lmProductService.list(paramMap);
		if (!CollectionUtils.isEmpty(lmProductList)) {
			for (LmProduct lmProduct : lmProductList) {
				map.put(lmProduct.getPrdType(), lmProduct.getTitle());
			}
		}
		return map;
	}

}
