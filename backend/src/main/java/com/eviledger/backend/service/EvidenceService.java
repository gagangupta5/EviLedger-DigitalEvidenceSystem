package com.eviledger.backend.service;

import com.eviledger.backend.entity.Evidence;
import com.eviledger.backend.repository.EvidenceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EvidenceService {

    private final EvidenceRepository evidenceRepository;

    public EvidenceService(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    public Evidence createEvidence(Evidence evidence) {
        evidence.setCreatedAt(LocalDateTime.now());

        if (evidence.getStatus() == null || evidence.getStatus().isBlank()) {
            evidence.setStatus("ACTIVE");
        }

        return evidenceRepository.save(evidence);
    }

    public List<Evidence> getAllEvidence() {
        return evidenceRepository.findAll();
    }

    public Optional<Evidence> getEvidenceById(Long id) {
        return evidenceRepository.findById(id);
    }

    public Evidence updateEvidence(Long id, Evidence updatedEvidence) {
        Evidence existingEvidence = evidenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evidence not found"));

        existingEvidence.setFileName(updatedEvidence.getFileName());
        existingEvidence.setFileType(updatedEvidence.getFileType());
        existingEvidence.setFileSize(updatedEvidence.getFileSize());
        existingEvidence.setSha256Hash(updatedEvidence.getSha256Hash());
        existingEvidence.setIpfsCid(updatedEvidence.getIpfsCid());
        existingEvidence.setStatus(updatedEvidence.getStatus());
        existingEvidence.setBlockchainTxHash(updatedEvidence.getBlockchainTxHash());

        return evidenceRepository.save(existingEvidence);
    }

    public void deleteEvidence(Long id) {
        evidenceRepository.deleteById(id);
    }
}