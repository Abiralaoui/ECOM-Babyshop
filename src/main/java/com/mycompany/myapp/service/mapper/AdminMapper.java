package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Admin;
import com.mycompany.myapp.service.dto.AdminDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Admin} and its DTO {@link AdminDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdminMapper extends EntityMapper<AdminDTO, Admin> {}
