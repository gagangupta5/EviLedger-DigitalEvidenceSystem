package com.eviledger.backend.controller;

import com.eviledger.backend.entity.CustodyRecord;
import com.eviledger.backend.service.CustodyRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/custody-records")
public class CustodyRecordController {

    private final CustodyRecordService custodyRecordService;

    public CustodyRecordController(
            CustodyRecordService custodyRecordService) {
        this.custodyRecordService = custodyRecordService;
    }

    @PostMapping
    public ResponseEntity<CustodyRecord> createCustodyRecord(
            @RequestBody CustodyRecord custodyRecord) {
        return ResponseEntity.ok(
                custodyRecordService.createCustodyRecord(custodyRecord)
        );
    }

    @GetMapping
    public ResponseEntity<List<CustodyRecord>> getAllCustodyRecords() {
        return ResponseEntity.ok(
                custodyRecordService.getAllCustodyRecords()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustodyRecord> getCustodyRecordById(
            @PathVariable Long id) {
        return custodyRecordService.getCustodyRecordById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustodyRecord> updateCustodyRecord(
            @PathVariable Long id,
            @RequestBody CustodyRecord custodyRecord) {
        return ResponseEntity.ok(
                custodyRecordService.updateCustodyRecord(
                        id,
                        custodyRecord
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustodyRecord(
            @PathVariable Long id) {
        custodyRecordService.deleteCustodyRecord(id);
        return ResponseEntity.noContent().build();
    }
}