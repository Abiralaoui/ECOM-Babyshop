package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.LigneCommandeRepository;
import com.mycompany.myapp.service.LigneCommandeService;
import com.mycompany.myapp.service.dto.LigneCommandeDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.LigneCommande}.
 */
@RestController
@RequestMapping("/api")
public class LigneCommandeResource {

    private final Logger log = LoggerFactory.getLogger(LigneCommandeResource.class);

    private static final String ENTITY_NAME = "ligneCommande";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LigneCommandeService ligneCommandeService;

    private final LigneCommandeRepository ligneCommandeRepository;

    public LigneCommandeResource(LigneCommandeService ligneCommandeService, LigneCommandeRepository ligneCommandeRepository) {
        this.ligneCommandeService = ligneCommandeService;
        this.ligneCommandeRepository = ligneCommandeRepository;
    }

    /**
     * {@code POST  /ligne-commandes} : Create a new ligneCommande.
     *
     * @param ligneCommandeDTO the ligneCommandeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ligneCommandeDTO, or with status {@code 400 (Bad Request)} if the ligneCommande has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ligne-commandes")
    public ResponseEntity<LigneCommandeDTO> createLigneCommande(@Valid @RequestBody LigneCommandeDTO ligneCommandeDTO)
        throws URISyntaxException {
        log.debug("REST request to save LigneCommande : {}", ligneCommandeDTO);
        if (ligneCommandeDTO.getId() != null) {
            throw new BadRequestAlertException("A new ligneCommande cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LigneCommandeDTO result = ligneCommandeService.save(ligneCommandeDTO);
        return ResponseEntity
            .created(new URI("/api/ligne-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ligne-commandes/:id} : Updates an existing ligneCommande.
     *
     * @param id the id of the ligneCommandeDTO to save.
     * @param ligneCommandeDTO the ligneCommandeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneCommandeDTO,
     * or with status {@code 400 (Bad Request)} if the ligneCommandeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ligneCommandeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ligne-commandes/{id}")
    public ResponseEntity<LigneCommandeDTO> updateLigneCommande(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LigneCommandeDTO ligneCommandeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LigneCommande : {}, {}", id, ligneCommandeDTO);
        if (ligneCommandeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneCommandeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneCommandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LigneCommandeDTO result = ligneCommandeService.update(ligneCommandeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneCommandeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ligne-commandes/:id} : Partial updates given fields of an existing ligneCommande, field will ignore if it is null
     *
     * @param id the id of the ligneCommandeDTO to save.
     * @param ligneCommandeDTO the ligneCommandeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ligneCommandeDTO,
     * or with status {@code 400 (Bad Request)} if the ligneCommandeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the ligneCommandeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the ligneCommandeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ligne-commandes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LigneCommandeDTO> partialUpdateLigneCommande(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LigneCommandeDTO ligneCommandeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LigneCommande partially : {}, {}", id, ligneCommandeDTO);
        if (ligneCommandeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ligneCommandeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ligneCommandeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LigneCommandeDTO> result = ligneCommandeService.partialUpdate(ligneCommandeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ligneCommandeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ligne-commandes} : get all the ligneCommandes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ligneCommandes in body.
     */
    @GetMapping("/ligne-commandes")
    public List<LigneCommandeDTO> getAllLigneCommandes() {
        log.debug("REST request to get all LigneCommandes");
        return ligneCommandeService.findAll();
    }

    /**
     * {@code GET  /ligne-commandes/:id} : get the "id" ligneCommande.
     *
     * @param id the id of the ligneCommandeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ligneCommandeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ligne-commandes/{id}")
    public ResponseEntity<LigneCommandeDTO> getLigneCommande(@PathVariable Long id) {
        log.debug("REST request to get LigneCommande : {}", id);
        Optional<LigneCommandeDTO> ligneCommandeDTO = ligneCommandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(ligneCommandeDTO);
    }

    /**
     * {@code DELETE  /ligne-commandes/:id} : delete the "id" ligneCommande.
     *
     * @param id the id of the ligneCommandeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ligne-commandes/{id}")
    public ResponseEntity<Void> deleteLigneCommande(@PathVariable Long id) {
        log.debug("REST request to delete LigneCommande : {}", id);
        ligneCommandeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
