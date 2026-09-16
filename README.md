# EviLedger

**Blockchain-Based Digital Evidence Management System**

EviLedger is a web-based digital evidence management system built to preserve evidence integrity and maintain a traceable chain of custody. It combines cryptographic hashing, content-addressed file storage, a relational metadata database, and a blockchain integrity registry.


---

## How It Works

1. A user uploads an evidence file through the React frontend.
2. The Spring Boot backend authenticates the user and validates the upload (type, size).
3. The backend calculates a **SHA-256** hash of the file.
4. The file is stored in **IPFS**, which returns a content identifier (CID).
5. Evidence metadata (hash, CID, uploader, timestamps, status) is stored in **PostgreSQL**.
6. A compact integrity record (evidence ID + hash) is registered on a **blockchain** smart contract.
7. At any later point, the file can be **re-verified**: the system recalculates the hash and compares it against the registered hash. A mismatch flags the evidence as `TAMPERED`.

The complete evidence file is **never** stored on-chain — only a compact, tamper-evident hash/event record.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Frontend | React.js |
| Backend | Spring Boot (Java) |
| Database | PostgreSQL |
| File Storage | IPFS |
| Integrity Hash | SHA-256 |
| Integrity/Audit Ledger | Blockchain (Solidity smart contract, Ethereum-compatible / Ganache for local dev) |
| Build Tool | Gradle |
| API Testing | Postman |
| Backend Testing | JUnit / Spring Boot Test |
| Version Control | Git + GitHub |

---

## Architecture

```
User → React Frontend → Spring Boot REST API → Auth/Authorization → Evidence Service
                                                        │
                          ┌─────────────────────────────┼─────────────────────────────┐
                          ▼                              ▼                              ▼
                     PostgreSQL                        IPFS                    Blockchain Smart Contract
              (metadata, users, custody,        (evidence file content,        (compact hash / event record)
                   audit records)                  content-addressed)
```

**Separation of concerns:**
- **PostgreSQL** — structured application metadata (users, evidence records, custody history, audit logs).
- **IPFS** — the actual evidence file content, referenced by CID.
- **Blockchain** — a small, tamper-evident record of the evidence hash and key events. The file itself is never on-chain.

---

## Features

- User authentication (JWT) with role-based access (`ADMIN`, `INVESTIGATOR`, `ANALYST`, `VIEWER`)
- Evidence upload with server-side validation and SHA-256 hashing
- IPFS-backed file storage with CID tracking
- On-chain hash registration for tamper-evidence
- Hash-based verification (`VERIFIED` / `TAMPERED` status)
- Chain-of-custody transfer tracking
- Audit logging of key security/operational events
- REST API with Postman collection and backend test suite

---

## Project Structure

```
EviLedger/
├── frontend/                  # React application
│   ├── pages/                 # Login, Dashboard, UploadEvidence, EvidenceDetails, VerifyEvidence, CustodyHistory
│   ├── components/            # Navbar, EvidenceCard, UploadForm, StatusBadge, ProtectedRoute
│   ├── services/api/          # Axios/fetch API client
│   ├── hooks/
│   └── utils/
├── backend/                   # Spring Boot application
│   ├── controller/             # REST endpoints
│   ├── service/                # Business logic
│   ├── repository/             # Spring Data JPA repositories
│   ├── entity/                 # JPA entities
│   ├── dto/                    # Request/response objects
│   ├── security/                # JWT / auth / role checks
│   ├── storage/                 # IPFS client
│   ├── blockchain/              # Web3/Ethereum client + contract integration
│   ├── exception/               # Centralized error handling
│   └── config/
├── blockchain/
│   ├── contracts/               # Solidity smart contract(s)
│   └── deploy/                  # Deployment scripts/configuration
├── docs/                        # Architecture, API docs, diagrams
├── .env.example
└── .gitignore
```

---

## Database Schema (PostgreSQL)

| Table | Key Fields | Purpose |
|---|---|---|
| `users` | id, name, email, password_hash, role, created_at | Identity and access control |
| `evidence` | id, evidence_id, file_name, file_type, file_size, sha256_hash, ipfs_cid, uploaded_by, created_at, status, blockchain_tx_hash | Evidence metadata, IPFS reference, integrity info |
| `custody_records` | id, evidence_id, from_user_id, to_user_id, action, timestamp, remarks | Chain-of-custody history |
| `audit_logs` | id, user_id, evidence_id, action, result, timestamp, ip_address | Security/operational audit trail |

