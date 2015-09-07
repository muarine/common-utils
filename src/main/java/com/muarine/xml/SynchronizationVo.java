/**   
* @Title: UploadTradeDataVo.java 
* @Package test.com.rtmap.order.kechuan 
* @Description: 用一句话描述该文件做什么
* @author maoyun0903 maoyun@rtmap.com   
* @date 2015-4-29 上午11:33:02 
* @version V1.0   
*/
package com.muarine.xml;

/** 
 * @ClassName: SynchronizationVo 
 * @Description: APP第三方支付记录同步
 * @author maoyun0903 maoyun@rtmap.com 
 * @date 2015-4-29 上午11:33:02 
 * @version V1.0  
 *  
 */
public class SynchronizationVo {
	
	// header
	private String terminalType;		//终端类型
	private String txDate;				//终端交易日期
	private String mallid;				//商场ID
	private String storecode;			//收银店铺号
	private String bsstorecode;			//合同店铺号
	private String tillid;				//收银机号
	private String cashierno;			//收银员
	private String crc;					//档次请求唯一标识
	private String type;				//支付类型
	
	
	private String totalfee;			//总金额
	private String actualfee;			//实收金额
	private String couponfee;			//优惠金额
	private String tradeno;				//收单行流水号
	private String outid;				//科传单号
	private String buyerid;				//买家账号
//	private String refundoutid;			//退款科传流水号
//	private String refundtradeno;		//退款交易号
	private String txtype;				//交易类型：SA：销售 	SR：退款
	private String status;				//状态
	private String txdate;				//终端交易日期
	private String officialdate;		//官方交易时间  yyyyMMdd
//	private String extresult;			//扩展信息
	
	private String Sign;
	
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	public String getTxDate() {
		return txDate;
	}
	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}
	public String getMallid() {
		return mallid;
	}
	public void setMallid(String mallid) {
		this.mallid = mallid;
	}
	public String getStorecode() {
		return storecode;
	}
	public void setStorecode(String storecode) {
		this.storecode = storecode;
	}
	public String getBsstorecode() {
		return bsstorecode;
	}
	public void setBsstorecode(String bsstorecode) {
		this.bsstorecode = bsstorecode;
	}
	public String getTillid() {
		return tillid;
	}
	public void setTillid(String tillid) {
		this.tillid = tillid;
	}
	public String getCashierno() {
		return cashierno;
	}
	public void setCashierno(String cashierno) {
		this.cashierno = cashierno;
	}
	public String getCrc() {
		return crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(String totalfee) {
		this.totalfee = totalfee;
	}
	public String getActualfee() {
		return actualfee;
	}
	public void setActualfee(String actualfee) {
		this.actualfee = actualfee;
	}
	public String getCouponfee() {
		return couponfee;
	}
	public void setCouponfee(String couponfee) {
		this.couponfee = couponfee;
	}
	public String getTradeno() {
		return tradeno;
	}
	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}
	public String getOutid() {
		return outid;
	}
	public void setOutid(String outid) {
		this.outid = outid;
	}
	public String getBuyerid() {
		return buyerid;
	}
	public void setBuyerid(String buyerid) {
		this.buyerid = buyerid;
	}
	public String getTxtype() {
		return txtype;
	}
	public void setTxtype(String txtype) {
		this.txtype = txtype;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTxdate() {
		return txdate;
	}
	public void setTxdate(String txdate) {
		this.txdate = txdate;
	}
	public String getOfficialdate() {
		return officialdate;
	}
	public void setOfficialdate(String officialdate) {
		this.officialdate = officialdate;
	}
	public String getSign() {
		return Sign;
	}
	public void setSign(String sign) {
		Sign = sign;
	}
	@Override
	public String toString() {
		return "SynchronizationVo [Sign=" + Sign + ", actualfee=" + actualfee
				+ ", bsstorecode=" + bsstorecode + ", buyerid=" + buyerid
				+ ", cashierno=" + cashierno + ", couponfee=" + couponfee
				+ ", crc=" + crc + ", mallid=" + mallid + ", officialdate="
				+ officialdate + ", outid=" + outid + ", status=" + status
				+ ", storecode=" + storecode + ", terminalType=" + terminalType
				+ ", tillid=" + tillid + ", totalfee=" + totalfee
				+ ", tradeno=" + tradeno + ", txDate=" + txDate + ", txdate="
				+ txdate + ", txtype=" + txtype + ", type=" + type + "]";
	}
	
}
