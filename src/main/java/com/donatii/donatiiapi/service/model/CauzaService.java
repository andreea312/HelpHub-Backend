package com.donatii.donatiiapi.service.model;

import com.donatii.donatiiapi.model.*;
import com.donatii.donatiiapi.repository.ICauzaRepository;
import com.donatii.donatiiapi.repository.IPozaRepository;
import com.donatii.donatiiapi.service.exceptions.EmptyObjectException;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import com.donatii.donatiiapi.service.interfaces.ICauzaService;
import com.donatii.donatiiapi.utils.Ensure;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CauzaService implements ICauzaService {
    private final ICauzaRepository cauzaRepository;
    private final IPozaRepository pozaRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CauzaService(ICauzaRepository cauzaRepository, IPozaRepository pozaRepository) {
        this.cauzaRepository = cauzaRepository;
        this.pozaRepository = pozaRepository;
    }

    public Cauza save(Cauza cauza) {
        Ensure.NotNull(cauza);

        return cauzaRepository.save(cauza);
    }

    public Cauza findById(Long id) throws NotFoundException {
        Ensure.NotNull(id);

        Optional<Cauza> cauza = cauzaRepository.findById(id);
        if (cauza.isEmpty()) {
            throw new NotFoundException("Case not found!");
        }
        return cauza.get();
    }

    @Override
    public List<Cauza> findAllByUser(User user)  throws NotFoundException{
        Ensure.NotNull(user);
        List<Cauza> causesOFUser = new ArrayList<>();
        List<Cauza> allCauses = cauzaRepository.findAll();
        for (Cauza cauza: allCauses){
            if (user.getCauze().contains(cauza)){
                causesOFUser.add(cauza);
            }
        }
        //causesOFUser.addAll(user.getCauze());
        return causesOFUser;
    }

    public void delete(Long id) throws NotFoundException {
        Ensure.NotNull(id);

        Optional<Cauza> cauza = cauzaRepository.findById(id);
        if(cauza.isEmpty())
            throw new NotFoundException("Case not found");
        cauzaRepository.deleteById(id);
    }

    public List<Cauza> findAll() {
        return cauzaRepository.findAll();
    }

    public List<Cauza> findAllSorted() {
        List<Cauza> sortedList = new ArrayList<>();

        // Filter the elements having sumaMinima != sumaStransa
        List<Cauza> differentSumList = cauzaRepository.findAll().stream()
                .filter(cauza -> cauza.getSumaMinima() != null && cauza.getSumaStransa() != null && !cauza.getSumaMinima().equals(cauza.getSumaStransa()))
                .collect(Collectors.toList());

        // Filter the elements having sumaMinima == sumaStransa
        List<Cauza> sameSumList = cauzaRepository.findAll().stream()
                .filter(cauza -> cauza.getSumaMinima() != null && cauza.getSumaStransa() != null && cauza.getSumaMinima().equals(cauza.getSumaStransa()))
                .collect(Collectors.toList());

        // Add elements having sumaMinima != sumaStransa to the sorted list
        sortedList.addAll(differentSumList);

        // Sort elements having sumaMinima == sumaStransa based on other criteria (if needed)
        // For example, sorting by ID or any other property
        sameSumList.sort(Comparator.comparingLong(Cauza::getId));

        // Add elements having sumaMinima == sumaStransa to the sorted list
        sortedList.addAll(sameSumList);

        return sortedList;
    }

    public void savePicture(String s, Long id) throws NotFoundException, EmptyObjectException {
        Ensure.NotNullOrEmpty(s);
        Ensure.NotNull(id);

        Optional<Cauza> cauza = cauzaRepository.findById(id);
        if (cauza.isEmpty()) {
            throw new NotFoundException("Case not found!");
        }
        Poza poza = new Poza();
        poza.setUrl(s);
        cauza.get().getPoze().add(poza);
        cauzaRepository.save(cauza.get());
    }

    public void update(Long id, Cauza cauza) throws NotFoundException {
        Ensure.NotNull(id);
        Ensure.NotNull(cauza);

        if (!cauzaRepository.existsById(id)) {
            throw new NotFoundException("Case not found!");
        }
        cauza.setId(id);
        cauzaRepository.save(cauza);
    }

    public List<Cauza> filter(String locatie, Integer sumMin, Integer sumMax, Boolean rezolvate) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<? extends Cauza> query = criteriaBuilder.createQuery(Cauza.class);
        final Root<? extends Cauza> cauza = query.from(Cauza.class);
        final List<Predicate> predicates = new ArrayList<>();
        if (locatie != null && !locatie.isEmpty()) {
            predicates.add(criteriaBuilder.like(cauza.get("locatie"), "%" + locatie + "%"));
        }
        if (sumMin != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(cauza.get("sumaMinima"), sumMin));
        }
        if (sumMax != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(cauza.get("sumaMinima"), sumMax));
        }
        if (rezolvate != null && rezolvate) {
            Expression<Integer> sumaStransa = cauza.get("sumaStransa");
            Expression<Integer> sumaMinima = cauza.get("sumaMinima");
            predicates.add(criteriaBuilder.greaterThan(sumaMinima, sumaStransa));
        }
        query.where(predicates.toArray(new Predicate[0]));
        TypedQuery<? extends Cauza> typedQuery = entityManager.createQuery(query);
        List<? extends Cauza> resultList = typedQuery.getResultList();

        return (List<Cauza>) resultList;
    }

    @Override
    public void donate(Long cauzaId, Integer sum) throws NotFoundException {
        Ensure.NotNull(cauzaId);
        Ensure.NotNull(sum);

        Optional<Cauza> cauzaOptional = cauzaRepository.findById(cauzaId);
        if(cauzaOptional.isEmpty())
            throw new NotFoundException("Cauza not found");
        Cauza cauza = cauzaOptional.get();
        cauza.setSumaStransa(cauza.getSumaStransa() + sum);
        cauzaRepository.save(cauza);
    }
}