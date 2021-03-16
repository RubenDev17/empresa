package com.cev.empresa.web.rest;

import com.cev.empresa.EmpresaApp;
import com.cev.empresa.domain.Pintor;
import com.cev.empresa.repository.PintorRepository;
import com.cev.empresa.repository.search.PintorSearchRepository;
import com.cev.empresa.service.PintorService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PintorResource} REST controller.
 */
@SpringBootTest(classes = EmpresaApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PintorResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SUELDO = 1;
    private static final Integer UPDATED_SUELDO = 2;

    private static final Integer DEFAULT_EXPERIENCIA = 1;
    private static final Integer UPDATED_EXPERIENCIA = 2;

    @Autowired
    private PintorRepository pintorRepository;

    @Autowired
    private PintorService pintorService;

    /**
     * This repository is mocked in the com.cev.empresa.repository.search test package.
     *
     * @see com.cev.empresa.repository.search.PintorSearchRepositoryMockConfiguration
     */
    @Autowired
    private PintorSearchRepository mockPintorSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPintorMockMvc;

    private Pintor pintor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pintor createEntity(EntityManager em) {
        Pintor pintor = new Pintor()
            .nombre(DEFAULT_NOMBRE)
            .apellidos(DEFAULT_APELLIDOS)
            .sueldo(DEFAULT_SUELDO)
            .experiencia(DEFAULT_EXPERIENCIA);
        return pintor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pintor createUpdatedEntity(EntityManager em) {
        Pintor pintor = new Pintor()
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .sueldo(UPDATED_SUELDO)
            .experiencia(UPDATED_EXPERIENCIA);
        return pintor;
    }

    @BeforeEach
    public void initTest() {
        pintor = createEntity(em);
    }

    @Test
    @Transactional
    public void createPintor() throws Exception {
        int databaseSizeBeforeCreate = pintorRepository.findAll().size();
        // Create the Pintor
        restPintorMockMvc.perform(post("/api/pintors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pintor)))
            .andExpect(status().isCreated());

        // Validate the Pintor in the database
        List<Pintor> pintorList = pintorRepository.findAll();
        assertThat(pintorList).hasSize(databaseSizeBeforeCreate + 1);
        Pintor testPintor = pintorList.get(pintorList.size() - 1);
        assertThat(testPintor.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPintor.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testPintor.getSueldo()).isEqualTo(DEFAULT_SUELDO);
        assertThat(testPintor.getExperiencia()).isEqualTo(DEFAULT_EXPERIENCIA);

        // Validate the Pintor in Elasticsearch
        verify(mockPintorSearchRepository, times(1)).save(testPintor);
    }

    @Test
    @Transactional
    public void createPintorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pintorRepository.findAll().size();

        // Create the Pintor with an existing ID
        pintor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPintorMockMvc.perform(post("/api/pintors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pintor)))
            .andExpect(status().isBadRequest());

        // Validate the Pintor in the database
        List<Pintor> pintorList = pintorRepository.findAll();
        assertThat(pintorList).hasSize(databaseSizeBeforeCreate);

        // Validate the Pintor in Elasticsearch
        verify(mockPintorSearchRepository, times(0)).save(pintor);
    }


    @Test
    @Transactional
    public void getAllPintors() throws Exception {
        // Initialize the database
        pintorRepository.saveAndFlush(pintor);

        // Get all the pintorList
        restPintorMockMvc.perform(get("/api/pintors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pintor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].sueldo").value(hasItem(DEFAULT_SUELDO)))
            .andExpect(jsonPath("$.[*].experiencia").value(hasItem(DEFAULT_EXPERIENCIA)));
    }
    
    @Test
    @Transactional
    public void getPintor() throws Exception {
        // Initialize the database
        pintorRepository.saveAndFlush(pintor);

        // Get the pintor
        restPintorMockMvc.perform(get("/api/pintors/{id}", pintor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pintor.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.sueldo").value(DEFAULT_SUELDO))
            .andExpect(jsonPath("$.experiencia").value(DEFAULT_EXPERIENCIA));
    }
    @Test
    @Transactional
    public void getNonExistingPintor() throws Exception {
        // Get the pintor
        restPintorMockMvc.perform(get("/api/pintors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePintor() throws Exception {
        // Initialize the database
        pintorService.save(pintor);

        int databaseSizeBeforeUpdate = pintorRepository.findAll().size();

        // Update the pintor
        Pintor updatedPintor = pintorRepository.findById(pintor.getId()).get();
        // Disconnect from session so that the updates on updatedPintor are not directly saved in db
        em.detach(updatedPintor);
        updatedPintor
            .nombre(UPDATED_NOMBRE)
            .apellidos(UPDATED_APELLIDOS)
            .sueldo(UPDATED_SUELDO)
            .experiencia(UPDATED_EXPERIENCIA);

        restPintorMockMvc.perform(put("/api/pintors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPintor)))
            .andExpect(status().isOk());

        // Validate the Pintor in the database
        List<Pintor> pintorList = pintorRepository.findAll();
        assertThat(pintorList).hasSize(databaseSizeBeforeUpdate);
        Pintor testPintor = pintorList.get(pintorList.size() - 1);
        assertThat(testPintor.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPintor.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPintor.getSueldo()).isEqualTo(UPDATED_SUELDO);
        assertThat(testPintor.getExperiencia()).isEqualTo(UPDATED_EXPERIENCIA);

        // Validate the Pintor in Elasticsearch
        verify(mockPintorSearchRepository, times(2)).save(testPintor);
    }

    @Test
    @Transactional
    public void updateNonExistingPintor() throws Exception {
        int databaseSizeBeforeUpdate = pintorRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPintorMockMvc.perform(put("/api/pintors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(pintor)))
            .andExpect(status().isBadRequest());

        // Validate the Pintor in the database
        List<Pintor> pintorList = pintorRepository.findAll();
        assertThat(pintorList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Pintor in Elasticsearch
        verify(mockPintorSearchRepository, times(0)).save(pintor);
    }

    @Test
    @Transactional
    public void deletePintor() throws Exception {
        // Initialize the database
        pintorService.save(pintor);

        int databaseSizeBeforeDelete = pintorRepository.findAll().size();

        // Delete the pintor
        restPintorMockMvc.perform(delete("/api/pintors/{id}", pintor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pintor> pintorList = pintorRepository.findAll();
        assertThat(pintorList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Pintor in Elasticsearch
        verify(mockPintorSearchRepository, times(1)).deleteById(pintor.getId());
    }

    @Test
    @Transactional
    public void searchPintor() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        pintorService.save(pintor);
        when(mockPintorSearchRepository.search(queryStringQuery("id:" + pintor.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(pintor), PageRequest.of(0, 1), 1));

        // Search the pintor
        restPintorMockMvc.perform(get("/api/_search/pintors?query=id:" + pintor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pintor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].sueldo").value(hasItem(DEFAULT_SUELDO)))
            .andExpect(jsonPath("$.[*].experiencia").value(hasItem(DEFAULT_EXPERIENCIA)));
    }
}
