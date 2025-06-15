package me.seoyeon.api.controller;

import me.seoyeon.api.dto.PageMarkdownResponse;
import me.seoyeon.api.utils.UrlUtils;
import org.springframework.stereotype.Service;

@Service
public class NotionPageService {

  public PageMarkdownResponse getPageAsMarkdown(String notionPageUrl) {
    String pageId = UrlUtils.extractPageId(notionPageUrl);
    return null;
  }
}
