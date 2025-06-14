package me.seoyeon.api.controller;

import me.seoyeon.api.dto.NotionDatabasePageResponse;
import me.seoyeon.api.dto.PageFilter;
import me.seoyeon.api.service.NotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class NotionController {

  private final NotionService notionService;

  public NotionController(NotionService notionService) {
    this.notionService = notionService;
  }

  @PostMapping("/notion/databases/{databaseId}/query")
  ResponseEntity<NotionDatabasePageResponse> queryDBPages(
      @PathVariable String databaseId, @RequestBody PageFilter request) {
    NotionDatabasePageResponse notionDatabaseQueryResponse = notionService.queryDBPages(databaseId, request);
    return ResponseEntity.ok(notionDatabaseQueryResponse);
  }
}