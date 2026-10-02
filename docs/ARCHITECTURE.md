# FinSight AI Architecture

## Business problem
Finance and data teams often work across ERP systems and need reliable reporting, anomaly monitoring, forecasting, auditability and decision support.

## Architecture
```text
React + Recharts
      |
      | HTTPS/REST + JWT
      v
Spring Boot 3
  |-- Auth/Security (JWT + RBAC)
  |-- Controllers (REST)
  |-- Services (business rules)
  |-- Analytics (KPIs, z-score anomalies, forecast)
  |-- Audit service
  |-- CSV ERP ingestion
      |
      v
JPA/Hibernate
      |
      +---- H2 (local demo)
      +---- PostgreSQL (production profile)

CI: GitHub Actions -> Maven tests -> frontend build
Deployment path: Docker -> AWS
```

## Design patterns demonstrated
- Layered architecture
- Repository pattern through Spring Data JPA
- DTO pattern for API contracts
- Strategy-ready analytics service (forecasting can be swapped for ML)
- Dependency injection
- Specification pattern for dynamic transaction filtering

## Security
JWT authentication, BCrypt password hashing and role-based authorization. Roles: ADMIN and FINANCE_ANALYST. Production should use a secret manager and rotate JWT keys.

## Data flow
1. ERP-style transaction enters via REST or CSV.
2. Validation occurs at API boundary.
3. Service persists through JPA.
4. Audit event is recorded.
5. Analytics engine recalculates KPI/anomaly state.
6. React dashboard requests only the required aggregates.
