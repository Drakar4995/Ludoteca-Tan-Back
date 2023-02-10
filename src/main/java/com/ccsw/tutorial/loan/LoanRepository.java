package com.ccsw.tutorial.loan;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ccsw.tutorial.loan.model.Loan;

public interface LoanRepository extends CrudRepository<Loan, Long> {
  @Query(
    "SELECT l FROM Loan l WHERE (l.game.id = :gameId OR :gameId IS NULL) AND (l.client.id = :clientId OR :clientId IS NULL) AND ((l.startDate <= :date AND l.endDate >= :date) OR :date IS NULL)"
  )
  Page<Loan> find(
    @Param("gameId") Long gameID,
    @Param("clientId") Long clientId,
    @Param("date") Date date,
    Pageable pageable
  );

  //CASE WHEN (COUNT (l) > 0) THEN TRUE ELSE FALSE END
  @Query(
    "SELECT l FROM Loan l WHERE ( l.game.title = :gameTitle AND ((l.startDate <= :startDate AND l.endDate >= :startDate) OR ( l.startDate <= :endDate AND l.endDate >= :endDate) OR (l.startDate >= :startDate AND l.endDate <= :endDate)))"
  )
  List<Loan> findLoansByDatesAndTitle(
    @Param("gameTitle") String gameTitle,
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate
  );

  //CASE WHEN (COUNT (l) > 0 ) THEN TRUE ELSE FALSE END
  @Query(
    "SELECT l FROM Loan l WHERE (l.client.id = :idClient AND ((l.startDate <= :startDate AND l.endDate >= :startDate) OR ( l.startDate <= :endDate AND l.endDate >= :endDate) OR (l.startDate >= :startDate AND l.endDate <= :endDate))) "
  )
  List<Loan> findLoansByClientIdAndDate(
    @Param("idClient") Long id,
    @Param("startDate") Date startDate,
    @Param("endDate") Date endDate
  );
}
