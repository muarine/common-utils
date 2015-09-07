package com.muarine.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
/**
 * XMLParseUtil.
 * 
 * @author Muarine maoyun@rtmap.com
 * @date 2015年7月28日
 * @since 2.0
 */
public class XMLParseUtil {
	
	/**
	 * 
	* @Description: XML转为键值Map对象
	* @param xmlString
	* @throws ParserConfigurationException
	* @throws IOException
	* @throws SAXException
	* @return Map<String,Object>
	 */
    public static Map<String,Object> getMapFromXML(String xmlString) throws ParserConfigurationException, IOException, SAXException {

        //这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is =  Util.getStringStream(xmlString);
        Document document = builder.parse(is);

        //获取到document里面的全部结点
        NodeList allNodes = document.getFirstChild().getChildNodes();
        Node node;
        Map<String, Object> map = new HashMap<String, Object>();
        int i=0;
        while (i < allNodes.getLength()) {
            node = allNodes.item(i);
            if(node instanceof Element){
                map.put(node.getNodeName(),node.getTextContent());
            }
            i++;
        }
        return map;
    }
	
    /**
     * 
     * @Description: TODO XML转为键值SynchronizationVo对象
     * @param xmlString
     * @throws Exception 
     */
	public static SynchronizationVo getSynchronizationFromXML(String xmlString) throws Exception {
    	SynchronizationVo result = new SynchronizationVo();
    	Class<?> clazz = result.getClass();
    	//这里用Dom的方式解析回包的最主要目的是防止API新增回包字段
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder builder = factory.newDocumentBuilder();
    	InputStream is =  Util.getStringStream(xmlString);
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
    	            	if(f.getType() == double.class){
    						f.setDouble(result, Double.valueOf(node.getTextContent()));
    					}else if(f.getType() == int.class){
    						f.setInt(result, Integer.valueOf(node.getTextContent()));
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
    
    /**
	 * 统一下单UniteOrder对象转为XML
	 * @param video
	 * @return
	 */
	public static String ObjectToXml(Object object) {
		XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
		xStreamForRequestPostData.alias("xml", object.getClass());
		return xStreamForRequestPostData.toXML(object);
	}

	
    /**
     * 
     * @Description: TODO XML转为键值SalesMemoStatusResult
     * @param xmlString
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     * @return Map<String,Object>
     */
    @SuppressWarnings("unchecked")
	public static BaseSOAPResult getSOAPResult(String soapXml) throws ParserConfigurationException, IOException, SAXException {
    	
    	BaseSOAPResult result = new BaseSOAPResult();
    	SOAPMessage msg = formatSoapString(soapXml);
    	SOAPBody body;
		try {
			body = msg.getSOAPBody();
			Iterator<SOAPElement> iterator = body.getChildElements();
			parse(iterator,result);
		} catch (SOAPException e) {
			e.printStackTrace();
		}
    	return result;
    	
    }

    /**
     * 解析soap xml
     * @param iterator
     * @param resultBean
     */
    @SuppressWarnings("unchecked")
	private static void parse(Iterator<SOAPElement> iterator, BaseSOAPResult resultBean) {
        while (iterator.hasNext()) {
            SOAPElement element = iterator.next();
            if ("header".equals(element.getNodeName())) {
            	Iterator<SOAPElement> it = element.getChildElements();
                SOAPElement el = null;
                while (it.hasNext()) {
                    el = it.next();
                    if ("responsecode".equals(el.getLocalName())) {
                        resultBean.setResponsecode(el.getValue());
                    } else if ("responsemessage".equals(el.getLocalName())) {
                        resultBean.setResponsemessage(null != el.getValue() ? el.getValue() : "");
                    } 
                }
            } else if ("result".equals(element.getNodeName())) {
                Iterator<SOAPElement> it = element.getChildElements();
                SOAPElement el = null;
                while (it.hasNext()) {
                    el = it.next();
                    if ("txdate_yyyymmdd".equals(el.getLocalName())) {
                        resultBean.setTxdate_yyyymmdd(el.getValue());
                    } else if ("txtime_hhmmss".equals(el.getLocalName())) {
                        resultBean.setTxtime_hhmmss(null != el.getValue() ? el.getValue() : "");
                    } else if ("storecode".equals(el.getLocalName())) {
                        resultBean.setStorecode(el.getValue());
                    } else if ("tillid".equals(el.getLocalName())) {
                    	resultBean.setTillid(el.getValue());
                    } else if ("txdocno".equals(el.getLocalName())) {
                    	resultBean.setTxdocno(el.getValue());
                    } else if ("ttpossalestxdate_yyyymmdd".equals(el.getLocalName())) {
                    	resultBean.setTtpossalestxdate_yyyymmdd(el.getValue());
                    } else if ("ttpossalesdocno".equals(el.getLocalName())) {
                    	resultBean.setTtpossalesdocno(el.getValue());
                    } else if ("isrefunded".equals(el.getLocalName())) {
                    	resultBean.setIsRefunded(el.getValue());
                    } 
                }
           } else if ("ErrorCode".equals(element.getNodeName())) {
        	   resultBean.setErrorCode(element.getValue());
           } else if ("ErrorMSG".equals(element.getNodeName())) {
        	   resultBean.setErrorMSG(element.getValue());
          } else if (null == element.getValue()
                   && element.getChildElements().hasNext()) {
               parse(element.getChildElements(), resultBean);
           }
       }
   }

    /**
     * 把soap字符串格式化为SOAPMessage
     * 
     * @param soapString
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static SOAPMessage formatSoapString(String soapString) {
  	  MessageFactory msgFactory;
  	  try {
  		  msgFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
  		  SOAPMessage reqMsg = msgFactory.createMessage(new MimeHeaders(),
  				  new ByteArrayInputStream(soapString.getBytes("UTF-8")));
  		  reqMsg.saveChanges();
  		  return reqMsg;
  	  } catch (Exception e) {
  		  e.printStackTrace();
  		  return null;
  	  }
    }
}
