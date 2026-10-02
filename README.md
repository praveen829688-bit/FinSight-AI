# FinSight AI 2.0 — Enterprise Financial Intelligence Platform

**Interview-focused portfolio project aligned to the insightsoftware Associate Software Engineer / Intern role.**

FinSight AI demonstrates the complete software-engineering lifecycle: Java 17, Spring Boot, REST, OOP, SQL/JPA, data modelling, JUnit, API validation, JWT/RBAC security, ERP-style ingestion, analytics, anomaly detection, forecasting, audit logging, React, Docker and GitHub Actions CI.

## Why this project matches the role
The target role asks candidates to design/develop/debug software, automate functional/integration/regression testing, work with CI/CD and AWS, participate in code reviews and Agile delivery, and understand general-purpose programming, SQL/PLSQL, databases/data modelling, APIs, testing, OOAD and design patterns. FinSight intentionally gives you a concrete story for each area.

## Core features
- Executive financial dashboard: revenue, expenses, profit, margin, transaction count and AI alerts
- ERP-style ingestion: SAP, Oracle and Dynamics source tags + CSV import
- Transaction search/filter and CRUD APIs
- Explainable AI anomaly detection using category-level z-score + high-value rule
- Revenue forecasting with a six-month moving-average baseline
- JWT authentication + BCrypt + role-based authorization
- Admin and Finance Analyst roles
- Audit trail for create/update/delete/import actions
- CSV export
- H2 local demo + PostgreSQL production profile + Flyway migration
- JUnit tests
- Docker Compose for PostgreSQL/backend/frontend
- GitHub Actions CI for backend tests and frontend build
- Responsive React executive dashboard

## Quick start
### Backend
```bash
cd backend
mvn spring-boot:run
```
Open http://localhost:8080/api/health

Demo users:
- admin / Admin@123
- analyst / Analyst@123

### Frontend
```bash
cd frontend
npm install
npm run dev
```
Open http://localhost:5173

### Docker
```bash
docker compose up --build
```

## CSV format
See `docs/DEMO_DATA.csv`.

## API highlights
- `POST /api/auth/login`
- `GET /api/dashboard/kpis`
- `GET /api/dashboard/trend`
- `GET /api/dashboard/category-spend`
- `GET /api/transactions`
- `POST /api/transactions`
- `PUT /api/transactions/{id}`
- `DELETE /api/transactions/{id}`
- `POST /api/transactions/import`
- `GET /api/analytics/anomalies`
- `GET /api/analytics/forecast?months=6`
- `GET /api/reports/export/transactions`
- `GET /api/dashboard/audit`

## Interview rule
Do not claim features you did not implement. Explain the demo as implemented, then clearly separate production extensions. The `docs/INTERVIEW_GUIDE.md` contains the exact story, architecture explanation, technical questions and a 2-minute demo flow.
