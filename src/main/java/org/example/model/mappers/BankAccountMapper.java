package org.example.model.mappers;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.DaoKeyspace;
import com.datastax.oss.driver.api.mapper.annotations.DaoTable;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.example.model.dao.BankAccountDao;
import org.example.model.dao.JuniorAccountDao;
import org.example.model.dao.SavingAccountDao;
import org.example.model.dao.StandardAccountDao;

@Mapper
public interface BankAccountMapper {
    @DaoFactory
    JuniorAccountDao juniorAccountDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    SavingAccountDao savingAccountDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    StandardAccountDao standardAccountDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    BankAccountDao bankAccountDao(@DaoKeyspace CqlIdentifier keyspace);

    @DaoFactory
    BankAccountDao bankAccountDao(@DaoKeyspace String keyspace, @DaoTable String table);
}
