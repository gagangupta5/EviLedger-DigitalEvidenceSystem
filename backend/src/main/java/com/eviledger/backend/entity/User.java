package com.eviledger.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String role;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "uploadedBy")
    private List<Evidence> evidenceList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<AuditLog> auditLogs = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser")
    private List<CustodyRecord> custodyRecordsFrom = new ArrayList<>();

    @OneToMany(mappedBy = "toUser")
    private List<CustodyRecord> custodyRecordsTo = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Evidence> getEvidenceList() {
        return evidenceList;
    }

    public void setEvidenceList(List<Evidence> evidenceList) {
        this.evidenceList = evidenceList;
    }

    public List<AuditLog> getAuditLogs() {
        return auditLogs;
    }

    public void setAuditLogs(List<AuditLog> auditLogs) {
        this.auditLogs = auditLogs;
    }

    public List<CustodyRecord> getCustodyRecordsFrom() {
        return custodyRecordsFrom;
    }

    public void setCustodyRecordsFrom(List<CustodyRecord> custodyRecordsFrom) {
        this.custodyRecordsFrom = custodyRecordsFrom;
    }

    public List<CustodyRecord> getCustodyRecordsTo() {
        return custodyRecordsTo;
    }

    public void setCustodyRecordsTo(List<CustodyRecord> custodyRecordsTo) {
        this.custodyRecordsTo = custodyRecordsTo;
    }
}