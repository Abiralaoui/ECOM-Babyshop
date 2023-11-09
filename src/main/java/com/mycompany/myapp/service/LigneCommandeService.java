package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.LigneCommande;
import com.mycompany.myapp.repository.LigneCommandeRepository;
import com.mycompany.myapp.service.dto.LigneCommandeDTO;
import com.mycompany.myapp.service.mapper.LigneCommandeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LigneCommande}.
 */
@Service
@Transactional
public class LigneCommandeService {

    private final Logger log = LoggerFactory.getLogger(LigneCommandeService.class);

    private final LigneCommandeRepository ligneCommandeRepository;

    private final LigneCommandeMapper ligneCommandeMapper;

    public LigneCommandeService(LigneCommandeRepository ligneCommandeRepository, LigneCommandeMapper ligneCommandeMapper) {
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.ligneCommandeMapper = ligneCommandeMapper;
    }

    /**
     * Save a ligneCommande.
     *
     * @param ligneCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public LigneCommandeDTO save(LigneCommandeDTO ligneCommandeDTO) {
        log.debug("Request to save LigneCommande : {}", ligneCommandeDTO);
        LigneCommande ligneCommande = ligneCommandeMapper.toEntity(ligneCommandeDTO);
        ligneCommande = ligneCommandeRepository.save(ligneCommande);
        return ligneCommandeMapper.toDto(ligneCommande);
    }

    /**
     * Update a ligneCommande.
     *
     * @param ligneCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public LigneCommandeDTO update(LigneCommandeDTO ligneCommandeDTO) {
        log.debug("Request to update LigneCommande : {}", ligneCommandeDTO);
        LigneCommande ligneCommande = ligneCommandeMapper.toEntity(ligneCommandeDTO);
        ligneCommande = ligneCommandeRepository.save(ligneCommande);
        return ligneCommandeMapper.toDto(ligneCommande);
    }

    /**
     * Partially update a ligneCommande.
     *
     * @param ligneCommandeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<LigneCommandeDTO> partialUpdate(LigneCommandeDTO ligneCommandeDTO) {
        log.debug("Request to partially update LigneCommande : {}", ligneCommandeDTO);

        return ligneCommandeRepository
            .findById(ligneCommandeDTO.getId())
            .map(existingLigneCommande -> {
                ligneCommandeMapper.partialUpdate(existingLigneCommande, ligneCommandeDTO);

                return existingLigneCommande;
            })
            .map(ligneCommandeRepository::save)
            .map(ligneCommandeMapper::toDto);
    }

    /**
     * Get all the ligneCommandes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LigneCommandeDTO> findAll() {
        log.debug("Request to get all LigneCommandes");
        return ligneCommandeRepository.findAll().stream().map(ligneCommandeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one ligneCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LigneCommandeDTO> findOne(Long id) {
        log.debug("Request to get LigneCommande : {}", id);
        return ligneCommandeRepository.findById(id).map(ligneCommandeMapper::toDto);
    }

    /**
     * Delete the ligneCommande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete LigneCommande : {}", id);
        ligneCommandeRepository.deleteById(id);
    }
}
