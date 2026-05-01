package com.projeto.rawmaterialservice.dto;

import java.util.List;

public record RawMaterialPayload(
        String id,
        String name,
        String type, // "RAW_MATERIAL"
        ProducerDTO producer,
        PurposeDTO purpose,
        List<Object> components
) { }
