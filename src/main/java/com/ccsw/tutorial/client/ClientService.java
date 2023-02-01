package com.ccsw.tutorial.client;

import java.util.List;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.exception.ExistsException;

public interface ClientService {

    /**
     * Este metodo devolvera un cliente con un id especifico
     * 
     * @param id
     * @return
     */
    Client getClient(Long id);

    /**
     * Este metodo devolvera todos los clientes
     * 
     * @return
     */
    List<Client> findAll();

    /**
     * Metodo para poder guardar o actualizar un cliente
     * {@link com.ccsw.tutorial.client.model.Client}
     * 
     * @param ClientDto
     * @return List<Client>
     */

    void save(Long id, ClientDto clientDto) throws ExistsException;

    /**
     * 
     * @param id
     * @return boolean para comprobar si se realizo correctamente
     */
    void delete(Long id);
}
