package com.picktartup.startup.repository.elasticsearch;

import com.picktartup.startup.entity.ElasticsearchStartupEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StartupElasticsearchRepository extends ElasticsearchRepository<ElasticsearchStartupEntity, String> {
    List<ElasticsearchStartupEntity> findByNameContainingOrDescriptionContaining(String nameKeyword, String descriptionKeyword);
}
