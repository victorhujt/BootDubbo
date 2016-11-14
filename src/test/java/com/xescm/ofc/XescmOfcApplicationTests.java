package com.xescm.ofc;

import com.xescm.ofc.config.MqConfig;
import com.xescm.ofc.domain.OfcFundamentalInformation;
import com.xescm.ofc.mq.consumer.DefaultMqConsumer;
import com.xescm.ofc.mq.producer.DefaultMqProducer;
import com.xescm.ofc.utils.JSONUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = XescmOfcApplication.class)
@WebAppConfiguration
public class XescmOfcApplicationTests {
	@Resource
	private DefaultMqProducer defaultMqProducer;
	@Resource
	private DefaultMqConsumer defaultMqConsumer;
	@Resource
	MqConfig mqConfig;
	@Test
	public void sendMQTest() {
		defaultMqProducer.toSendMQ(JSONUtils.objectToJson(new OfcFundamentalInformation()));
	}

	@Test
	public void receiveMQTest(){
		defaultMqConsumer.MqConsumer(mqConfig.getTopic());
	}

}
