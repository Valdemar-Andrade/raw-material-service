package com.projeto.rawmaterialservice.service;

import com.projeto.rawmaterialservice.dto.RawMaterialRequest;
import com.projeto.rawmaterialservice.entity.RawMaterial;
import com.projeto.rawmaterialservice.model.PipelineStep;
import com.projeto.rawmaterialservice.model.ProductionPipeline;
import com.projeto.rawmaterialservice.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {

    private final RawMaterialRepository repository;
    private final PipelineService pipelineService;
    private final EventPublisher eventPublisher;

    public RawMaterialService(RawMaterialRepository repository,
                              PipelineService pipelineService,
                              EventPublisher eventPublisher) {
        this.repository = repository;
        this.pipelineService = pipelineService;
        this.eventPublisher = eventPublisher;
    }

    private ProductionPipeline buildDefaultPipeline() {

        return ProductionPipeline.builder().
                name("RAW_MATERIAL_PIPELINE").
                steps(List.of(
                        new PipelineStep("EXTRACTION", 3000L),
                        new PipelineStep("INITIAL_PROCESSING", 2000L),
                        new PipelineStep("PACKAGING", 1000L)
                )).build();
    }

    public void create(RawMaterialRequest request) {

        validate(request);
        RawMaterial material = createRawMaterial(request);

        repository.save(material);

        ProductionPipeline pipeline = buildDefaultPipeline();

        pipelineService.execute(pipeline);
        eventPublisher.sendRawMaterialEvent(material);
    }

    private RawMaterial createRawMaterial(RawMaterialRequest request) {
        return RawMaterial.builder()
                .name(request.name())
                .type("RAW_MATERIAL")
                .targetComponent(request.targetComponent())
                .quantity(request.quantity())
                .build();
    }

    private void validate(RawMaterialRequest request) {

        if (request.name() == null || request.name().isBlank()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (request.quantity() == null || request.quantity() <= 0) {
            throw new RuntimeException("Quantidade deve ser maior que zero");
        }
    }
}
