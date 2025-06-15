package me.seoyeon.api.controller;

import jakarta.validation.constraints.NotBlank;
import me.seoyeon.api.dto.NotionDatabasePageResponse;
import me.seoyeon.api.dto.PageFilter;
import me.seoyeon.api.dto.PageMarkdownResponse;
import me.seoyeon.api.service.NotionDatabaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class NotionController {

  private final NotionDatabaseService notionDatabaseService;
  private final NotionPageService notionPageService;

  public NotionController(
      NotionDatabaseService notionService, NotionPageService notionPageService) {
    this.notionDatabaseService = notionService;
    this.notionPageService = notionPageService;
  }

  // Notion DB 검색 조회 API
  @PostMapping("/notion/databases/{databaseId}/query")
  ResponseEntity<NotionDatabasePageResponse> queryDBPages(
      @PathVariable String databaseId, @RequestBody PageFilter request) {
    NotionDatabasePageResponse notionDatabaseQueryResponse =
        notionDatabaseService.queryDBPages(databaseId, request);
    return ResponseEntity.ok(notionDatabaseQueryResponse);
  }

  // Notion Page 마크다운 반환 API
  @GetMapping("/notion/pages/markdown")
  ResponseEntity<PageMarkdownResponse> getPageAsMarkdown(
      @RequestParam("url") @NotBlank(message = "페이지 URL은 비어있을 수 없습니다.") String notionPageUrl) {
    PageMarkdownResponse pageAsMarkdown = notionPageService.getPageAsMarkdown(notionPageUrl);
    return ResponseEntity.ok(pageAsMarkdown);
  }
}
