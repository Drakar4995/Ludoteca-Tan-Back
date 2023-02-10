package com.ccsw.tutorial.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.exception.ExistsException;

@Service
public class ClientServiceImpl implements ClientService {
  @Autowired
  ClientRepository clientRepository;

  @Override
  public Client getClient(Long id) {
    return this.clientRepository.findById(id).orElse(null);
  }

  @Override
  public List<Client> findAll() {
    return (List<Client>) this.clientRepository.findAll();
  }

  /**
   * Podemos guardar o actualizar un cliente
   */
  @Override
  public void save(Long id, ClientDto clientDto) {
    Client client;
    boolean exists;

    exists = this.clientRepository.existsClientByName(clientDto.getName());
    if (!exists) {
      if (id == null) {
        client = new Client();
      } else {
        client = this.getClient(id);
      }

      client.setName(clientDto.getName());

      this.clientRepository.save(client);
    } else {
      // Throw new Error
      throw new ExistsException(clientDto.getName() + ", ya existe");
    }
  }

  @Override
  public void delete(Long id) {
    this.clientRepository.deleteById(id);
  }
}
