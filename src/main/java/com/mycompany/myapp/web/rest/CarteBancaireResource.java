package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CarteBancaireRepository;
import com.mycompany.myapp.service.CarteBancaireService;
import com.mycompany.myapp.service.dto.CarteBancaireDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CarteBancaire}.
 */
@RestController
@RequestMapping("/api")
public class CarteBancaireResource {

    private final Logger log = LoggerFactory.getLogger(CarteBancaireResource.class);

    private static final String ENTITY_NAME = "carteBancaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarteBancaireService carteBancaireService;

    private final CarteBancaireRepository carteBancaireRepository;

    public CarteBancaireResource(CarteBancaireService carteBancaireService, CarteBancaireRepository carteBancaireRepository) {
        this.carteBancaireService = carteBancaireService;
        this.carteBancaireRepository = carteBancaireRepository;
    }

    /**
     * {@code POST  /carte-bancaires} : Create a new carteBancaire.
     *
     * @param carteBancaireDTO the carteBancaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carteBancaireDTO, or with status {@code 400 (Bad Request)} if the carteBancaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carte-bancaires")
    public ResponseEntity<CarteBancaireDTO> createCarteBancaire(@RequestBody CarteBancaireDTO carteBancaireDTO) throws URISyntaxException {
        log.debug("REST request to save CarteBancaire : {}", carteBancaireDTO);
        if (carteBancaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new carteBancaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CarteBancaireDTO result = carteBancaireService.save(carteBancaireDTO);
        return ResponseEntity
            .created(new URI("/api/carte-bancaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carte-bancaires/:id} : Updates an existing carteBancaire.
     *
     * @param id the id of the carteBancaireDTO to save.
     * @param carteBancaireDTO the carteBancaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carteBancaireDTO,
     * or with status {@code 400 (Bad Request)} if the carteBancaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carteBancaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carte-bancaires/{id}")
    public ResponseEntity<CarteBancaireDTO> updateCarteBancaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CarteBancaireDTO carteBancaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CarteBancaire : {}, {}", id, carteBancaireDTO);
        if (carteBancaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carteBancaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carteBancaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CarteBancaireDTO result = carteBancaireService.update(carteBancaireDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carteBancaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carte-bancaires/:id} : Partial updates given fields of an existing carteBancaire, field will ignore if it is null
     *
     * @param id the id of the carteBancaireDTO to save.
     * @param carteBancaireDTO the carteBancaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carteBancaireDTO,
     * or with status {@code 400 (Bad Request)} if the carteBancaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the carteBancaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the carteBancaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/carte-bancaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CarteBancaireDTO> partialUpdateCarteBancaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CarteBancaireDTO carteBancaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CarteBancaire partially : {}, {}", id, carteBancaireDTO);
        if (carteBancaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carteBancaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carteBancaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CarteBancaireDTO> result = carteBancaireService.partialUpdate(carteBancaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carteBancaireDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /carte-bancaires} : get all the carteBancaires.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carteBancaires in body.
     */
    @GetMapping("/carte-bancaires")
    public List<CarteBancaireDTO> getAllCarteBancaires() {
        log.debug("REST request to get all CarteBancaires");
        return carteBancaireService.findAll();
    }

    /**
     * {@code GET  /carte-bancaires/:id} : get the "id" carteBancaire.
     *
     * @param id the id of the carteBancaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carteBancaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carte-bancaires/{id}")
    public ResponseEntity<CarteBancaireDTO> getCarteBancaire(@PathVariable Long id) {
        log.debug("REST request to get CarteBancaire : {}", id);
        Optional<CarteBancaireDTO> carteBancaireDTO = carteBancaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carteBancaireDTO);
    }

    /**
     * {@code DELETE  /carte-bancaires/:id} : delete the "id" carteBancaire.
     *
     * @param id the id of the carteBancaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carte-bancaires/{id}")
    public ResponseEntity<Void> deleteCarteBancaire(@PathVariable Long id) {
        log.debug("REST request to delete CarteBancaire : {}", id);
        carteBancaireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
