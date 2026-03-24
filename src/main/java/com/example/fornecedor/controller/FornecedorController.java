package com.example.fornecedor.controller;

import com.example.fornecedor.model.FornecedorModel;
import com.example.fornecedor.service.FornecedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService service;

    public FornecedorController(FornecedorService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FornecedorModel> criar(@RequestBody FornecedorModel fornecedor) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.salvar(fornecedor));
    }

    @GetMapping
    public ResponseEntity<List<FornecedorModel>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FornecedorModel> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (service.buscarPorId(id).isPresent()) {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}