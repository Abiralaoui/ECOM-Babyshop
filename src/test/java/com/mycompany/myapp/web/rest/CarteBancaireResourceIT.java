package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CarteBancaire;
import com.mycompany.myapp.repository.CarteBancaireRepository;
import com.mycompany.myapp.service.dto.CarteBancaireDTO;
import com.mycompany.myapp.service.mapper.CarteBancaireMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CarteBancaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarteBancaireResourceIT {

    private static final String DEFAULT_NOM_PORTEUR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PORTEUR = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUM_CARTE = 1;
    private static final Integer UPDATED_NUM_CARTE = 2;

    private static final Instant DEFAULT_DATE_EXPIRATION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_EXPIRATION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CVV = 1;
    private static final Integer UPDATED_CVV = 2;

    private static final String ENTITY_API_URL = "/api/carte-bancaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarteBancaireRepository carteBancaireRepository;

    @Autowired
    private CarteBancaireMapper carteBancaireMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarteBancaireMockMvc;

    private CarteBancaire carteBancaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteBancaire createEntity(EntityManager em) {
        CarteBancaire carteBancaire = new CarteBancaire()
            .nomPorteur(DEFAULT_NOM_PORTEUR)
            .numCarte(DEFAULT_NUM_CARTE)
            .dateExpiration(DEFAULT_DATE_EXPIRATION)
            .cvv(DEFAULT_CVV);
        return carteBancaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarteBancaire createUpdatedEntity(EntityManager em) {
        CarteBancaire carteBancaire = new CarteBancaire()
            .nomPorteur(UPDATED_NOM_PORTEUR)
            .numCarte(UPDATED_NUM_CARTE)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .cvv(UPDATED_CVV);
        return carteBancaire;
    }

    @BeforeEach
    public void initTest() {
        carteBancaire = createEntity(em);
    }

    @Test
    @Transactional
    void createCarteBancaire() throws Exception {
        int databaseSizeBeforeCreate = carteBancaireRepository.findAll().size();
        // Create the CarteBancaire
        CarteBancaireDTO carteBancaireDTO = carteBancaireMapper.toDto(carteBancaire);
        restCarteBancaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteBancaireDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeCreate + 1);
        CarteBancaire testCarteBancaire = carteBancaireList.get(carteBancaireList.size() - 1);
        assertThat(testCarteBancaire.getNomPorteur()).isEqualTo(DEFAULT_NOM_PORTEUR);
        assertThat(testCarteBancaire.getNumCarte()).isEqualTo(DEFAULT_NUM_CARTE);
        assertThat(testCarteBancaire.getDateExpiration()).isEqualTo(DEFAULT_DATE_EXPIRATION);
        assertThat(testCarteBancaire.getCvv()).isEqualTo(DEFAULT_CVV);
    }

    @Test
    @Transactional
    void createCarteBancaireWithExistingId() throws Exception {
        // Create the CarteBancaire with an existing ID
        carteBancaire.setId(1L);
        CarteBancaireDTO carteBancaireDTO = carteBancaireMapper.toDto(carteBancaire);

        int databaseSizeBeforeCreate = carteBancaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarteBancaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCarteBancaires() throws Exception {
        // Initialize the database
        carteBancaireRepository.saveAndFlush(carteBancaire);

        // Get all the carteBancaireList
        restCarteBancaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carteBancaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomPorteur").value(hasItem(DEFAULT_NOM_PORTEUR)))
            .andExpect(jsonPath("$.[*].numCarte").value(hasItem(DEFAULT_NUM_CARTE)))
            .andExpect(jsonPath("$.[*].dateExpiration").value(hasItem(DEFAULT_DATE_EXPIRATION.toString())))
            .andExpect(jsonPath("$.[*].cvv").value(hasItem(DEFAULT_CVV)));
    }

    @Test
    @Transactional
    void getCarteBancaire() throws Exception {
        // Initialize the database
        carteBancaireRepository.saveAndFlush(carteBancaire);

        // Get the carteBancaire
        restCarteBancaireMockMvc
            .perform(get(ENTITY_API_URL_ID, carteBancaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carteBancaire.getId().intValue()))
            .andExpect(jsonPath("$.nomPorteur").value(DEFAULT_NOM_PORTEUR))
            .andExpect(jsonPath("$.numCarte").value(DEFAULT_NUM_CARTE))
            .andExpect(jsonPath("$.dateExpiration").value(DEFAULT_DATE_EXPIRATION.toString()))
            .andExpect(jsonPath("$.cvv").value(DEFAULT_CVV));
    }

    @Test
    @Transactional
    void getNonExistingCarteBancaire() throws Exception {
        // Get the carteBancaire
        restCarteBancaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCarteBancaire() throws Exception {
        // Initialize the database
        carteBancaireRepository.saveAndFlush(carteBancaire);

        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();

        // Update the carteBancaire
        CarteBancaire updatedCarteBancaire = carteBancaireRepository.findById(carteBancaire.getId()).get();
        // Disconnect from session so that the updates on updatedCarteBancaire are not directly saved in db
        em.detach(updatedCarteBancaire);
        updatedCarteBancaire
            .nomPorteur(UPDATED_NOM_PORTEUR)
            .numCarte(UPDATED_NUM_CARTE)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .cvv(UPDATED_CVV);
        CarteBancaireDTO carteBancaireDTO = carteBancaireMapper.toDto(updatedCarteBancaire);

        restCarteBancaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carteBancaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteBancaireDTO))
            )
            .andExpect(status().isOk());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
        CarteBancaire testCarteBancaire = carteBancaireList.get(carteBancaireList.size() - 1);
        assertThat(testCarteBancaire.getNomPorteur()).isEqualTo(UPDATED_NOM_PORTEUR);
        assertThat(testCarteBancaire.getNumCarte()).isEqualTo(UPDATED_NUM_CARTE);
        assertThat(testCarteBancaire.getDateExpiration()).isEqualTo(UPDATED_DATE_EXPIRATION);
        assertThat(testCarteBancaire.getCvv()).isEqualTo(UPDATED_CVV);
    }

    @Test
    @Transactional
    void putNonExistingCarteBancaire() throws Exception {
        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();
        carteBancaire.setId(count.incrementAndGet());

        // Create the CarteBancaire
        CarteBancaireDTO carteBancaireDTO = carteBancaireMapper.toDto(carteBancaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteBancaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carteBancaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarteBancaire() throws Exception {
        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();
        carteBancaire.setId(count.incrementAndGet());

        // Create the CarteBancaire
        CarteBancaireDTO carteBancaireDTO = carteBancaireMapper.toDto(carteBancaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteBancaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarteBancaire() throws Exception {
        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();
        carteBancaire.setId(count.incrementAndGet());

        // Create the CarteBancaire
        CarteBancaireDTO carteBancaireDTO = carteBancaireMapper.toDto(carteBancaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteBancaireMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carteBancaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarteBancaireWithPatch() throws Exception {
        // Initialize the database
        carteBancaireRepository.saveAndFlush(carteBancaire);

        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();

        // Update the carteBancaire using partial update
        CarteBancaire partialUpdatedCarteBancaire = new CarteBancaire();
        partialUpdatedCarteBancaire.setId(carteBancaire.getId());

        partialUpdatedCarteBancaire.nomPorteur(UPDATED_NOM_PORTEUR).numCarte(UPDATED_NUM_CARTE);

        restCarteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarteBancaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarteBancaire))
            )
            .andExpect(status().isOk());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
        CarteBancaire testCarteBancaire = carteBancaireList.get(carteBancaireList.size() - 1);
        assertThat(testCarteBancaire.getNomPorteur()).isEqualTo(UPDATED_NOM_PORTEUR);
        assertThat(testCarteBancaire.getNumCarte()).isEqualTo(UPDATED_NUM_CARTE);
        assertThat(testCarteBancaire.getDateExpiration()).isEqualTo(DEFAULT_DATE_EXPIRATION);
        assertThat(testCarteBancaire.getCvv()).isEqualTo(DEFAULT_CVV);
    }

    @Test
    @Transactional
    void fullUpdateCarteBancaireWithPatch() throws Exception {
        // Initialize the database
        carteBancaireRepository.saveAndFlush(carteBancaire);

        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();

        // Update the carteBancaire using partial update
        CarteBancaire partialUpdatedCarteBancaire = new CarteBancaire();
        partialUpdatedCarteBancaire.setId(carteBancaire.getId());

        partialUpdatedCarteBancaire
            .nomPorteur(UPDATED_NOM_PORTEUR)
            .numCarte(UPDATED_NUM_CARTE)
            .dateExpiration(UPDATED_DATE_EXPIRATION)
            .cvv(UPDATED_CVV);

        restCarteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarteBancaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarteBancaire))
            )
            .andExpect(status().isOk());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
        CarteBancaire testCarteBancaire = carteBancaireList.get(carteBancaireList.size() - 1);
        assertThat(testCarteBancaire.getNomPorteur()).isEqualTo(UPDATED_NOM_PORTEUR);
        assertThat(testCarteBancaire.getNumCarte()).isEqualTo(UPDATED_NUM_CARTE);
        assertThat(testCarteBancaire.getDateExpiration()).isEqualTo(UPDATED_DATE_EXPIRATION);
        assertThat(testCarteBancaire.getCvv()).isEqualTo(UPDATED_CVV);
    }

    @Test
    @Transactional
    void patchNonExistingCarteBancaire() throws Exception {
        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();
        carteBancaire.setId(count.incrementAndGet());

        // Create the CarteBancaire
        CarteBancaireDTO carteBancaireDTO = carteBancaireMapper.toDto(carteBancaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carteBancaireDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarteBancaire() throws Exception {
        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();
        carteBancaire.setId(count.incrementAndGet());

        // Create the CarteBancaire
        CarteBancaireDTO carteBancaireDTO = carteBancaireMapper.toDto(carteBancaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteBancaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarteBancaire() throws Exception {
        int databaseSizeBeforeUpdate = carteBancaireRepository.findAll().size();
        carteBancaire.setId(count.incrementAndGet());

        // Create the CarteBancaire
        CarteBancaireDTO carteBancaireDTO = carteBancaireMapper.toDto(carteBancaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarteBancaireMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carteBancaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarteBancaire in the database
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarteBancaire() throws Exception {
        // Initialize the database
        carteBancaireRepository.saveAndFlush(carteBancaire);

        int databaseSizeBeforeDelete = carteBancaireRepository.findAll().size();

        // Delete the carteBancaire
        restCarteBancaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, carteBancaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarteBancaire> carteBancaireList = carteBancaireRepository.findAll();
        assertThat(carteBancaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
