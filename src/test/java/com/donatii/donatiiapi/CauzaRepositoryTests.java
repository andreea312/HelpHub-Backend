package com.donatii.donatiiapi;

import com.donatii.donatiiapi.model.Cauza;
import com.donatii.donatiiapi.repository.ICauzaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.List;

@DataJpaTest
public class CauzaRepositoryTests {
    @Autowired
    private ICauzaRepository cauzaRepository;

    //@Test
    //public void findByIdCauzaTest()
    {
        /*Cauza cauza = Cauza();
        Cauza cauzaTest1 = cauzaRepository.save(cauza);
        Cauza cauzaTest2 = cauzaRepository.findById(cauza.getId()).get();
        Assertions.assertThat(cauzaTest1).isEqualTo(cauzaTest2);*/
    }

    @Test
    public void findAllCauzaTest()
    {
        Cauza cauza = Cauza.builder()
                .titlu("test")
                .build();
        Cauza cauzaTest = cauzaRepository.save(cauza);
        List<Cauza> listaCauze = cauzaRepository.findAll();
        Assertions.assertThat(listaCauze.contains(cauzaTest)).isTrue();
        Assertions.assertThat(listaCauze.size() == 1).isTrue();
    }

    //@Test
    public void saveCauzaTest()
    {
       /* Cauza cauza = Cauza.builder().
                .build();
        Cauza cauzaTest = cauzaRepository.save(cauza);
        Assertions.assertThat(cauzaTest).isNotNull();*/
    }

    //@Test
    public void deleteCauzaTest()
    {
        /*Cauza cauza = Cauza.builder()
                .descriere("test")
                .id(0L)
                .locatie("test")
                .moneda("test")
                .nrSustinatori(0)
                .poze(new HashSet<>())
                .sumaMinima(0)
                .sumaStransa()
                .build();
        cauzaRepository.save(cauza);
        cauzaRepository.delete(cauza);
        Assertions.assertThat(cauzaRepository.count()).isEqualTo(0);*/
    }
    //@Test
    public void updateCauzaTest()
    {
       /* Cauza cauza = Cauza.builder()
                .numeAnimal("test")
                .rasaAnimal("test")
                .varstaAnimal(1)
                .build();
        cauzaRepository.save(cauza);
        cauza.setDescriere("test");
        cauzaRepository.save(cauza);
        Assertions.assertThat(cauzaRepository.findById(cauza.getId()).get().getDescriere()).isEqualTo("test");*/
    }

}
