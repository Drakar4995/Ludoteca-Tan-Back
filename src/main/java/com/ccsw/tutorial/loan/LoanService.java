package com.ccsw.tutorial.loan;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

public interface LoanService {
    
     Page<Loan> find(Long idGame, Long idClient, Date date,LoanSearchDto dto);
     
     void save(LoanDto loanDto);
 
     void delete(Long idLoan);
}

