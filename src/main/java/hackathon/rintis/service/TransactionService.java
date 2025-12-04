package hackathon.rintis.service;

import hackathon.rintis.model.DTO.DataBarResponse;
import hackathon.rintis.model.DTO.InsertTransDto;
import hackathon.rintis.model.entity.TransactionList;
import hackathon.rintis.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepo transactionRepo;

    public TransactionService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public Double getCurrentBalance(Integer userId){
        return transactionRepo.getBalance(userId);
    }

    public List<TransactionList> getRecentCashflow(Integer userId){
        return transactionRepo.getRecentTransaction(userId);
    }

    public List<DataBarResponse> getDataBar(Integer userId, Integer year, Integer month){

        return transactionRepo.getDataBar(userId, year, month);
    }

    public void inserTransaksi(List<InsertTransDto> request, Integer userId){

        List<TransactionList> listTrx = new ArrayList<>();

        for (InsertTransDto trans : request){
            TransactionList trx = new TransactionList();
            trx.setAmount(trans.getAmount());
            trx.setDate(java.util.Date.from(
                    LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
            ));
            trx.setName(trans.getDesc());
            trx.setType(trans.getType());
            trx.setId_user(userId);

            listTrx.add(trx);
        }

        transactionRepo.saveAll(listTrx);
    }

    public Double getLabaRugi(Integer year, Integer month, Integer userId){
        return transactionRepo.getLabaRugi(userId, year, month);
    }

    public List<TransactionList> getAll(Integer userId){

        return transactionRepo.findAll().stream()
                .filter(v -> v.getId_user().equals(userId))
                .toList();
    }

}
