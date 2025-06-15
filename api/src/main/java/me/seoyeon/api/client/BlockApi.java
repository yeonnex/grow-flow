package me.seoyeon.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class BlockApi {
  private final Logger logger = LoggerFactory.getLogger(BlockApi.class);
  private final NotionHttpExecutor executor;
  private final ObjectMapper objectMapper;

  public BlockApi(NotionHttpExecutor executor, ObjectMapper objectMapper) {
    this.executor = executor;
    this.objectMapper = objectMapper;
  }

  /**
   * Notion 이 제공하는 "GET/v1/blocks/{block_id}/children" API는 설계 자체가 해당 블록의 직계 자식(1단계 깊이)까지만 반환하도록 되어
   * 있다. 따라서 직계 자식의 자식 블록들을 가져오려면 추가적인 API 호출이 필요하다. 해결책: 재귀 호출을 통한 전체 블록 트리 순회 - 전체 페이지를 하나의 완전한
   * 문서로 보기 위해, 다음 로직을 애플리케이션에 구현해야 한다. 1. 최상위 페이지 ID로 시작하여 1단계 자식 블록 목록을 가져온다. 2. 가져온 각 블록에 대해
   * has_children 속성이 true 인지 확인한다. 3. 이 과정을 has_children 속성이 false 인 블록을 만날때까지 반복하여 모든 블록을 수집한다.
   * 서비스 클래스(예: NotionPageService)가 재귀 호출을 지휘하고, BlocksApi는 API 통신만 담당하도록 역할을 명확히 분리하자.
   *
   * <p>본 클래스(BlockApi)가 한 번의 호출로 특정 블록의 모든 직계 자식들을 가져오도록 페이지네이션을 처리하는 로직을 추가하자. Notion API는 한 번에 최대
   * 100개의 블록만 반환하기 때문/
   *
   * @param blockId
   * @return
   */
  /*    public List<BlockResponse> retrieveChildren(String blockId) {
      //TODO
  }*/
}
