package com.ccsw.tutorial.loan;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.config.mapper.BeanMapper;
import com.ccsw.tutorial.exception.ExistsLoanException;
import com.ccsw.tutorial.exception.InvalidDateException;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {
  @Autowired
  LoanRepository loanRepository;

  @Autowired
  GameService gameService;
  
  @Autowired
  ClientService clientService;
  
  @Autowired
  BeanMapper beanMapper;

  @Override
  public Page<Loan> find(Long idGame, Long idClient, Date date, LoanSearchDto dto) { // TODO Auto-generated method stub
    return this.loanRepository.find(idGame, idClient, date, dto.getPageable());
  }

  public void save(LoanDto loanDto) {
        
    if (this.validateDate(loanDto.getStartDate(), loanDto.getEndDate())) {
        
        this.AvailabilityForLoan(loanDto);
        
        Loan loan = new Loan();
        
        BeanUtils.copyProperties(loanDto,loan, "id", "game", "client");    
        
        loan.setGame(this.gameService.get(loanDto.getGame().getId()));
        loan.setClient(this.clientService.getClient(loanDto.getClient().getId()));
        
        this.loanRepository.save(loan);
        
    }else {
        throw new InvalidDateException("La fecha no es valida");
    }
  }

  private boolean validateDate(Date startDate, Date endDate) {
    if (startDate.after(endDate)) {
      return false;
    }

    long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
    long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

    return diffInDays <= 14;
  }
  
  private void AvailabilityForLoan(LoanDto loanDto) {
     
      //Comprobamos que el cliente no tenga ningun prestamo
      List<Loan> loans;
      
      loans = this.loanRepository.findLoansByClientIdAndDate(loanDto.getClient().getId(),loanDto.getStartDate(), loanDto.getEndDate());
      if(!loans.isEmpty())throw new ExistsLoanException(loanDto.getClient().getName() + " Ya tienes un prestamo activo");
      
      loans = this.loanRepository.findLoansByDatesAndTitle(loanDto.getGame().getTitle(),loanDto.getStartDate(), loanDto.getEndDate());
      if(!loans.isEmpty())throw new InvalidDateException("El juego: "+ loanDto.getGame().getTitle()+" ya esta reservado para estas fechas");
      
     
  }
  public void delete(Long id) {
      this.loanRepository.deleteById(id);
  }
}
