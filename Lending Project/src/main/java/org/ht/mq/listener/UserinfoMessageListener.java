package com.qianfeng.p2p.mq.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.qianfeng.p2p.bean.BaseJson;
import com.qianfeng.p2p.dao.BorrowMoneyMapper;
import com.qianfeng.p2p.jedis.JedisInterface;
import com.qianfeng.p2p.pojo.BorrowMoneyInfoPojo;
import com.qianfeng.p2p.utils.JsonUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import redis.clients.jedis.Jedis;
@Component("messageListener")
public class UserinfoMessageListener implements MessageListener {
	@Resource
	private JedisInterface jedisInterface;
	@Resource
	private FreeMarkerConfigurer configurer;
	@Resource
	private BorrowMoneyMapper borrowMoneyMapper;
	
	@Override
	public void onMessage(Message message) {
		
		try {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//receive data
			TextMessage textMessage=(TextMessage) message;
			String text = textMessage.getText();
			System.err.println(text);
			//process message
				//{"code":"1","data":{id:"1","field":"realstatus","value":"2"}}
			
			BaseJson json = JsonUtils.jsonToObject(text, BaseJson.class);

			int code = json.getCode();
			if (code==1) {
				Map<String, String> map=(Map<String, String>) json.getData();
				String userid = map.get("userid");
				String key ="userinfo"+userid;
				map.remove("userid");
				Jedis jedis = jedisInterface.getJedis();
				jedisInterface.hmset(key, map, jedis);
				jedisInterface.close(jedis);
			}else if (code==2) {
				try {

					Integer id=Integer.parseInt(json.getData().toString());
					BorrowMoneyInfoPojo borrowMoneyInfoPojo=borrowMoneyMapper.findBorrowMoneyInfoById(id);
				
					Map<String, Object> dataModel =new HashMap<>();
					dataModel.put("rates", new DecimalFormat("#.00").format(borrowMoneyInfoPojo.getRate()).split("\\."));
					dataModel.put("borrowMoneyInfoPojo", borrowMoneyInfoPojo);
					Configuration configuration = configurer.getConfiguration();
					Template template = configuration.getTemplate("xiangqing.ftl");
					FileWriter fileWriter=new FileWriter(new File("/home/jackiechan/下载/html/"+id+".html"));
					template.process(dataModel, fileWriter);
					fileWriter.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
