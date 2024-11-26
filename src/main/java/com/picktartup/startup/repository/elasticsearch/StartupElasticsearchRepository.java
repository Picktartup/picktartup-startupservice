package com.picktartup.startup.repository.elasticsearch;

import com.picktartup.startup.dto.StartupElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StartupElasticsearchRepository extends ElasticsearchRepository<StartupElasticsearch, Long> {
    List<StartupElasticsearch> findByNameContainingOrDescriptionContaining(String nameKeyword, String descriptionKeyword);

}
