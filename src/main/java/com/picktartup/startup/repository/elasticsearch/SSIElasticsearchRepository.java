package com.picktartup.startup.repository.elasticsearch;

import com.picktartup.startup.dto.SSIElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SSIElasticsearchRepository extends ElasticsearchRepository<SSIElasticsearch, Long> {
    List<SSIElasticsearch> findByStartupId(Long startupId);
}
