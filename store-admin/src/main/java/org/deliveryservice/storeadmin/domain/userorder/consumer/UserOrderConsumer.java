package org.deliveryservice.storeadmin.domain.userorder.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.message.model.UserOrderMessage;
import org.deliveryservice.storeadmin.domain.userorder.business.UserOrderBusiness;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserOrderConsumer {

    private final UserOrderBusiness userOrderBusiness;

    @RabbitListener(queues = "delivery.queue")
    public void userOrderConsumer(
        UserOrderMessage userOrderMessage
    ) {
        log.info("message queue >> {}", userOrderMessage);
        userOrderBusiness.pushUserOrder(userOrderMessage);
    }
}