package com.ccsw.tutorial.loan.model;

import org.springframework.data.domain.Pageable;

public class LoanSearchDto {
  private Pageable pageable;

  public Pageable getPageable() {
    return pageable;
  }

  public void setPageable(Pageable pageable) {
    this.pageable = pageable;
  }
}
