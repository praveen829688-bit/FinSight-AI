# API demo sequence

1. `POST /api/auth/login`
```json
{"username":"admin","password":"Admin@123"}
```
Copy the returned JWT and use `Authorization: Bearer <token>` for protected endpoints.

2. `GET /api/dashboard/kpis`
3. `GET /api/dashboard/trend`
4. `GET /api/transactions?q=ERP`
5. `GET /api/analytics/anomalies`
6. `GET /api/analytics/forecast?months=6`
7. `GET /api/dashboard/audit`
8. `POST /api/transactions/import` with form-data key `file` and `docs/DEMO_DATA.csv`.
9. `GET /api/reports/export/transactions`
