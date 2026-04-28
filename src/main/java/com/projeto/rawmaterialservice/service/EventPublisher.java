package com.projeto.rawmaterialservice.service;

import com.projeto.rawmaterialservice.entity.RawMaterial;

public interface EventPublisher {

    void sendRawMaterialEvent(RawMaterial material);
}
