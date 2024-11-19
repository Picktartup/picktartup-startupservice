package com.picktartup.startup.service;

import com.picktartup.startup.dto.StartupServiceRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class StartupServiceImplTest {

    @Autowired
    private StartupService startupService;

    @Test
    public void testSearchStartupsByKeyword() {
        // Arrange: Elasticsearch에 데이터를 인덱싱해놓은 상태여야 합니다.
        String keyword = "리클";

        // Act: 키워드를 이용해 검색
        List<StartupServiceRequest> results = startupService.searchStartupsByKeyword(keyword);

        // Assert: 검색 결과가 기대한 대로 반환되는지 확인
        assertFalse(results.isEmpty(), "검색 결과가 비어있지 않아야 합니다.");
        assertTrue(results.stream().anyMatch(r -> r.getName().contains(keyword)), "검색 결과에 해당 키워드가 포함된 항목이 있어야 합니다.");
    }
}
