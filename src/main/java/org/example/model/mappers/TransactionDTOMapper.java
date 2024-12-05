package org.example.model.mappers;

import org.example.model.Transaction;
import org.example.model.dto.TransactionDTO;

public class TransactionDTOMapper {

    public static TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getSourceAccount(),
                transaction.getDestinationAccount(),
                transaction.getAmount()
        );
    }

    public static Transaction fromDTO(TransactionDTO transactionDTO) {
        return new Transaction(
                transactionDTO.getId(),
                transactionDTO.getSourceAccount(),
                transactionDTO.getDestinationAccount(),
                transactionDTO.getAmount()
        );
    }

}
