# Airflow 연동을 위한 "마이크로 REST API"

- 각 API 는 작고 명확한 하나의 기능만 수행

## 1. Notion 특정 데이터베이스에서 페이지 목록 검색 조회 API

- Endpoint: `POST` /api/v1/notion/databases/{{databaseId}}/query
- 역할: 검색 조건에 따라 데이터베이스에 속한 모든 페이지의 링크와 기본 정보(제목 등) 목록을 반환한다.
  이 API는 Airflow DAG의 시작점에서 어떤 페이지들을 처리할지 결정하는 데 사용된다.

## 2. Notion 페이지 콘텐츠 조회 API

- Endpoint: `GET` /api/v1/notion/pages/{{pageId}}/content
- 역할: 페이지 ID 하나를 받아 해당 페이지의 전체 텍스트 콘텐츠를 반환한다.

## 3. LLM 처리 API

- Endpoint: `POST` /api/v1/llm/process
- Request Body:
  ```json
  {
    "title": "Map 인터페이스",
    "link": "[페이지 링크 URL]",
    "promptType": "summary-with-question" // 추후 다양한 프롬프트 지원
  }
  ```
- 역할: 해당 노션 페이지 링크를 읽고 주어진 프롬프트(sysMsg)에 맞춰 Gemini API 로 처리하고,
  결과 텍스트를 반환한다. 이 API는 Notion 이나 Slack 에 대해 전혀 알 필요가 없는, 순수한 LLM 처리 기능만 담당.

## 4. Slack 알림 전송 API

- Endpoint: `POST` /api/v1/notify/slack
- Request Body:
    ```json
    {
      "title": "Map 인터페이스",
      "link": "https://www.notion.so/xxx",
      "summary": "...",
      "question": "...",
      "empathy": "...",
      "channel": "HA2343439TU",
      "message": "LLM이 처리한 결과 메시지"
    }
    ```
- 역할: 지정된 채널에 주어진 메시지를 전송한다

# 비교: 기존 단일 API 처리 vs Airflow 연동

| 항목     | 기존 방식 (Monolithic API)  | Airflow 연동방식 (Micro-APIs)                        |
|--------|-------------------------|--------------------------------------------------|
| 호출 방식  | 단일 API 호출로 모든 작업 트리거    | Airflow 가 각 단계별 API를 순차적/병렬적으로 호출하며 워크폴루우 조립     |
| 재시도 단위 | 워크 플로우 전체 (처음부터 다시)     | 실패한 개별 태스크 (API호출)만 재시도                          |
| 역할 분리  | Spring Boot 앱에 모든 책임 집중 | Airflow: 오케스트레이션 / Spring Boot: 실제 작업 수행(Worker) |

# Airflow DAG 예시

```python
with DAG(...) as dag:
    fetch_pages = PythonOperator(task_id="fetch_pages", ...)
    extract_content = PythonOperator(task_id="extract_content", ...)
    analyze_llm = PythonOperator(task_id="analyze_llm", ...)
    send_slack = PythonOperator(task_id="send_slack", ...)

    fetch_pages >> extract_content >> analyze_llm >> send_slack
```