package com.ccsw.tutorial.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.exception.ExistsException;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    private static final String CLIENT_NAME_NOT_EXISTS = "CLIENT_1";
    private static final String CLIENT_NAME_EXISTS = "Cliente 1";

    @Test
    public void findAllShouldReturnAllClients() {

        List<Client> list = new ArrayList<>();

        list.add(mock(Client.class));

        when(clientRepository.findAll()).thenReturn(list);

        List<Client> clients = clientService.findAll();

        assertNotNull(clients);

        assertEquals(1, clients.size());
    }

    @Test
    public void saveNotExistsClientIdNotExistsNameShouldInsert()throws ExistsException{

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME_NOT_EXISTS);

        when(clientRepository.existsClientByName(clientDto.getName())).thenReturn(false);
        
        ArgumentCaptor<Client> client = ArgumentCaptor.forClass(Client.class);

        clientService.save(null, clientDto);

        verify(clientRepository).save(client.capture());

        assertEquals(CLIENT_NAME_NOT_EXISTS, client.getValue().getName());
    }

    @Test
    public void saveNoExistClientIdExistsNameShouldThrowExistsExcepction() {

        ClientDto clientDto = new ClientDto();
        clientDto.setName(CLIENT_NAME_EXISTS);
        when(clientRepository.existsClientByName(clientDto.getName())).thenReturn(true);

        assertThrows(ExistsException.class, () -> clientService.save(null, clientDto));

    }
}
