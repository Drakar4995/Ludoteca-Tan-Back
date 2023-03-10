package com.ccsw.tutorial.loan.model;

import java.util.Date;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

public class LoanDto {
    
  private Long id;

  private GameDto game;

  private ClientDto client;

  private Date startDate;
  private Date endDate;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GameDto getGame() {
    return game;
  }

  public void setGame(GameDto gameDto) {
    this.game = gameDto;
  }

  public ClientDto getClient() {
    return client;
  }

  public void setClient(ClientDto clientDto) {
    this.client = clientDto;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
