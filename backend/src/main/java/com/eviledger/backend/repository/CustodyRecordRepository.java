package com.eviledger.backend.repository;

import com.eviledger.backend.entity.CustodyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustodyRecordRepository extends JpaRepository<CustodyRecord, Long> {
}