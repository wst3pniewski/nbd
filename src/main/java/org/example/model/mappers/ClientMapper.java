package org.example.model.mappers;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.example.model.dao.ClientDao;

@Mapper
public interface ClientMapper {
    @DaoFactory
    ClientDao clientDao();
}
