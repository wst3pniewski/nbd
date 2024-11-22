package org.example.model.mappers;

import org.example.model.Transaction;
import org.example.model.dto.BankAccountDTO;
import org.example.model.dto.TransactionDTO;

public class TransactionDTOMapper {

    public static TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getSourceAccount(),
                transaction.getDestinationAccount(),
//                BankAccountDTOMapper.toDTO(transaction.getSourceAccount()),
//                BankAccountDTOMapper.toDTO(transaction.getDestinationAccount()),
                transaction.getAmount()
        );
    }

    public static Transaction fromDTO(TransactionDTO transactionDTO) {
        return new Transaction(
                transactionDTO.getId(),
                transactionDTO.getSourceAccount(),
                transactionDTO.getDestinationAccount(),
//                BankAccountDTOMapper.fromDTO(transactionDTO.getSourceAccount()),
//                BankAccountDTOMapper.fromDTO(transactionDTO.getDestinationAccount()),
                transactionDTO.getAmount()
        );
    }

}
