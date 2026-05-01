package com.projeto.rawmaterialservice.dto;

import java.util.List;

public record ExtractionConfig(
        boolean enabled,
        long frequencyMs,
        List<String> allowedMaterials // Ex: ["IRON", "LATEX", "LITHIUM"]
) { }
