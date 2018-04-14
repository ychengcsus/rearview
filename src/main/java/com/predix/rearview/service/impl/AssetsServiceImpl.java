package com.predix.rearview.service.impl;

import com.predix.rearview.service.AssetsService;
import com.predix.rearview.domain.Assets;
import com.predix.rearview.repository.AssetsRepository;
import com.predix.rearview.service.dto.AssetsDTO;
import com.predix.rearview.service.mapper.AssetsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Assets.
 */
@Service
@Transactional
public class AssetsServiceImpl implements AssetsService {

    private final Logger log = LoggerFactory.getLogger(AssetsServiceImpl.class);

    private final AssetsRepository assetsRepository;

    private final AssetsMapper assetsMapper;

    public AssetsServiceImpl(AssetsRepository assetsRepository, AssetsMapper assetsMapper) {
        this.assetsRepository = assetsRepository;
        this.assetsMapper = assetsMapper;
    }

    /**
     * Save a assets.
     *
     * @param assetsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AssetsDTO save(AssetsDTO assetsDTO) {
        log.debug("Request to save Assets : {}", assetsDTO);
        Assets assets = assetsMapper.toEntity(assetsDTO);
        assets = assetsRepository.save(assets);
        return assetsMapper.toDto(assets);
    }

    /**
     * Get all the assets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssetsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assets");
        return assetsRepository.findAll(pageable)
            .map(assetsMapper::toDto);
    }

    /**
     * Get one assets by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AssetsDTO findOne(Long id) {
        log.debug("Request to get Assets : {}", id);
        Assets assets = assetsRepository.findOne(id);
        return assetsMapper.toDto(assets);
    }

    /**
     * Delete the assets by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Assets : {}", id);
        assetsRepository.delete(id);
    }
}
