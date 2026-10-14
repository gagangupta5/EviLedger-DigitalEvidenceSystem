package com.eviledger.backend.controller;

import com.eviledger.backend.entity.Evidence;
import com.eviledger.backend.service.EvidenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evidence")
public class EvidenceController {

    private final EvidenceService evidenceService;

    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @PostMapping
    public ResponseEntity<Evidence> createEvidence(
            @RequestBody Evidence evidence) {
        return ResponseEntity.ok(evidenceService.createEvidence(evidence));
    }

    @GetMapping
    public ResponseEntity<List<Evidence>> getAllEvidence() {
        return ResponseEntity.ok(evidenceService.getAllEvidence());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evidence> getEvidenceById(
            @PathVariable Long id) {
        return evidenceService.getEvidenceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evidence> updateEvidence(
            @PathVariable Long id,
            @RequestBody Evidence evidence) {
        return ResponseEntity.ok(
                evidenceService.updateEvidence(id, evidence)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvidence(
            @PathVariable Long id) {
        evidenceService.deleteEvidence(id);
        return ResponseEntity.noContent().build();
    }
}