package me.seoyeon.api.dto;

import java.time.ZonedDateTime;

public class PageFilter {

  private ZonedDateTime createdTime;
  private ZonedDateTime lastEditedTime;

  // 정렬 조건
  private Sort sortBy;

  public ZonedDateTime getCreatedTime() {
    return createdTime;
  }

  public ZonedDateTime getLastEditedTime() {
    return lastEditedTime;
  }

  public Sort getSortBy() {
    return sortBy;
  }

  public enum Direction {
    ASCENDING,
    DESCENDING
  }

  public static class Sort {
    private String property; // 정렬 기준이 될 속성 이름 (예: "생성 일시")
    private Direction direction; // "ASCENDING" 또는 "DESCENDING"
  }
}
