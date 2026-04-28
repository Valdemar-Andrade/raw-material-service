package com.projeto.rawmaterialservice.controller;

import com.projeto.rawmaterialservice.dto.RawMaterialRequest;
import com.projeto.rawmaterialservice.service.RawMaterialService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/raw-material")
public class RawMaterialController {

    private final RawMaterialService service;

    public RawMaterialController(RawMaterialService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody RawMaterialRequest request) {

        service.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body("Matéria Prima criada com sucesso");
    }
}
