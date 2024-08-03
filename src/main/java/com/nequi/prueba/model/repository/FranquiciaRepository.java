package com.nequi.prueba.model.repository;

import com.nequi.prueba.model.entity.FranquiciaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface FranquiciaRepository extends ReactiveCrudRepository<FranquiciaEntity, UUID> {
}
