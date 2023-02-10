package com.ccsw.tutorial.loan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {
  public static final String LOCALHOST = "http://localhost:";
  public static final String SERVICE_PATH = "/loan/";
  public static final int PAGE_SIZE = 5;
  public static final int TOTAL_LOANS = 6;
  public final static String CLIENT_NAME_1 = "Cliente 1"; 
  public final static Long CLIENT_ID_1 = 1L;
  public final static String GAME_TITLE_1 = "Azul"; 

  public static final Long CLIENT_ID_2 =2L;
  public static final String CLIENT_NAME_2 ="Cliente 2";
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  ParameterizedTypeReference<Page<LoanDto>> responseTypePage = new ParameterizedTypeReference<Page<LoanDto>>() {};

  @Test
  public void findFirstPageShouldReturnFirstFiveLoans() {
    LoanSearchDto searchDto = new LoanSearchDto();
    searchDto.setPageable(PageRequest.of(0, PAGE_SIZE));

    ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(
      LOCALHOST + port + SERVICE_PATH,
      HttpMethod.POST,
      new HttpEntity<>(searchDto),
      responseTypePage
    );
    assertNotNull(response);
    assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
    assertEquals(PAGE_SIZE, response.getBody().getSize());
  }

  @Test
  public void findSecondPageWithFiveSizeShouldReturnLastResult() {
    int elementCounts = TOTAL_LOANS - PAGE_SIZE;

    LoanSearchDto searchDto = new LoanSearchDto();
    searchDto.setPageable(PageRequest.of(1, PAGE_SIZE));

    ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(
      LOCALHOST + port + SERVICE_PATH,
      HttpMethod.POST,
      new HttpEntity<>(searchDto),
      responseTypePage
    );
    assertNotNull(response);
    assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
    assertEquals(elementCounts, response.getBody().getContent().size());
  }
  
 
  @Test
  public void saveMoreThan14DaysShouldThrowInvalidDateException() {
      
      LoanDto dto = new LoanDto();
      
      Date startDate =  new GregorianCalendar(2023,0,05).getTime();
      Date endDate = new GregorianCalendar(2023,0,25).getTime();
      
      dto.setStartDate(startDate);
      dto.setEndDate(endDate);
            
      ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
              LOCALHOST + port + SERVICE_PATH,
              HttpMethod.PUT,
              new HttpEntity<>(dto),
              new ParameterizedTypeReference<Map<String, Object>>() {}
            );

      String message = (String) response.getBody().get("message");

      assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
      assertEquals("La fecha no es valida", message);
     
  }
  @Test
  public void saveStartDateLaterThanEndDateShouldThrowInvalidDateException() {
      
      LoanDto dto = new LoanDto();
      
      Date startDate =  new GregorianCalendar(2023,0,15).getTime();
      Date endDate = new GregorianCalendar(2023,0,02).getTime();
      
      dto.setStartDate(startDate);
      dto.setEndDate(endDate);
            
      ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
              LOCALHOST + port + SERVICE_PATH,
              HttpMethod.PUT,
              new HttpEntity<>(dto),
              new ParameterizedTypeReference<Map<String, Object>>() {}
            );

      String message = (String) response.getBody().get("message");

      assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
      assertEquals("La fecha no es valida", message);
     
  }
  @Test
  public void saveClientAlreadyLoanSameDatesShouldThrowExistsLoanException() {
      LoanDto dto = new LoanDto();
      ClientDto client = new ClientDto();
      //GameDto game = new GameDto();
      Date startDate =  new GregorianCalendar(2022,2,10).getTime();
      Date endDate = new GregorianCalendar(2022,2,21).getTime();
      
      client.setId(CLIENT_ID_1);
      client.setName(CLIENT_NAME_1);
      //game.setTitle(GAME_TITLE);
      
      dto.setClient(client);
      dto.setStartDate(startDate);
      dto.setEndDate(endDate);
      
      ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
              LOCALHOST + port + SERVICE_PATH,
              HttpMethod.PUT,
              new HttpEntity<>(dto),
              new ParameterizedTypeReference<Map<String, Object>>() {}
            );

      String message = (String) response.getBody().get("message");

      assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
      assertEquals(CLIENT_NAME_1+" Ya tienes un prestamo activo", message);
      
  }
  
  @Test
  public void saveGameAlreadyLoanByOtherClientSameDatesShouldThrowInvalidDateException() {
      LoanDto dto = new LoanDto();
      ClientDto client = new ClientDto();
      GameDto game = new GameDto();
      Date startDate =  new GregorianCalendar(2022,2,10).getTime();
      Date endDate = new GregorianCalendar(2022,2,24).getTime();
      
      client.setId(CLIENT_ID_2);
      client.setName(CLIENT_NAME_2);
      
      game.setTitle(GAME_TITLE_1);
      
      dto.setClient(client);
      dto.setGame(game);
      dto.setStartDate(startDate);
      dto.setEndDate(endDate);
      
      ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
              LOCALHOST + port + SERVICE_PATH,
              HttpMethod.PUT,
              new HttpEntity<>(dto),
              new ParameterizedTypeReference<Map<String, Object>>() {}
            );

      String message = (String) response.getBody().get("message");

      assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
      assertEquals("El juego: "+ GAME_TITLE_1+" ya esta reservado para estas fechas", message);
  }
  
  public static final Long GAME_ID_1 = 6L;
  @Test
  public void saveShouldCreateNewLoan() {
      
     

      LoanDto dto = new LoanDto();
      ClientDto client = new ClientDto();
      GameDto game = new GameDto();
      Date startDate =  new GregorianCalendar(2020,2,10).getTime();
      Date endDate = new GregorianCalendar(2020,2,22).getTime();
      
      client.setId(CLIENT_ID_2);
      client.setName(CLIENT_NAME_2);
      
      game.setId(GAME_ID_1);
      game.setTitle(GAME_TITLE_1);
      
      dto.setClient(client);
      dto.setGame(game);
      
      dto.setStartDate(startDate);
      dto.setEndDate(endDate);
      
      restTemplate.exchange(
              LOCALHOST + port + SERVICE_PATH,
              HttpMethod.PUT,
              new HttpEntity<>(dto),
              responseTypePage
            );
      
      long newLoanId = TOTAL_LOANS +1;
      long newLoanSize =  TOTAL_LOANS +1;
      
      LoanSearchDto searchDto = new LoanSearchDto();
      searchDto.setPageable(PageRequest.of(0, (int)newLoanSize));
      
      ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(
              LOCALHOST + port + SERVICE_PATH,
              HttpMethod.POST,
              new HttpEntity<>(searchDto),
              responseTypePage
            );
            assertNotNull(response);
            assertEquals(newLoanSize, response.getBody().getTotalElements());
            
            LoanDto loan = response.getBody().getContent().stream().filter(item -> item.getId().equals(newLoanId)).findFirst().orElse(null);
            
            assertNotNull(loan);
            assertEquals(CLIENT_ID_2,loan.getClient().getId());
            assertEquals(GAME_TITLE_1,loan.getGame().getTitle());
            assertEquals(startDate,loan.getStartDate());
            assertEquals(endDate,loan.getEndDate());
  }
  public static final Long LOAN_ID_DELETE = 1L;
  @Test 
  public void deleteWithExistsIdShouldDeleteLoan() {
      long newLoanSize = TOTAL_LOANS -1;
      
      restTemplate.exchange(
              LOCALHOST + port + SERVICE_PATH + LOAN_ID_DELETE,
              HttpMethod.DELETE,
              null,
              Void.class
            );
      
      LoanSearchDto searchDto = new LoanSearchDto();
      searchDto.setPageable(PageRequest.of(0, (int)newLoanSize));
      
      ResponseEntity<Page<LoanDto>> response = restTemplate.exchange(
              LOCALHOST + port + SERVICE_PATH,
              HttpMethod.POST,
              new HttpEntity<>(searchDto),
              responseTypePage
            );
      
      assertNotNull(response);
      assertEquals(newLoanSize,response.getBody().getTotalElements());
      
  }
  
  @Test
  public void deleteWithNotExistsIdShouldThrowException() {

      long deleteLoanId = TOTAL_LOANS + 1;

      ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + deleteLoanId,
              HttpMethod.DELETE, null, Void.class);

      assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }
  
}
