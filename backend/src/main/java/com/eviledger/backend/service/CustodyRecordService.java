package com.eviledger.backend.service;

import com.eviledger.backend.entity.CustodyRecord;
import com.eviledger.backend.repository.CustodyRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustodyRecordService {

    private final CustodyRecordRepository custodyRecordRepository;

    public CustodyRecordService(CustodyRecordRepository custodyRecordRepository) {
        this.custodyRecordRepository = custodyRecordRepository;
    }

    public CustodyRecord createCustodyRecord(CustodyRecord custodyRecord) {
        custodyRecord.setTimestamp(LocalDateTime.now());
        return custodyRecordRepository.save(custodyRecord);
    }

    public List<CustodyRecord> getAllCustodyRecords() {
        return custodyRecordRepository.findAll();
    }

    public Optional<CustodyRecord> getCustodyRecordById(Long id) {
        return custodyRecordRepository.findById(id);
    }

    public CustodyRecord updateCustodyRecord(
            Long id,
            CustodyRecord updatedRecord
    ) {
        CustodyRecord existingRecord = custodyRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Custody record not found"));

        existingRecord.setEvidence(updatedRecord.getEvidence());
        existingRecord.setFromUser(updatedRecord.getFromUser());
        existingRecord.setToUser(updatedRecord.getToUser());
        existingRecord.setAction(updatedRecord.getAction());
        existingRecord.setRemarks(updatedRecord.getRemarks());

        return custodyRecordRepository.save(existingRecord);
    }

    public void deleteCustodyRecord(Long id) {
        custodyRecordRepository.deleteById(id);
    }
}