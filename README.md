# AI Document Processing & Business Automation SaaS

An enterprise-ready SaaS solution for intelligent document ingestion, automated data extraction, and business workflow orchestration.

---

## 🏗️ Architecture Overview

The system is structured as a modular monorepo containing three core services:

```text
.
├── backend/       # Spring Boot 3.5 (Java 21) — Core API & Business Logic
├── ai-service/    # FastAPI (Python 3.13) — Document Parsing & ML Pipelines
├── frontend/      # React + TypeScript + Vite — User Dashboard & Admin Console
└── docker-compose.yml
