package com.ExempleRailway.repository;

import com.ExempleRailway.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, UUID> {
}
