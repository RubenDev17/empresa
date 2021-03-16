package com.cev.empresa.web.rest;

import com.cev.empresa.domain.Pintor;
import com.cev.empresa.service.PintorService;
import com.cev.empresa.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.cev.empresa.domain.Pintor}.
 */
@RestController
@RequestMapping("/api")
public class PintorResource {

    private final Logger log = LoggerFactory.getLogger(PintorResource.class);

    private static final String ENTITY_NAME = "pintor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PintorService pintorService;

    public PintorResource(PintorService pintorService) {
        this.pintorService = pintorService;
    }

    /**
     * {@code POST  /pintors} : Create a new pintor.
     *
     * @param pintor the pintor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pintor, or with status {@code 400 (Bad Request)} if the pintor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pintors")
    public ResponseEntity<Pintor> createPintor(@Valid @RequestBody Pintor pintor) throws URISyntaxException {
        log.debug("REST request to save Pintor : {}", pintor);
        if (pintor.getId() != null) {
            throw new BadRequestAlertException("A new pintor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pintor result = pintorService.save(pintor);
        return ResponseEntity.created(new URI("/api/pintors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pintors} : Updates an existing pintor.
     *
     * @param pintor the pintor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pintor,
     * or with status {@code 400 (Bad Request)} if the pintor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pintor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pintors")
    public ResponseEntity<Pintor> updatePintor(@Valid @RequestBody Pintor pintor) throws URISyntaxException {
        log.debug("REST request to update Pintor : {}", pintor);
        if (pintor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pintor result = pintorService.save(pintor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, pintor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pintors} : get all the pintors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pintors in body.
     */
    @GetMapping("/pintors")
    public ResponseEntity<List<Pintor>> getAllPintors(Pageable pageable) {
        log.debug("REST request to get a page of Pintors");
        Page<Pintor> page = pintorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pintors/:id} : get the "id" pintor.
     *
     * @param id the id of the pintor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pintor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pintors/{id}")
    public ResponseEntity<Pintor> getPintor(@PathVariable Long id) {
        log.debug("REST request to get Pintor : {}", id);
        Optional<Pintor> pintor = pintorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pintor);
    }

    /**
     * {@code DELETE  /pintors/:id} : delete the "id" pintor.
     *
     * @param id the id of the pintor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pintors/{id}")
    public ResponseEntity<Void> deletePintor(@PathVariable Long id) {
        log.debug("REST request to delete Pintor : {}", id);
        pintorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pintors?query=:query} : search for the pintor corresponding
     * to the query.
     *
     * @param query the query of the pintor search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/pintors")
    public ResponseEntity<List<Pintor>> searchPintors(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Pintors for query {}", query);
        Page<Pintor> page = pintorService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
