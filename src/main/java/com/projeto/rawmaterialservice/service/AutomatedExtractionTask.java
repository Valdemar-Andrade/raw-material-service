package com.projeto.rawmaterialservice.service;

import com.projeto.rawmaterialservice.dto.ExtractionConfig;
import com.projeto.rawmaterialservice.dto.RawMaterialRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class AutomatedExtractionTask {

    private static final Logger logger = LoggerFactory.getLogger(AutomatedExtractionTask.class);
    private final RawMaterialService rawMaterialService;

    private boolean enabled = false;
    private long frequencyMs = 10000;
    private List<String> allowedMaterials = List.of("IRON", "LATEX", "LITHIUM", "BAUXITE");

    public AutomatedExtractionTask(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    // Executa a cada 1 segundo, mas só processa se o tempo decorrido > frequencyMs
    private long lastRun = System.currentTimeMillis();

    @Scheduled(fixedRate = 1000)
    public void run() {
        if (enabled && (System.currentTimeMillis() - lastRun) >= frequencyMs) {
            lastRun = System.currentTimeMillis();
            RawMaterialRequest request = generateRandomMaterialForCar();

            logger.info("Iniciando extração automática: {}", request.name());
            rawMaterialService.create(request);
        }
    }

    private RawMaterialRequest generateRandomMaterialForCar() {
        String materialName = allowedMaterials.get(new Random().nextInt(allowedMaterials.size()));

        // Define o targetComponent com base no material
        String targetComponent = switch (materialName) {
            case "IRON" -> "ENGINE_BLOCK";
            case "LATEX" -> "TIRES";
            case "LITHIUM" -> "BATTERY";
            case "BAUXITE" -> "CHASSIS";
            default -> "GENERAL_PARTS";
        };

        return new RawMaterialRequest(materialName, targetComponent, 1);
    }

    public void updateConfig(ExtractionConfig config) {
        this.enabled = config.enabled();
        this.frequencyMs = config.frequencyMs();
        this.allowedMaterials = config.allowedMaterials();
        logger.info("[CONFIG] Simulação atualizada: Ativo={}, Frequência={}ms", enabled, frequencyMs);
    }

    public ExtractionConfig getConfig() {
        return new ExtractionConfig(enabled, frequencyMs, allowedMaterials);
    }
}
