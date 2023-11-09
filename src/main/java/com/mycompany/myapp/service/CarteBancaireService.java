package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CarteBancaire;
import com.mycompany.myapp.repository.CarteBancaireRepository;
import com.mycompany.myapp.service.dto.CarteBancaireDTO;
import com.mycompany.myapp.service.mapper.CarteBancaireMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CarteBancaire}.
 */
@Service
@Transactional
public class CarteBancaireService {

    private final Logger log = LoggerFactory.getLogger(CarteBancaireService.class);

    private final CarteBancaireRepository carteBancaireRepository;

    private final CarteBancaireMapper carteBancaireMapper;

    public CarteBancaireService(CarteBancaireRepository carteBancaireRepository, CarteBancaireMapper carteBancaireMapper) {
        this.carteBancaireRepository = carteBancaireRepository;
        this.carteBancaireMapper = carteBancaireMapper;
    }

    /**
     * Save a carteBancaire.
     *
     * @param carteBancaireDTO the entity to save.
     * @return the persisted entity.
     */
    public CarteBancaireDTO save(CarteBancaireDTO carteBancaireDTO) {
        log.debug("Request to save CarteBancaire : {}", carteBancaireDTO);
        CarteBancaire carteBancaire = carteBancaireMapper.toEntity(carteBancaireDTO);
        carteBancaire = carteBancaireRepository.save(carteBancaire);
        return carteBancaireMapper.toDto(carteBancaire);
    }

    /**
     * Update a carteBancaire.
     *
     * @param carteBancaireDTO the entity to save.
     * @return the persisted entity.
     */
    public CarteBancaireDTO update(CarteBancaireDTO carteBancaireDTO) {
        log.debug("Request to update CarteBancaire : {}", carteBancaireDTO);
        CarteBancaire carteBancaire = carteBancaireMapper.toEntity(carteBancaireDTO);
        carteBancaire = carteBancaireRepository.save(carteBancaire);
        return carteBancaireMapper.toDto(carteBancaire);
    }

    /**
     * Partially update a carteBancaire.
     *
     * @param carteBancaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CarteBancaireDTO> partialUpdate(CarteBancaireDTO carteBancaireDTO) {
        log.debug("Request to partially update CarteBancaire : {}", carteBancaireDTO);

        return carteBancaireRepository
            .findById(carteBancaireDTO.getId())
            .map(existingCarteBancaire -> {
                carteBancaireMapper.partialUpdate(existingCarteBancaire, carteBancaireDTO);

                return existingCarteBancaire;
            })
            .map(carteBancaireRepository::save)
            .map(carteBancaireMapper::toDto);
    }

    /**
     * Get all the carteBancaires.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CarteBancaireDTO> findAll() {
        log.debug("Request to get all CarteBancaires");
        return carteBancaireRepository.findAll().stream().map(carteBancaireMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one carteBancaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CarteBancaireDTO> findOne(Long id) {
        log.debug("Request to get CarteBancaire : {}", id);
        return carteBancaireRepository.findById(id).map(carteBancaireMapper::toDto);
    }

    /**
     * Delete the carteBancaire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CarteBancaire : {}", id);
        carteBancaireRepository.deleteById(id);
    }
}
