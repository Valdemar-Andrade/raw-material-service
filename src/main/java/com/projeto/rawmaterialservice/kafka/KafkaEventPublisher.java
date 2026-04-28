package com.projeto.rawmaterialservice.kafka;

import com.projeto.rawmaterialservice.dto.BaseEvent;
import com.projeto.rawmaterialservice.entity.RawMaterial;
import com.projeto.rawmaterialservice.service.EventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @Value("${app.kafka.topic}")
    private String topic;

    @Value("${app.service.name}")
    private String serviceName;

    public KafkaEventPublisher(
            KafkaTemplate<String, BaseEvent> kafkaTemplate,
            @Value("${app.kafka.topic}") String topic,
            @Value("${app.service.name}") String serviceName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
        this.serviceName = serviceName;
    }

    @Override
    public void sendRawMaterialEvent(RawMaterial material) {

        BaseEvent event = BaseEvent.create(
                "RAW_MATERIAL_EXTRACTED",
                serviceName,
                "component-service",
                material
        );

        kafkaTemplate.send(topic, event);
    }
}
