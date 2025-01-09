package org.example.model.mappers;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.example.model.dao.BankAccountDao;

@Mapper
public interface BankAccountMapper {
    @DaoFactory
    BankAccountDao bankAccountDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    BankAccountDao bankAccountDao(@DaoKeyspace String keyspace, @DaoTable String table);
}
