package com.cev.empresa.repository.search;

import com.cev.empresa.domain.Pintor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Spring Data Elasticsearch repository for the {@link Pintor} entity.
 */
public interface PintorSearchRepository extends ElasticsearchRepository<Pintor, Long> {
}
