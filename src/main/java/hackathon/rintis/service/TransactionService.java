package hackathon.rintis.service;

import hackathon.rintis.model.entity.TransactionList;
import hackathon.rintis.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public Integer getCurrentBalance(){
        return transactionRepo.getBalance(1);
    }

    public Integer getLabaRugi(Integer year, Integer month){
        return transactionRepo.getLabaRugi(1, year, month);
    }

    public List<TransactionList> getAll(Integer userId){

        return transactionRepo.findAll().stream()
                .filter(v -> v.getId_user().equals(userId))
                .toList();
    }

}
