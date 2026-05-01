package com.projeto.rawmaterialservice.service;

import com.projeto.rawmaterialservice.dto.ProducerDTO;
import com.projeto.rawmaterialservice.dto.PurposeDTO;
import com.projeto.rawmaterialservice.dto.RawMaterialPayload;
import com.projeto.rawmaterialservice.dto.RawMaterialRequest;
import com.projeto.rawmaterialservice.entity.RawMaterial;
import com.projeto.rawmaterialservice.model.PipelineStep;
import com.projeto.rawmaterialservice.model.ProductionPipeline;
import com.projeto.rawmaterialservice.repository.RawMaterialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawMaterialService {

    private final RawMaterialRepository repository;
    private final PipelineService pipelineService;
    private final String serviceName;

    public RawMaterialService(RawMaterialRepository repository,
                              PipelineService pipelineService, @Value("${app.service.name}") String serviceName) {
        this.repository = repository;
        this.pipelineService = pipelineService;
        this.serviceName = serviceName;
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

    @Transactional
    public void create(RawMaterialRequest request) {
        RawMaterial material = createRawMaterial(request);
        material = repository.save(material);

        RawMaterialPayload payload = new RawMaterialPayload(
                material.getId().toString(),
                material.getName(),
                "RAW_MATERIAL",
                new ProducerDTO(serviceName, "Mining-Plant-01"),
                new PurposeDTO("CAR", request.targetComponent(), "Extração base para componentes"),
                List.of() // Raw material não tem componentes filhos
        );

        // Define o Pipeline com tempos (EXTRACTION, INITIAL_PROCESSING, PACKAGING)
        ProductionPipeline pipeline = buildDefaultPipeline();

        // Inicia execução assíncrona (O evento será enviado ao fim do tempo)
        pipelineService.execute(pipeline, payload);
    }

    private RawMaterial createRawMaterial(RawMaterialRequest request) {
        return RawMaterial.builder()
                .name(request.name())
                .type("RAW_MATERIAL")
                .targetComponent(request.targetComponent())
                .quantity(request.quantity())
                .build();
    }
}
