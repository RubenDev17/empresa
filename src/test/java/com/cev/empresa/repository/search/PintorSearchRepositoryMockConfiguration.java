package com.cev.empresa.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PintorSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PintorSearchRepositoryMockConfiguration {

    @MockBean
    private PintorSearchRepository mockPintorSearchRepository;

}