---

## REST API

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/register` | Create a user account |
| POST | `/api/auth/login` | Authenticate a user |
| POST | `/api/evidence/upload` | Upload and register evidence |
| GET | `/api/evidence` | List/search evidence |
| GET | `/api/evidence/{id}` | Get evidence details |
| POST | `/api/evidence/{id}/verify` | Verify current evidence integrity |
| POST | `/api/evidence/{id}/custody` | Record a custody transfer/access |
| GET | `/api/evidence/{id}/custody` | Get custody history |
| GET | `/api/audit` | View authorized audit records |

A full Postman collection is available in `docs/`.

---

## Getting Started

### Prerequisites
- Java 17+
- Node.js 18+
- PostgreSQL 14+
- IPFS (local node or IPFS Desktop)
- Ganache (local Ethereum-compatible blockchain for development)
- No separate Gradle install needed — the project uses the Gradle Wrapper (`gradlew`)

### 1. Clone the repository
```bash
git clone https://github.com/gagangupta5/EviLedger-DigitalEvidenceSystem
cd EviLedger
```

### 2. Configure environment variables
Copy `.env.example` to `.env` and fill in real values (never commit the real `.env`):
```
DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=
IPFS_API_URL=
BLOCKCHAIN_RPC_URL=
CONTRACT_ADDRESS=
BLOCKCHAIN_PRIVATE_KEY=   # development only — never commit
CORS_ALLOWED_ORIGINS=
```

### 3. Start PostgreSQL and create the database
```bash
createdb eviledger
```

### 4. Deploy the smart contract (local development)
```bash
cd blockchain
# start Ganache, then deploy using your chosen tooling (e.g. Truffle/Hardhat)
```

### 5. Run the backend
```bash
cd backend
./gradlew bootRun
```

### 6. Run the frontend
```bash
cd frontend
npm install
npm run dev
```

The app should now be available locally, with the React frontend calling the Spring Boot API, which in turn talks to PostgreSQL, IPFS, and the local blockchain network.

---

## Testing

| Area | Coverage |
|---|---|
| Authentication | Valid login, invalid password, expired/invalid token, unauthorized role |
| Upload | Valid file, unsupported file, oversized file, missing file |
| Hashing | Same content → same SHA-256; changed content → different SHA-256 |
| Database | Insert, read, foreign-key validation, transaction behavior |
| IPFS | Successful upload, CID persistence, retrieval failure handling |
| Blockchain | Register hash, verify hash, invalid evidence ID, transaction failure |
| Verification | Matching hash vs. mismatched hash |
| Custody | Valid/unauthorized transfer, history retrieval |
| API | 200/201, 400, 401/403, 404, 500 responses |

Run backend tests:
```bash
cd backend
./gradlew test
```

---

## Security Notes

- Passwords are stored as hashes only (BCrypt) — never in plain text.
- Uploaded files are validated by type and size; filename/MIME type alone is never trusted.
- The SHA-256 hash is always calculated from received file content on the server, not the client.
- IPFS credentials and blockchain private keys are never exposed to the frontend.
- All inputs are validated server-side, even when the frontend already validates them.

---

## Limitations

- This is an academic prototype and has not undergone production-grade security or forensic validation.
- A local blockchain (Ganache) is suitable for development/demo purposes only, not production.
- IPFS content availability depends on proper pinning/storage infrastructure.
- A blockchain record alone does **not** establish legal admissibility — that depends on applicable forensic, procedural, and legal requirements.
- A hash mismatch indicates a content difference; it does not by itself establish who changed a file or why.

---

## Future Scope

- Production-grade IPFS pinning and redundancy
- Encrypted evidence storage with controlled decryption
- Digital signatures in addition to hashing
- Hardware-backed key management
- Permissioned blockchain for organizational environments
- Cloud object storage or hybrid IPFS + object storage
- Advanced audit and analytics dashboard
- Mobile application for field investigators
- Multi-organization / multi-tenant evidence sharing
- Automated evidence classification (subject to forensic validation)
- Stronger malware scanning and file-type validation
- Immutable audit export/report generation
- High-availability deployment and backup/restore strategy

---

## License

Add your chosen license here (e.g., MIT) before publishing publicly.

## Author

Major project — developed and maintained by Gagan Gupta.