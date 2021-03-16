package com.cev.empresa.service;

import com.cev.empresa.domain.Pintor;
import com.cev.empresa.repository.PintorRepository;
import com.cev.empresa.repository.search.PintorSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Pintor}.
 */
@Service
@Transactional
public class PintorService {

    private final Logger log = LoggerFactory.getLogger(PintorService.class);

    private final PintorRepository pintorRepository;

    private final PintorSearchRepository pintorSearchRepository;

    public PintorService(PintorRepository pintorRepository, PintorSearchRepository pintorSearchRepository) {
        this.pintorRepository = pintorRepository;
        this.pintorSearchRepository = pintorSearchRepository;
    }

    /**
     * Save a pintor.
     *
     * @param pintor the entity to save.
     * @return the persisted entity.
     */
    public Pintor save(Pintor pintor) {
        log.debug("Request to save Pintor : {}", pintor);
        Pintor result = pintorRepository.save(pintor);
        pintorSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the pintors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Pintor> findAll(Pageable pageable) {
        log.debug("Request to get all Pintors");
        return pintorRepository.findAll(pageable);
    }


    /**
     * Get one pintor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Pintor> findOne(Long id) {
        log.debug("Request to get Pintor : {}", id);
        return pintorRepository.findById(id);
    }

    /**
     * Delete the pintor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Pintor : {}", id);
        pintorRepository.deleteById(id);
        pintorSearchRepository.deleteById(id);
    }

    /**
     * Search for the pintor corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Pintor> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Pintors for query {}", query);
        return pintorSearchRepository.search(queryStringQuery(query), pageable);    }
}
