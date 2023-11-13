package com.donatii.donatiiapi.service.interfaces;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.exceptions.EmptyObjectException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;

import java.util.List;

public interface ICauzaService {
    /**
     * Salveaza cauza
     * @param cauza Cauza
     * @return Cauza
     */
    Cauza save(Cauza cauza);

    /**
     * Gaseste o cauza dupa id
     * @param id Identificator Cauza
     * @return Cauza
     */
    Cauza findById(Long id) throws NotFoundException;

    /**
     * Gaseste toate cauzele unui user
     * @param user User
     * @return Lista de cauze
     */
    List<Cauza> findAllByUser(User user) throws NotFoundException;

    /**
     * Sterge cauza
     * @param id Identificator Cauza
     */
    void delete(Long id) throws NotFoundException;

    /**
     * Gaseste toate cauzele
     * @return Lista de cauze
     */
    List<Cauza> findAll();

    /**
     * Salveaza o poza
     * @param s Url poza
     * @param id Identificator
     */
    void savePicture(String s, Long id) throws NotFoundException, EmptyObjectException;

    /**
     * Modifica cauza
     * @param id Identificator Cauza
     * @param cauza Cauza
     */
    void update(Long id, Cauza cauza) throws NotFoundException;

    /**
     *
     * @param locatie
     * @param sumMin
     * @param sumMax
     * @param rezolvate
     * @param adaposturi
     * @return Lista de cauze
     */
    List<Cauza> filter(String locatie, Integer sumMin, Integer sumMax, Boolean rezolvate, Boolean adaposturi);

    /**
     * Donate
     * @param cauzaId Identificator Cauza
     * @param sum Suma
     */
    void donate(Long cauzaId, Integer sum) throws NotFoundException;
}
