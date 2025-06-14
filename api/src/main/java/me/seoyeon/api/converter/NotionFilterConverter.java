package me.seoyeon.api.converter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import me.seoyeon.api.dto.PageFilter;

public class NotionFilterConverter {

  public static Map<String, Object> convert(PageFilter filter) {
    HashMap<String, Object> notionQueryBody = new HashMap<>();
    List<Map<String, Object>> filterConditions = new ArrayList<>();
    // 날짜 범위 필터 변환
    if (filter.getCreatedTime() != null) {
      filterConditions.add(timestampFilter("created_time", filter.getCreatedTime()));
    }
    if (filter.getLastEditedTime() != null) {
      filterConditions.add(timestampFilter("last_edited_time", filter.getLastEditedTime()));
    }

    // 모든 필터 조건을 "and" 로 묶어 최종 filter 객체 생성
    if (!filterConditions.isEmpty()) {
      notionQueryBody.put("filter", Map.of("and", filterConditions));
    }

    return notionQueryBody;
  }

  private static Map<String, Object> timestampFilter(String timestampType, ZonedDateTime dateTime) {
    // KST -> UTC 로 변환
    String utcTime = dateTime.withZoneSameInstant(ZoneOffset.UTC).toString();
    // 순서를 보장하는 LinkedHashMap 생성
    Map<String, Object> orderedMap = new LinkedHashMap<>();

    // 1. 원하는 순서대로 'timestamp'를 먼저 추가합니다.
    orderedMap.put("timestamp", timestampType);

    // 2. 다음 요소를 추가합니다.
    orderedMap.put(timestampType, Map.of("on_or_after", utcTime));

    return orderedMap;
  }
}
