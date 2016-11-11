/**
 *
 */
package com.spider.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.util.ThreadLocalCache;
import com.spider.bean.ParserType;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Wayne.Wang<5waynewang@gmail.com>
 * @since 5:16:14 PM Jul 19, 2014
 */
public class TaskBean implements Serializable {

	private static final long serialVersionUID = 1802998747586288798L;
	private String taskId;
	private int retryTimes; // 重试次数
	private boolean retry = true; // 是否需要重试
	private boolean retryIncr = true; // 是否递增重试次数
	private long createTimeMillis = System.currentTimeMillis();// 创建时间
	private ParserType parserType;//解析类型
	//private ReqFrom reqFrom = ReqFrom.KKKD;//请求来源
	//private DeviceType deviceType = DeviceType.OTHER; // 设备类型
//	private String shopName;//店铺名称
	private String reqUrl;//请求url
	private String reqType;//请求类型 post、get
	private Map<String, Object> params;//请求参数

	private  boolean isFirstPage=false;
	private int pageSize=50;

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean firstPage) {
		isFirstPage = firstPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	//	public String getShopName() {
//		return shopName;
//	}
//
//	public void setShopName(String shopName) {
//		this.shopName = shopName;
//	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	public void setReqType(String type) {
		this.reqType = type;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getReqUrl() {

		return reqUrl;
	}

	public String getReqType() {
		return reqType;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getRetryTimes() {
		return retryTimes;
	}
	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}
	public int retryTimes() {
		return this.retryTimes(true);
	}
	public int retryTimes(boolean incr) {
		return incr ? ++this.retryTimes : this.retryTimes;
	}

	public boolean isRetry() {
		return retry;
	}
	public void setRetry(boolean retry) {
		this.retry = retry;
	}

	public boolean isRetryIncr() {
		return retryIncr;
	}
	public void setRetryIncr(boolean retryIncr) {
		this.retryIncr = retryIncr;
	}

//	public DeviceType getDeviceType() {
//		return deviceType;
//	}
//
//	public void setDeviceType(DeviceType deviceType) {
//		this.deviceType = deviceType;
//	}
//
//	public ReqFrom getReqFrom() {
//		return reqFrom;
//	}
//
//	public void setReqFrom(ReqFrom reqFrom) {
//		this.reqFrom = reqFrom;
//	}

	public ParserType getParserType() {
		return parserType;
	}

	public void setParserType(ParserType parserType) {
		this.parserType = parserType;
	}

	public long getCreateTimeMillis() {
		return createTimeMillis;
	}

	public void setCreateTimeMillis(long createTimeMillis) {
		this.createTimeMillis = createTimeMillis;
	}

	/**
	 * 复制bean
	 * @return
	 */
	public TaskBean clone() {
		final TaskBean bean = new TaskBean();
		BeanUtils.copyProperties(this, bean);
		return bean;
	}

	/**
	 * 转换成byte字节数组
	 * @return
	 */
	public byte[] toJSONBytes() {
		SerializeWriter out = new SerializeWriter();

		try {
			JSONSerializer serializer = new JSONSerializer(out);

			serializer.write(this);

			return out.toBytes("UTF-8");
		}
		finally {
			out.close();
		}
	}

	/**
	 * 转化成 bean对象
	 * @param bytes
	 * @return
	 */
	public static TaskBean parse(byte[] bytes) {

		return JSON.parseObject(bytes, 0, bytes.length, ThreadLocalCache.getUTF8Decoder(), TaskBean.class);
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
