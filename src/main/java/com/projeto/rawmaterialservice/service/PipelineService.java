package com.projeto.rawmaterialservice.service;

import com.projeto.rawmaterialservice.dto.BaseEvent;
import com.projeto.rawmaterialservice.dto.RawMaterialPayload;
import com.projeto.rawmaterialservice.model.PipelineStep;
import com.projeto.rawmaterialservice.model.ProductionPipeline;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PipelineService {
    private final EventPublisher eventPublisher;

    public PipelineService(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Async
    public void execute(ProductionPipeline pipeline, RawMaterialPayload payload) {
        for (PipelineStep step : pipeline.getSteps()) {
            try {
                Thread.sleep(step.getDurationMs());
                System.out.println("Etapa: " + step.getName() + " concluída.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Criando o envelope final para o Kafka
        BaseEvent event = BaseEvent.create(
                "RAW_MATERIAL_EXTRACTED", // Evento obrigatório
                payload.producer().service(),
                "processing-service", // Próximo serviço na cadeia
                payload
        );

        eventPublisher.sendRawMaterialEvent(event);
        System.out.println("[raw-material-service] Matéria-prima enviada para a próxima etapa da cadeia.");
    }
}
