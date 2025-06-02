package me.seoyeon.api.dto;

import java.time.LocalDateTime;

public record NotionDBPageSearchRequest(
        LocalDateTime fromDateTime,
        LocalDateTime to
) {}


