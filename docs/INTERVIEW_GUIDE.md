# Interview Guide — FinSight AI

## 60-second answer
"FinSight AI is an enterprise financial intelligence platform I built using Java 17 and Spring Boot. I designed it around a real-world finance-data workflow: ERP-style transaction ingestion, REST APIs, SQL persistence, executive KPI reporting, anomaly detection, revenue forecasting, role-based access control and audit logging. The React dashboard consumes the backend APIs. I also added JUnit tests, Docker and GitHub Actions so the project demonstrates the complete software-development lifecycle rather than only a UI."

## Problem statement
Finance teams need timely answers about revenue, expenses, profitability and unusual transactions. Data may originate in multiple ERP systems. The platform centralizes transaction data and provides reporting plus AI-assisted signals.

## Architecture explanation
"I used a layered architecture: controller for HTTP/API concerns, service for business logic, repository for persistence, DTOs for API contracts and separate security/audit services. This keeps responsibilities isolated and makes unit testing easier."

## Why Java/Spring Boot?
"Java gives strong typing, mature collections and enterprise tooling. Spring Boot reduces configuration and provides production-ready REST, security, validation and data-access support."

## Why PostgreSQL?
"Financial data is structured and transactional, so a relational database provides ACID transactions, constraints, indexing and reliable aggregation. PostgreSQL is the production profile while H2 makes local demos fast."

## Explain anomaly detection
"For the demo I used a transparent statistical baseline. Transactions are grouped by category, then I calculate mean and standard deviation. A z-score above an absolute threshold is flagged, with an additional high-value rule. I deliberately kept the rule explainable. In production I would evaluate a versioned ML model against precision, recall and false-positive cost before replacing the baseline."

## Explain forecasting
"The demo forecast uses a six-month moving average so the behavior is easy to explain and test. The service is isolated so a time-series model can replace it later without changing the dashboard contract."

## Security
"Login returns a JWT. The JWT filter validates the token on subsequent requests. BCrypt stores passwords as hashes. Method-level security restricts transaction mutation to Finance Analyst/Admin and deletion to Admin."

## Testing
Be ready to explain unit tests, integration tests, API tests, regression testing and why deterministic business logic is easier to unit test.

## CI/CD
"GitHub Actions runs Maven tests and the frontend production build on every push/PR. The Docker path packages backend/frontend and PostgreSQL can be deployed on AWS."

## DSA connection
The project uses maps for grouped analytics and Java streams for aggregation. Dynamic search uses JPA Specifications. Be ready to discuss Big-O: grouping is O(n) average, sorting anomalies is O(n log n), and database filtering should use indexes rather than loading everything into memory at scale.

## Key interview questions
1. Why layered architecture?
2. ArrayList vs LinkedList?
3. HashMap internals?
4. equals vs hashCode?
5. Checked vs unchecked exception?
6. What is dependency injection?
7. REST vs SOAP?
8. PUT vs PATCH?
9. JWT vs session authentication?
10. SQL JOIN types?
11. Index trade-offs?
12. ACID properties?
13. Unit vs integration testing?
14. How would you scale anomaly detection to millions of transactions?
15. How would you make ERP ingestion reliable?
16. How would you prevent duplicate imports?
17. How would you monitor the application in AWS?
18. What happens if PostgreSQL goes down?
19. How would you improve forecast accuracy?
20. What was the hardest engineering decision?

## Strong answer for the hardest decision
"I chose an explainable statistical anomaly baseline rather than claiming that a small demo ML model was production-ready. It makes the alert reason auditable. I designed the analytics boundary so a trained model can be introduced later with versioning and evaluation."

## Honest AI-first answer
"I use AI coding assistants as productivity tools, but I treat generated code as a draft. I verify APIs, write tests, review security and understand every important code path."

## 2-minute demo order
1. Login as admin.
2. Show KPI dashboard.
3. Open Transactions and explain ERP source tags.
4. Import a CSV.
5. Open AI Anomalies and explain z-score.
6. Open Forecast.
7. Show Audit Trail.
8. Explain Docker + CI workflow.
