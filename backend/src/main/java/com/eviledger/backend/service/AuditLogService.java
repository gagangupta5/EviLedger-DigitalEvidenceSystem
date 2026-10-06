package com.eviledger.backend.service;

import com.eviledger.backend.entity.AuditLog;
import com.eviledger.backend.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public AuditLog createAuditLog(AuditLog auditLog) {
        auditLog.setTimestamp(LocalDateTime.now());
        return auditLogRepository.save(auditLog);
    }

    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    public Optional<AuditLog> getAuditLogById(Long id) {
        return auditLogRepository.findById(id);
    }

    public AuditLog updateAuditLog(Long id, AuditLog updatedLog) {
        AuditLog existingLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit log not found"));

        existingLog.setUser(updatedLog.getUser());
        existingLog.setEvidence(updatedLog.getEvidence());
        existingLog.setAction(updatedLog.getAction());
        existingLog.setResult(updatedLog.getResult());
        existingLog.setIpAddress(updatedLog.getIpAddress());

        return auditLogRepository.save(existingLog);
    }

    public void deleteAuditLog(Long id) {
        auditLogRepository.deleteById(id);
    }
}