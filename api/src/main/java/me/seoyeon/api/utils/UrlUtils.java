package me.seoyeon.api.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import me.seoyeon.api.exception.ErrorCode;
import me.seoyeon.api.exception.NotionException;

public final class UrlUtils {
  // Notion 페이지 ID는 32자리의 16진수 문자열임.
  private static final Pattern NOTION_PAGE_ID_PATTERN =
      Pattern.compile(".*-([a-f0-9]{32})(?:\\?.*)?$");

  /**
   * Notion 페이지 URL에서 32자리의 페이지 ID를 추출한다. 예:
   * "https://www.notion.so/HashMap-213c3be34a468052ba14dd77c0d8c80d" ->
   * "213c3be34a468052ba14dd77c0d8c80d"
   *
   * @param notionUrl Notion 페이지의 전체 URL
   * @return 추출된 페이지 ID, 찾지 못하면 예외 400대 에러 발생
   */
  public static String extractPageId(String notionUrl) {
    Matcher matcher = NOTION_PAGE_ID_PATTERN.matcher(notionUrl);
    if (matcher.matches()) {
      return matcher.group(1); // 첫번째 캡쳐 그룹(ID) 반환
    }
    throw new NotionException.InvalidPageIdException(ErrorCode.INVALID_NOTION_PAGE_ID);
  }
}
