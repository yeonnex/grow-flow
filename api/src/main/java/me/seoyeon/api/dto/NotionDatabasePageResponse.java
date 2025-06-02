package me.seoyeon.api.dto;

import java.util.List;

public record NotionDatabasePageResponse(List<NotionPage> results, boolean hasMore, String nextCursor) {
    public record NotionPage(String title, String link) {}
}
