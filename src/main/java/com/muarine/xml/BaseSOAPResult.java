/**   
* @Title: BaseSOAPResult.java 
* @Package test.com.rtmap.order.kechuan 
* @Description: 用一句话描述该文件做什么
* @author maoyun0903 maoyun@rtmap.com   
* @date 2015-4-30 下午02:17:25 
* @version V1.0   
*/
package com.muarine.xml;

/** 
 * @ClassName: BaseSOAPResult 
 * @Description: SOAP返回结果实体类 
 * @author maoyun0903 maoyun@rtmap.com 
 * @date 2015-4-30 下午02:17:25 
 * @version V1.0  
 *  
 */
public class BaseSOAPResult {

	
	private String responsecode;						// 服务器返回 0：成功；其他代码代表未成功。 1008：查询的销售单据不存在
	private String responsemessage;						// 服务器返回消息
	
	// 交易单据数据上传
	private String txdate_yyyymmdd;						//交易日期
	private String txtime_hhmmss;						//交易时间
	private String storecode;							//店铺号
	private String tillid;								//收银机号
	private String txdocno;								//销售单号
	private String ttpossalestxdate_yyyymmdd;			//科传系统交易日期
	private String ttpossalesdocno;						//科传销售单号
	// 查询交易单据
	private String isRefunded;						//科传销售单号
	
	// 移动支付交易上传导入接口
	private String ErrorCode;					//错误编码   0 ：成功； 非0： 异常
	private String ErrorMSG;					//异常描述
	private String ResObj;						//详细描述
	private String CRC;							//当次请求唯一标识 
	private String Alert;						//警告提示
	
	
	public String getResponsecode() {
		return responsecode;
	}
	public void setResponsecode(String responsecode) {
		this.responsecode = responsecode;
	}
	public String getResponsemessage() {
		return responsemessage;
	}
	public void setResponsemessage(String responsemessage) {
		this.responsemessage = responsemessage;
	}
	public String getTxdate_yyyymmdd() {
		return txdate_yyyymmdd;
	}
	public void setTxdate_yyyymmdd(String txdateYyyymmdd) {
		txdate_yyyymmdd = txdateYyyymmdd;
	}
	public String getTxtime_hhmmss() {
		return txtime_hhmmss;
	}
	public void setTxtime_hhmmss(String txtimeHhmmss) {
		txtime_hhmmss = txtimeHhmmss;
	}
	public String getStorecode() {
		return storecode;
	}
	public void setStorecode(String storecode) {
		this.storecode = storecode;
	}
	public String getTillid() {
		return tillid;
	}
	public void setTillid(String tillid) {
		this.tillid = tillid;
	}
	public String getTxdocno() {
		return txdocno;
	}
	public void setTxdocno(String txdocno) {
		this.txdocno = txdocno;
	}
	public String getTtpossalestxdate_yyyymmdd() {
		return ttpossalestxdate_yyyymmdd;
	}
	public void setTtpossalestxdate_yyyymmdd(String ttpossalestxdateYyyymmdd) {
		ttpossalestxdate_yyyymmdd = ttpossalestxdateYyyymmdd;
	}
	public String getTtpossalesdocno() {
		return ttpossalesdocno;
	}
	public void setTtpossalesdocno(String ttpossalesdocno) {
		this.ttpossalesdocno = ttpossalesdocno;
	}
	public String getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(String errorCode) {
		ErrorCode = errorCode;
	}
	public String getErrorMSG() {
		return ErrorMSG;
	}
	public void setErrorMSG(String errorMSG) {
		ErrorMSG = errorMSG;
	}
	public String getResObj() {
		return ResObj;
	}
	public void setResObj(String resObj) {
		ResObj = resObj;
	}
	public String getCRC() {
		return CRC;
	}
	public void setCRC(String cRC) {
		CRC = cRC;
	}
	public String getAlert() {
		return Alert;
	}
	public void setAlert(String alert) {
		Alert = alert;
	}
	
	public String getIsRefunded() {
		return isRefunded;
	}
	public void setIsRefunded(String isRefunded) {
		this.isRefunded = isRefunded;
	}
	@Override
	public String toString() {
		return "BaseSOAPResult [Alert=" + Alert + ", CRC=" + CRC
				+ ", ErrorCode=" + ErrorCode + ", ErrorMSG=" + ErrorMSG
				+ ", ResObj=" + ResObj + ", responsecode=" + responsecode
				+ ", responsemessage=" + responsemessage + ", storecode="
				+ storecode + ", tillid=" + tillid + ", ttpossalesdocno="
				+ ttpossalesdocno + ", ttpossalestxdate_yyyymmdd="
				+ ttpossalestxdate_yyyymmdd + ", txdate_yyyymmdd="
				+ txdate_yyyymmdd + ", txdocno=" + txdocno + ", txtime_hhmmss="
				+ txtime_hhmmss + "]";
	}
}
