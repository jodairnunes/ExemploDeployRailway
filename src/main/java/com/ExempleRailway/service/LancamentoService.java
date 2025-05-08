package com.ExempleRailway.service;


import com.ExempleRailway.dto.LancamentoDTO;
import com.ExempleRailway.model.Lancamento;
import com.ExempleRailway.repository.LancamentoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LancamentoService {

    private final LancamentoRepository repository;

    public LancamentoService(LancamentoRepository repository) {
        this.repository = repository;
    }

    public List<LancamentoDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public ResponseEntity<LancamentoDTO> buscarPorId(UUID id) {
        return repository.findById(id)
                .map(l -> ResponseEntity.ok(toDTO(l)))
                .orElse(ResponseEntity.notFound().build());
    }

    public LancamentoDTO criar(LancamentoDTO dto) {
        Lancamento l = toEntity(dto);
        return toDTO(repository.save(l));
    }

    public ResponseEntity<LancamentoDTO> atualizar(UUID id, LancamentoDTO dto) {
        Optional<Lancamento> opt = repository.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        Lancamento l = opt.get();
        l.setDescription(dto.getDescription());
        l.setValue(dto.getValue());
        l.setType(dto.getType());
        return ResponseEntity.ok(toDTO(repository.save(l)));
    }

    public ResponseEntity<Void> deletar(UUID id) {
        if (!repository.existsById(id)) return ResponseEntity.notFound().build();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private LancamentoDTO toDTO(Lancamento l) {
        LancamentoDTO dto = new LancamentoDTO();
        dto.setId(l.getId());
        dto.setDescription(l.getDescription());
        dto.setValue(l.getValue());
        dto.setType(l.getType());
        return dto;
    }

    private Lancamento toEntity(LancamentoDTO dto) {
        Lancamento l = new Lancamento();
        l.setDescription(dto.getDescription());
        l.setValue(dto.getValue());
        l.setType(dto.getType());
        return l;
    }
}
