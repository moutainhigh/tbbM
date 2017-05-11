package com.xinguang.tubobo.impl.merchant.mq;

import com.rabbitmq.client.Channel;
import com.xinguang.tubobo.impl.merchant.entity.MerchantOrderEntity;
import com.xinguang.tubobo.impl.merchant.service.OrderService;
import com.xinguang.tubobo.merchant.api.dto.MerchantGrabCallbackDTO;
import com.xinguang.tubobo.merchant.api.enums.EnumMerchantOrderStatus;
import com.xinguang.tubobo.merchant.api.util.BeanBytesConvertionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/4/30.
 */
@Service
public class RmqTaskGrabHandler implements ChannelAwareMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(RmqTaskGrabHandler.class);
//    @Autowired
//    private MerchantOrderService merchantOrderService;
    @Autowired
    private OrderService orderService;
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] bytes = message.getBody();
        MerchantGrabCallbackDTO merchantGrabCallbackDTO = (MerchantGrabCallbackDTO) BeanBytesConvertionUtil.ByteToObject(bytes);
        logger.info("收到抢单回调。MerchantGrabCallbackDTO：{}", merchantGrabCallbackDTO.toString());
        if (null == merchantGrabCallbackDTO) {
            return;
        }
        MerchantOrderEntity order = orderService.findByOrderNo(merchantGrabCallbackDTO.getTaskNo());
        if (order == null ||
                !EnumMerchantOrderStatus.WAITING_GRAB.getValue().equals(order.getOrderStatus())){
            logger.info("处理抢单回调，orderNo：{}",merchantGrabCallbackDTO.getTaskNo());
            orderService.riderGrabOrder(merchantGrabCallbackDTO.getRiderId(), merchantGrabCallbackDTO.getRiderName(),
                    merchantGrabCallbackDTO.getRiderPhone(), merchantGrabCallbackDTO.getTaskNo(), merchantGrabCallbackDTO.getGrabTime());
        }
    }
}