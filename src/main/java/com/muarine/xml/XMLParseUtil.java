package com.rtmap.weixin.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.rtmap.weixin.aes.AesException;
import com.rtmap.weixin.domain.ChosenBeacon;
import com.rtmap.weixin.vo.Article;
import com.rtmap.weixin.vo.ReqMsg;
import com.rtmap.weixin.vo.ResNewsMsg;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
/**
 * XMLParseUtil.
 * 
 * @author Muarine maoyun@rtmap.com
 * @date 2015年7月28日
 * @since 2.0
 */
public class XMLParseUtil {
	
	private static Logger log = LoggerFactory.getLogger(XMLParseUtil.class);
	
	/**
     * 
     * @Description: XML转为键值SynchronizationVo对象
     * @param xmlString
     * @throws Exception 
     */
	public static ReqMsg parseXML(InputStream is) throws Exception {
		ReqMsg result = new ReqMsg();
    	Class<?> clazz = result.getClass();
    	//这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder = factory.newDocumentBuilder();
    	Document document = builder.parse(is);
    	//获取到document里面的全部结点
    	NodeList allNodes = document.getFirstChild().getChildNodes();
    	Node node;
    	int i=0;
    	while (i < allNodes.getLength()) {
    		node = allNodes.item(i);
    		if(node instanceof Element){
    			Field[] fields = clazz.getDeclaredFields();
    	        for (Field f : fields) {
    	            f.setAccessible(true);
    	            if (f.getName().equals(node.getNodeName())) {
    					if(f.getType() == int.class){
    						f.setInt(result, Integer.valueOf(node.getTextContent()));
    					}else if(f.getType() == int.class){
    						f.setInt(result, Integer.valueOf(node.getTextContent()));
    					}else if(f.getType() == long.class){
    						f.setLong(result, Long.valueOf(node.getTextContent()));
    					}else{
    						f.set(result, node.getTextContent());
    					}
    	            }
    	        }
    		}
    		i++;
    	}
    	return result;
    }
	
	
	public static InputStream getStringStream(String sInputString) {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }
	
	/**
	 * 文本消息转为XML
	 * @param text
	 * @return
	 */
	public static String objToXml(Object obj) {
		xstream.alias("xml", obj.getClass());
		xstream.alias("ChosenBeacon", ChosenBeacon.class);
		xstream.alias("AroundBeacons", List.class);
		xstream.alias("AroundBeacon", ChosenBeacon.class);
		return xstream.toXML(obj);
	}
	
	public static ReqMsg xmlToObj(String xml){
		xstream.alias("xml", ReqMsg.class);
		xstream.alias("ChosenBeacon", ChosenBeacon.class);
		xstream.addImplicitCollection(ReqMsg.class, "CreateTime", String.class);
		xstream.alias("AroundBeacons", List.class);
		xstream.alias("AroundBeacon", ChosenBeacon.class);
		return (ReqMsg) xstream.fromXML(xml);
	}
	
	/**
	 * 列表新闻转为XML
	 * @param news
	 * @return
	 */
	public static String newsToXml(ResNewsMsg news) {
		xstream.alias("xml", news.getClass());
		xstream.alias("item", Article.class);
		return xstream.toXML(news);
	}
	
	/**
	 * 提取出xml数据包中的加密消息
	 * @param xmltext 待提取的xml字符串
	 * @return 提取出的加密消息字符串
	 * @throws AesException 
	 */
	public static Object[] extract(String xmltext) throws AesException     {
		Object[] result = new Object[3];
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xmltext);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");
			NodeList nodelist2 = root.getElementsByTagName("ToUserName");
			result[0] = 0;
			result[1] = nodelist1.item(0).getTextContent();
			result[2] = nodelist2.item(0).getTextContent();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return result;
	}

	/**
	 * 生成xml消息
	 * @param encrypt 加密后的消息密文
	 * @param signature 安全签名
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @return 生成的xml字符串
	 */
	public static String generate(String encrypt, String signature, String timestamp, String nonce) {

		String format = "<xml>\n" + "<Encrypt><![CDATA[%1$s]]></Encrypt>\n"
				+ "<MsgSignature><![CDATA[%2$s]]></MsgSignature>\n"
				+ "<TimeStamp>%3$s</TimeStamp>\n" + "<Nonce><![CDATA[%4$s]]></Nonce>\n" + "</xml>";
		return String.format(format, encrypt, signature, timestamp, nonce);

	}
	
	
	public static XStream xstream = new XStream(new XppDriver(){
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					boolean CDATA = true;
					@SuppressWarnings("rawtypes")
					@Override
					public void startNode(String name, Class clazz) {
						super.startNode(name, clazz);
					}
					@Override
					protected void writeText(QuickWriter writer, String text) {
						
						if(StringUtils.isNumeric(text)){
							CDATA = false;
						}
						if (CDATA) {
							writer.write("<![CDATA[");
							writer.write(text);
							writer.write("]]>");
						} else {
							writer.write(text);
						}
					}
				};
			}
	});
}
