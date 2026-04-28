package com.projeto.rawmaterialservice.service;

import com.projeto.rawmaterialservice.model.PipelineStep;
import com.projeto.rawmaterialservice.model.ProductionPipeline;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PipelineService {

    public void execute(ProductionPipeline  pipeline) {
        // Simula a execução do pipeline
        for (PipelineStep step : pipeline.getSteps()) {
            System.out.println("Executando etapa: " + step.getName() + " por " + step.getDurationMs() + " ms");

            try {
                Thread.sleep(step.getDurationMs());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Pipeline interrompido");
                return;
            }
        }

        System.out.println("Pipeline " + pipeline.getName() + " concluído");
    }
}
