package com.muarine.xml;

/**
 * soap请求xml构造
 * @author 轩
 *
 */
public class RequestPostXml {
	private static RequestPostXml _postXml;

	/**
	 * @Description: 创建单例
	 * @return PostXml
	 */
	public static RequestPostXml getInstance() {
		if (null == _postXml) {
			_postXml = new RequestPostXml();
		}
		return _postXml;
	}

	/**
	* @Description:嘉荣卡券查询接口
	* @param 
	* @return String
	 */
	public static String getSearchTicketXML(String ticketCode) {
		StringBuffer sb = new StringBuffer();
		sb.append("<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempui.org/\">")
				.append("<soap:Header/>")
				.append("<soap:Body>")
				    .append("<tem:call_pay>")
						.append("<request>")
							.append("<Base>")
								.append("<ID/>")
								.append("<key/>")
							.append("</Base>")					
							.append("<weidian>")
								.append("<fun>WX_TICKET_QUERY_IBEACON</fun>")
								.append("<input>{TICKET_CODE:"+ticketCode+"}</input>")
							.append("</weidian>")
						.append("</request>")
					.append("</tem:call_pay>")
				.append("</soap:Body>")
			.append("</soap:Envelope>");				
		return sb.toString();
	}
	
}
