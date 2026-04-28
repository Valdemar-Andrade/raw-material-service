package com.projeto.rawmaterialservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RawMaterialRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,

        @NotBlank(message = "Target Component é obrigatório")
        String targetComponent,

        @NotNull(message = "Quantidade é obrigatório")
        @Min(value = 1, message = "Quantidade deve ser maior que 0")
        Integer quantity
) { }
