package com.ccsw.tutorial.loan;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ccsw.tutorial.config.mapper.BeanMapper;
import com.ccsw.tutorial.exception.ExistsLoanException;
import com.ccsw.tutorial.exception.InvalidDateException;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
@RequestMapping(value = "/loan")
@RestController
@CrossOrigin(origins = "*")
public class LoanController {
    
  @Autowired
  LoanService loanService;

  @Autowired
  BeanMapper beanMapper;

  //PARA HACER EL FILTRADO
  @RequestMapping(path = "", method = RequestMethod.POST)
  public Page<LoanDto> findPage(
    @RequestParam(value = "idGame", required = false) Long idGame,
    @RequestParam(value = "idClient", required = false) Long idClient,
    @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy")Date date,
    @RequestBody LoanSearchDto dto
  ) {
      
    return this.beanMapper.mapPage(this.loanService.find(idGame, idClient, date, dto),LoanDto.class);
  }
  
  @RequestMapping(path="",method=RequestMethod.PUT)
  public void save(@RequestBody LoanDto loanDto) {
      
      try {

          this.loanService.save(loanDto);
      }catch(InvalidDateException ex) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
      }catch(ExistsLoanException ex) {
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
      }
  }
  
  @RequestMapping(path="/{id}",method=RequestMethod.DELETE)
  public void delete(@PathVariable(value="id")Long idLoan){
      this.loanService.delete(idLoan);
  }
 
}
