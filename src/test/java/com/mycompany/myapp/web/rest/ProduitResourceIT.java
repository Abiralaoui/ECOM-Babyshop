package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Avis;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.Image;
import com.mycompany.myapp.domain.LigneCommande;
import com.mycompany.myapp.domain.Produit;
import com.mycompany.myapp.repository.ProduitRepository;
import com.mycompany.myapp.service.ProduitService;
import com.mycompany.myapp.service.criteria.ProduitCriteria;
import com.mycompany.myapp.service.dto.ProduitDTO;
import com.mycompany.myapp.service.mapper.ProduitMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProduitResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProduitResourceIT {

    private static final Integer DEFAULT_ID_PRODUIT = 1;
    private static final Integer UPDATED_ID_PRODUIT = 2;
    private static final Integer SMALLER_ID_PRODUIT = 1 - 1;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_PRIX_UNITAIRE = 0F;
    private static final Float UPDATED_PRIX_UNITAIRE = 1F;
    private static final Float SMALLER_PRIX_UNITAIRE = 0F - 1F;

    private static final Integer DEFAULT_TAILLE = 0;
    private static final Integer UPDATED_TAILLE = 1;
    private static final Integer SMALLER_TAILLE = 0 - 1;

    private static final String DEFAULT_COULEUR = "AAAAAAAAAA";
    private static final String UPDATED_COULEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/produits";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProduitRepository produitRepository;

    @Mock
    private ProduitRepository produitRepositoryMock;

    @Autowired
    private ProduitMapper produitMapper;

    @Mock
    private ProduitService produitServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProduitMockMvc;

    private Produit produit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createEntity(EntityManager em) {
        Produit produit = new Produit()
            .idProduit(DEFAULT_ID_PRODUIT)
            .libelle(DEFAULT_LIBELLE)
            .description(DEFAULT_DESCRIPTION)
            .prixUnitaire(DEFAULT_PRIX_UNITAIRE)
            .taille(DEFAULT_TAILLE)
            .couleur(DEFAULT_COULEUR);
        return produit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produit createUpdatedEntity(EntityManager em) {
        Produit produit = new Produit()
            .idProduit(UPDATED_ID_PRODUIT)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .taille(UPDATED_TAILLE)
            .couleur(UPDATED_COULEUR);
        return produit;
    }

    @BeforeEach
    public void initTest() {
        produit = createEntity(em);
    }

    @Test
    @Transactional
    void createProduit() throws Exception {
        int databaseSizeBeforeCreate = produitRepository.findAll().size();
        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);
        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isCreated());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate + 1);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getIdProduit()).isEqualTo(DEFAULT_ID_PRODUIT);
        assertThat(testProduit.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testProduit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduit.getPrixUnitaire()).isEqualTo(DEFAULT_PRIX_UNITAIRE);
        assertThat(testProduit.getTaille()).isEqualTo(DEFAULT_TAILLE);
        assertThat(testProduit.getCouleur()).isEqualTo(DEFAULT_COULEUR);
    }

    @Test
    @Transactional
    void createProduitWithExistingId() throws Exception {
        // Create the Produit with an existing ID
        produit.setId(1L);
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        int databaseSizeBeforeCreate = produitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProduits() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProduit").value(hasItem(DEFAULT_ID_PRODUIT)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(DEFAULT_PRIX_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE)))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProduitsWithEagerRelationshipsIsEnabled() throws Exception {
        when(produitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProduitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(produitServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProduitsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(produitServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProduitMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(produitRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get the produit
        restProduitMockMvc
            .perform(get(ENTITY_API_URL_ID, produit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(produit.getId().intValue()))
            .andExpect(jsonPath("$.idProduit").value(DEFAULT_ID_PRODUIT))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.prixUnitaire").value(DEFAULT_PRIX_UNITAIRE.doubleValue()))
            .andExpect(jsonPath("$.taille").value(DEFAULT_TAILLE))
            .andExpect(jsonPath("$.couleur").value(DEFAULT_COULEUR));
    }

    @Test
    @Transactional
    void getProduitsByIdFiltering() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        Long id = produit.getId();

        defaultProduitShouldBeFound("id.equals=" + id);
        defaultProduitShouldNotBeFound("id.notEquals=" + id);

        defaultProduitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProduitShouldNotBeFound("id.greaterThan=" + id);

        defaultProduitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProduitShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProduitsByIdProduitIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idProduit equals to DEFAULT_ID_PRODUIT
        defaultProduitShouldBeFound("idProduit.equals=" + DEFAULT_ID_PRODUIT);

        // Get all the produitList where idProduit equals to UPDATED_ID_PRODUIT
        defaultProduitShouldNotBeFound("idProduit.equals=" + UPDATED_ID_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByIdProduitIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idProduit in DEFAULT_ID_PRODUIT or UPDATED_ID_PRODUIT
        defaultProduitShouldBeFound("idProduit.in=" + DEFAULT_ID_PRODUIT + "," + UPDATED_ID_PRODUIT);

        // Get all the produitList where idProduit equals to UPDATED_ID_PRODUIT
        defaultProduitShouldNotBeFound("idProduit.in=" + UPDATED_ID_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByIdProduitIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idProduit is not null
        defaultProduitShouldBeFound("idProduit.specified=true");

        // Get all the produitList where idProduit is null
        defaultProduitShouldNotBeFound("idProduit.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByIdProduitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idProduit is greater than or equal to DEFAULT_ID_PRODUIT
        defaultProduitShouldBeFound("idProduit.greaterThanOrEqual=" + DEFAULT_ID_PRODUIT);

        // Get all the produitList where idProduit is greater than or equal to UPDATED_ID_PRODUIT
        defaultProduitShouldNotBeFound("idProduit.greaterThanOrEqual=" + UPDATED_ID_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByIdProduitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idProduit is less than or equal to DEFAULT_ID_PRODUIT
        defaultProduitShouldBeFound("idProduit.lessThanOrEqual=" + DEFAULT_ID_PRODUIT);

        // Get all the produitList where idProduit is less than or equal to SMALLER_ID_PRODUIT
        defaultProduitShouldNotBeFound("idProduit.lessThanOrEqual=" + SMALLER_ID_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByIdProduitIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idProduit is less than DEFAULT_ID_PRODUIT
        defaultProduitShouldNotBeFound("idProduit.lessThan=" + DEFAULT_ID_PRODUIT);

        // Get all the produitList where idProduit is less than UPDATED_ID_PRODUIT
        defaultProduitShouldBeFound("idProduit.lessThan=" + UPDATED_ID_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByIdProduitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where idProduit is greater than DEFAULT_ID_PRODUIT
        defaultProduitShouldNotBeFound("idProduit.greaterThan=" + DEFAULT_ID_PRODUIT);

        // Get all the produitList where idProduit is greater than SMALLER_ID_PRODUIT
        defaultProduitShouldBeFound("idProduit.greaterThan=" + SMALLER_ID_PRODUIT);
    }

    @Test
    @Transactional
    void getAllProduitsByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle equals to DEFAULT_LIBELLE
        defaultProduitShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the produitList where libelle equals to UPDATED_LIBELLE
        defaultProduitShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllProduitsByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultProduitShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the produitList where libelle equals to UPDATED_LIBELLE
        defaultProduitShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllProduitsByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle is not null
        defaultProduitShouldBeFound("libelle.specified=true");

        // Get all the produitList where libelle is null
        defaultProduitShouldNotBeFound("libelle.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByLibelleContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle contains DEFAULT_LIBELLE
        defaultProduitShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the produitList where libelle contains UPDATED_LIBELLE
        defaultProduitShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllProduitsByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where libelle does not contain DEFAULT_LIBELLE
        defaultProduitShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the produitList where libelle does not contain UPDATED_LIBELLE
        defaultProduitShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description equals to DEFAULT_DESCRIPTION
        defaultProduitShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the produitList where description equals to UPDATED_DESCRIPTION
        defaultProduitShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProduitShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the produitList where description equals to UPDATED_DESCRIPTION
        defaultProduitShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description is not null
        defaultProduitShouldBeFound("description.specified=true");

        // Get all the produitList where description is null
        defaultProduitShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description contains DEFAULT_DESCRIPTION
        defaultProduitShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the produitList where description contains UPDATED_DESCRIPTION
        defaultProduitShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProduitsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where description does not contain DEFAULT_DESCRIPTION
        defaultProduitShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the produitList where description does not contain UPDATED_DESCRIPTION
        defaultProduitShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixUnitaireIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixUnitaire equals to DEFAULT_PRIX_UNITAIRE
        defaultProduitShouldBeFound("prixUnitaire.equals=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the produitList where prixUnitaire equals to UPDATED_PRIX_UNITAIRE
        defaultProduitShouldNotBeFound("prixUnitaire.equals=" + UPDATED_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixUnitaireIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixUnitaire in DEFAULT_PRIX_UNITAIRE or UPDATED_PRIX_UNITAIRE
        defaultProduitShouldBeFound("prixUnitaire.in=" + DEFAULT_PRIX_UNITAIRE + "," + UPDATED_PRIX_UNITAIRE);

        // Get all the produitList where prixUnitaire equals to UPDATED_PRIX_UNITAIRE
        defaultProduitShouldNotBeFound("prixUnitaire.in=" + UPDATED_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixUnitaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixUnitaire is not null
        defaultProduitShouldBeFound("prixUnitaire.specified=true");

        // Get all the produitList where prixUnitaire is null
        defaultProduitShouldNotBeFound("prixUnitaire.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByPrixUnitaireIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixUnitaire is greater than or equal to DEFAULT_PRIX_UNITAIRE
        defaultProduitShouldBeFound("prixUnitaire.greaterThanOrEqual=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the produitList where prixUnitaire is greater than or equal to UPDATED_PRIX_UNITAIRE
        defaultProduitShouldNotBeFound("prixUnitaire.greaterThanOrEqual=" + UPDATED_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixUnitaireIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixUnitaire is less than or equal to DEFAULT_PRIX_UNITAIRE
        defaultProduitShouldBeFound("prixUnitaire.lessThanOrEqual=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the produitList where prixUnitaire is less than or equal to SMALLER_PRIX_UNITAIRE
        defaultProduitShouldNotBeFound("prixUnitaire.lessThanOrEqual=" + SMALLER_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixUnitaireIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixUnitaire is less than DEFAULT_PRIX_UNITAIRE
        defaultProduitShouldNotBeFound("prixUnitaire.lessThan=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the produitList where prixUnitaire is less than UPDATED_PRIX_UNITAIRE
        defaultProduitShouldBeFound("prixUnitaire.lessThan=" + UPDATED_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    void getAllProduitsByPrixUnitaireIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where prixUnitaire is greater than DEFAULT_PRIX_UNITAIRE
        defaultProduitShouldNotBeFound("prixUnitaire.greaterThan=" + DEFAULT_PRIX_UNITAIRE);

        // Get all the produitList where prixUnitaire is greater than SMALLER_PRIX_UNITAIRE
        defaultProduitShouldBeFound("prixUnitaire.greaterThan=" + SMALLER_PRIX_UNITAIRE);
    }

    @Test
    @Transactional
    void getAllProduitsByTailleIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where taille equals to DEFAULT_TAILLE
        defaultProduitShouldBeFound("taille.equals=" + DEFAULT_TAILLE);

        // Get all the produitList where taille equals to UPDATED_TAILLE
        defaultProduitShouldNotBeFound("taille.equals=" + UPDATED_TAILLE);
    }

    @Test
    @Transactional
    void getAllProduitsByTailleIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where taille in DEFAULT_TAILLE or UPDATED_TAILLE
        defaultProduitShouldBeFound("taille.in=" + DEFAULT_TAILLE + "," + UPDATED_TAILLE);

        // Get all the produitList where taille equals to UPDATED_TAILLE
        defaultProduitShouldNotBeFound("taille.in=" + UPDATED_TAILLE);
    }

    @Test
    @Transactional
    void getAllProduitsByTailleIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where taille is not null
        defaultProduitShouldBeFound("taille.specified=true");

        // Get all the produitList where taille is null
        defaultProduitShouldNotBeFound("taille.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByTailleIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where taille is greater than or equal to DEFAULT_TAILLE
        defaultProduitShouldBeFound("taille.greaterThanOrEqual=" + DEFAULT_TAILLE);

        // Get all the produitList where taille is greater than or equal to UPDATED_TAILLE
        defaultProduitShouldNotBeFound("taille.greaterThanOrEqual=" + UPDATED_TAILLE);
    }

    @Test
    @Transactional
    void getAllProduitsByTailleIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where taille is less than or equal to DEFAULT_TAILLE
        defaultProduitShouldBeFound("taille.lessThanOrEqual=" + DEFAULT_TAILLE);

        // Get all the produitList where taille is less than or equal to SMALLER_TAILLE
        defaultProduitShouldNotBeFound("taille.lessThanOrEqual=" + SMALLER_TAILLE);
    }

    @Test
    @Transactional
    void getAllProduitsByTailleIsLessThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where taille is less than DEFAULT_TAILLE
        defaultProduitShouldNotBeFound("taille.lessThan=" + DEFAULT_TAILLE);

        // Get all the produitList where taille is less than UPDATED_TAILLE
        defaultProduitShouldBeFound("taille.lessThan=" + UPDATED_TAILLE);
    }

    @Test
    @Transactional
    void getAllProduitsByTailleIsGreaterThanSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where taille is greater than DEFAULT_TAILLE
        defaultProduitShouldNotBeFound("taille.greaterThan=" + DEFAULT_TAILLE);

        // Get all the produitList where taille is greater than SMALLER_TAILLE
        defaultProduitShouldBeFound("taille.greaterThan=" + SMALLER_TAILLE);
    }

    @Test
    @Transactional
    void getAllProduitsByCouleurIsEqualToSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where couleur equals to DEFAULT_COULEUR
        defaultProduitShouldBeFound("couleur.equals=" + DEFAULT_COULEUR);

        // Get all the produitList where couleur equals to UPDATED_COULEUR
        defaultProduitShouldNotBeFound("couleur.equals=" + UPDATED_COULEUR);
    }

    @Test
    @Transactional
    void getAllProduitsByCouleurIsInShouldWork() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where couleur in DEFAULT_COULEUR or UPDATED_COULEUR
        defaultProduitShouldBeFound("couleur.in=" + DEFAULT_COULEUR + "," + UPDATED_COULEUR);

        // Get all the produitList where couleur equals to UPDATED_COULEUR
        defaultProduitShouldNotBeFound("couleur.in=" + UPDATED_COULEUR);
    }

    @Test
    @Transactional
    void getAllProduitsByCouleurIsNullOrNotNull() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where couleur is not null
        defaultProduitShouldBeFound("couleur.specified=true");

        // Get all the produitList where couleur is null
        defaultProduitShouldNotBeFound("couleur.specified=false");
    }

    @Test
    @Transactional
    void getAllProduitsByCouleurContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where couleur contains DEFAULT_COULEUR
        defaultProduitShouldBeFound("couleur.contains=" + DEFAULT_COULEUR);

        // Get all the produitList where couleur contains UPDATED_COULEUR
        defaultProduitShouldNotBeFound("couleur.contains=" + UPDATED_COULEUR);
    }

    @Test
    @Transactional
    void getAllProduitsByCouleurNotContainsSomething() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        // Get all the produitList where couleur does not contain DEFAULT_COULEUR
        defaultProduitShouldNotBeFound("couleur.doesNotContain=" + DEFAULT_COULEUR);

        // Get all the produitList where couleur does not contain UPDATED_COULEUR
        defaultProduitShouldBeFound("couleur.doesNotContain=" + UPDATED_COULEUR);
    }

    @Test
    @Transactional
    void getAllProduitsByImageIsEqualToSomething() throws Exception {
        Image image;
        if (TestUtil.findAll(em, Image.class).isEmpty()) {
            produitRepository.saveAndFlush(produit);
            image = ImageResourceIT.createEntity(em);
        } else {
            image = TestUtil.findAll(em, Image.class).get(0);
        }
        em.persist(image);
        em.flush();
        produit.addImage(image);
        produitRepository.saveAndFlush(produit);
        Long imageId = image.getId();

        // Get all the produitList where image equals to imageId
        defaultProduitShouldBeFound("imageId.equals=" + imageId);

        // Get all the produitList where image equals to (imageId + 1)
        defaultProduitShouldNotBeFound("imageId.equals=" + (imageId + 1));
    }

    @Test
    @Transactional
    void getAllProduitsByLigneCommandeIsEqualToSomething() throws Exception {
        LigneCommande ligneCommande;
        if (TestUtil.findAll(em, LigneCommande.class).isEmpty()) {
            produitRepository.saveAndFlush(produit);
            ligneCommande = LigneCommandeResourceIT.createEntity(em);
        } else {
            ligneCommande = TestUtil.findAll(em, LigneCommande.class).get(0);
        }
        em.persist(ligneCommande);
        em.flush();
        produit.addLigneCommande(ligneCommande);
        produitRepository.saveAndFlush(produit);
        Long ligneCommandeId = ligneCommande.getId();

        // Get all the produitList where ligneCommande equals to ligneCommandeId
        defaultProduitShouldBeFound("ligneCommandeId.equals=" + ligneCommandeId);

        // Get all the produitList where ligneCommande equals to (ligneCommandeId + 1)
        defaultProduitShouldNotBeFound("ligneCommandeId.equals=" + (ligneCommandeId + 1));
    }

    @Test
    @Transactional
    void getAllProduitsByAvisIsEqualToSomething() throws Exception {
        Avis avis;
        if (TestUtil.findAll(em, Avis.class).isEmpty()) {
            produitRepository.saveAndFlush(produit);
            avis = AvisResourceIT.createEntity(em);
        } else {
            avis = TestUtil.findAll(em, Avis.class).get(0);
        }
        em.persist(avis);
        em.flush();
        produit.addAvis(avis);
        produitRepository.saveAndFlush(produit);
        Long avisId = avis.getId();

        // Get all the produitList where avis equals to avisId
        defaultProduitShouldBeFound("avisId.equals=" + avisId);

        // Get all the produitList where avis equals to (avisId + 1)
        defaultProduitShouldNotBeFound("avisId.equals=" + (avisId + 1));
    }

    @Test
    @Transactional
    void getAllProduitsByCategoryIsEqualToSomething() throws Exception {
        Category category;
        if (TestUtil.findAll(em, Category.class).isEmpty()) {
            produitRepository.saveAndFlush(produit);
            category = CategoryResourceIT.createEntity(em);
        } else {
            category = TestUtil.findAll(em, Category.class).get(0);
        }
        em.persist(category);
        em.flush();
        produit.addCategory(category);
        produitRepository.saveAndFlush(produit);
        Long categoryId = category.getId();

        // Get all the produitList where category equals to categoryId
        defaultProduitShouldBeFound("categoryId.equals=" + categoryId);

        // Get all the produitList where category equals to (categoryId + 1)
        defaultProduitShouldNotBeFound("categoryId.equals=" + (categoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProduitShouldBeFound(String filter) throws Exception {
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produit.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProduit").value(hasItem(DEFAULT_ID_PRODUIT)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].prixUnitaire").value(hasItem(DEFAULT_PRIX_UNITAIRE.doubleValue())))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE)))
            .andExpect(jsonPath("$.[*].couleur").value(hasItem(DEFAULT_COULEUR)));

        // Check, that the count call also returns 1
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProduitShouldNotBeFound(String filter) throws Exception {
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProduitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProduit() throws Exception {
        // Get the produit
        restProduitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit
        Produit updatedProduit = produitRepository.findById(produit.getId()).get();
        // Disconnect from session so that the updates on updatedProduit are not directly saved in db
        em.detach(updatedProduit);
        updatedProduit
            .idProduit(UPDATED_ID_PRODUIT)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .taille(UPDATED_TAILLE)
            .couleur(UPDATED_COULEUR);
        ProduitDTO produitDTO = produitMapper.toDto(updatedProduit);

        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getIdProduit()).isEqualTo(UPDATED_ID_PRODUIT);
        assertThat(testProduit.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testProduit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduit.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testProduit.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testProduit.getCouleur()).isEqualTo(UPDATED_COULEUR);
    }

    @Test
    @Transactional
    void putNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, produitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(produitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProduitWithPatch() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit using partial update
        Produit partialUpdatedProduit = new Produit();
        partialUpdatedProduit.setId(produit.getId());

        partialUpdatedProduit.description(UPDATED_DESCRIPTION).prixUnitaire(UPDATED_PRIX_UNITAIRE).taille(UPDATED_TAILLE);

        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduit))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getIdProduit()).isEqualTo(DEFAULT_ID_PRODUIT);
        assertThat(testProduit.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testProduit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduit.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testProduit.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testProduit.getCouleur()).isEqualTo(DEFAULT_COULEUR);
    }

    @Test
    @Transactional
    void fullUpdateProduitWithPatch() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeUpdate = produitRepository.findAll().size();

        // Update the produit using partial update
        Produit partialUpdatedProduit = new Produit();
        partialUpdatedProduit.setId(produit.getId());

        partialUpdatedProduit
            .idProduit(UPDATED_ID_PRODUIT)
            .libelle(UPDATED_LIBELLE)
            .description(UPDATED_DESCRIPTION)
            .prixUnitaire(UPDATED_PRIX_UNITAIRE)
            .taille(UPDATED_TAILLE)
            .couleur(UPDATED_COULEUR);

        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduit))
            )
            .andExpect(status().isOk());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
        Produit testProduit = produitList.get(produitList.size() - 1);
        assertThat(testProduit.getIdProduit()).isEqualTo(UPDATED_ID_PRODUIT);
        assertThat(testProduit.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testProduit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduit.getPrixUnitaire()).isEqualTo(UPDATED_PRIX_UNITAIRE);
        assertThat(testProduit.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testProduit.getCouleur()).isEqualTo(UPDATED_COULEUR);
    }

    @Test
    @Transactional
    void patchNonExistingProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, produitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduit() throws Exception {
        int databaseSizeBeforeUpdate = produitRepository.findAll().size();
        produit.setId(count.incrementAndGet());

        // Create the Produit
        ProduitDTO produitDTO = produitMapper.toDto(produit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProduitMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(produitDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Produit in the database
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduit() throws Exception {
        // Initialize the database
        produitRepository.saveAndFlush(produit);

        int databaseSizeBeforeDelete = produitRepository.findAll().size();

        // Delete the produit
        restProduitMockMvc
            .perform(delete(ENTITY_API_URL_ID, produit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produit> produitList = produitRepository.findAll();
        assertThat(produitList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
