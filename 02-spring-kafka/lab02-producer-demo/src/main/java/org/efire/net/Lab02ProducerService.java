package org.efire.net;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Log4j2
public class Lab02ProducerService {

    private KafkaTemplate<String, Object> kafkaTemplate;

    public Lab02ProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ListenableFuture<SendResult<String, Object>> publishEvent(Lab02Message lab02Message) {
        // Message sent asynchronously (non-blocking) using ListenableFuture
        var futureResult = kafkaTemplate.sendDefault(lab02Message.getEventId(), lab02Message);
        futureResult.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                handleFailure(ex);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                handleSuccess(result);
            }
        });
        return futureResult;
    }

    private void handleSuccess(SendResult<String, Object> result) {
        log.info("Message Sent SuccessFully for the key : {} and the value is {} , partition is {}",
                result.getProducerRecord().key(),
                result.getProducerRecord().value(), result.getRecordMetadata().partition());
    }

    private void handleFailure(Throwable ex) {
        log.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
            log.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }
}
