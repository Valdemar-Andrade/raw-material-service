package com.projeto.rawmaterialservice.controller;

import com.projeto.rawmaterialservice.dto.ExtractionConfig;
import com.projeto.rawmaterialservice.service.AutomatedExtractionTask;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(origins = "http://localhost:4200")
public class SimulationController {

    private final AutomatedExtractionTask extractionTask;

    public SimulationController(AutomatedExtractionTask extractionTask) {
        this.extractionTask = extractionTask;
    }

    @GetMapping("/config")
    public ResponseEntity<ExtractionConfig> getConfig() {
        return ResponseEntity.ok(extractionTask.getConfig());
    }

    @PostMapping("/config")
    public ResponseEntity<String> updateConfig(@RequestBody ExtractionConfig config) {
        extractionTask.updateConfig(config);
        return ResponseEntity.ok("Configuração da simulação atualizada com sucesso!");
    }
}
