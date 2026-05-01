package com.projeto.rawmaterialservice.service;

import com.projeto.rawmaterialservice.dto.BaseEvent;

public interface EventPublisher {

    void sendRawMaterialEvent(BaseEvent materialEvent);
}
