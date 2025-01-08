package org.example.model.mappers;

import com.datastax.oss.driver.api.mapper.annotations.DaoFactory;
import com.datastax.oss.driver.api.mapper.annotations.Mapper;
import org.example.model.dao.TransactionDao;

@Mapper
public interface TransactionMapper {
    @DaoFactory
    TransactionDao transactionDao();
}
