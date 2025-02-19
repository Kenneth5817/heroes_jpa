package org.iesvdm.heroes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Builder;
import org.iesvdm.heroes.domain.Heroe;
import org.iesvdm.heroes.domain.HeroeHasPoder;
import org.iesvdm.heroes.domain.Mision;
import org.iesvdm.heroes.domain.Poder;
import org.iesvdm.heroes.repository.HeroeHasPoderRepository;
import org.iesvdm.heroes.repository.HeroeRepository;
import org.iesvdm.heroes.repository.MisionRepository;
import org.iesvdm.heroes.repository.PoderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class PoderesHeroesTests {

    @Autowired
    HeroeRepository heroeRepository;

    @Autowired
    MisionRepository misionRepository;

    @Autowired
    HeroeHasPoderRepository heroeHasPoderRepository;

    @Autowired
    PoderRepository poderRepository;

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    public void setUp() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }


    @Test
    @Order(1)
    void crearHeroeMisionPoderTest() {

        //Creamos una misión
        Mision mision = Mision.builder()
                .descripcion("Salvar el mundo")
                .villano("Thanos")
                .build();

        //Creamos un heroe
        Heroe heroe = Heroe.builder()
                .nombre("Wolverine")
                .build();
        this.heroeRepository.save(heroe);

        //Creamos un poder
        Poder poder = Poder.builder()
                .nombre("Regeneración")
                .build();

        this.poderRepository.save(poder);

        //Creamos la relación entre heroe y poder
        HeroeHasPoder heroeHasPoder = HeroeHasPoder.builder()
                .heroe(heroe)
                .poder(poder)
                .build();
        this.heroeHasPoderRepository.save(heroeHasPoder);

        //Asignamos el poder al heroe
        heroe.getPoderes().add(heroeHasPoder.getPoder());
        //Asignamos la misión al heroe
        heroe.setMision(mision);
        //Asignamos el heroe a la misión
        mision.getHeroes().add(heroe);
        //Guardar la misión
        mision = this.misionRepository.save(mision);
        //Guardar el heroe
        this.heroeRepository.save(heroe);


    }

    @Test
    @Order(2)
    void listarTodoDesdeMision() {

        transactionTemplate.executeWithoutResult(transactionStatus -> {

            misionRepository.findById(1L).ifPresentOrElse(mision -> {
                System.out.println("Misión: " + mision.toString());
            }, () -> {
                System.out.println("La misión con ID 1 no existe.");
            });

        });

    }

    @Test
    @Order(3)
    void desasociarHeroeDeMision() {

        transactionTemplate.executeWithoutResult(transactionStatus -> {

            // Buscar el heroe
            Heroe heroe = heroeRepository.findById(3L).orElse(null);
            if (heroe != null) {
                // Desasociar el heroe de la misión
                heroe.setMision(null);
                // Guardar el heroe
                heroeRepository.save(heroe);
            }

        });

    }

    @Test
    @Order(4)
    void asociarHeroeAMision() {

        transactionTemplate.executeWithoutResult(transactionStatus -> {

            // Buscar el heroe
            Heroe heroe = heroeRepository.findById(3L).orElse(null);
            if (heroe != null) {
                //  misión
                Mision mision = Mision.builder()
                        .descripcion("Salvar el mundo2")
                        .villano("Thanos2")
                        .build();
                if (mision != null) {
                    //Asociamos el heroe a la misión
                    heroe.setMision(mision);
                    // Asignar el heroe a la misión
                    mision.getHeroes().add(heroe);
                    //Guardamos la mision
                    mision = this.misionRepository.save(mision);

                    // Guardar el heroe
                    heroeRepository.save(heroe);
                }
            }

        });

    }

    @Test
    @Order(5)
    void eliminarHeroe() {

        transactionTemplate.executeWithoutResult(transactionStatus -> {

            // Buscar el heroe
            Heroe heroe = heroeRepository.findById(3L).orElse(null);
            if (heroe != null) {

                // Desasociar la misión del héroe
                if (heroe.getMision() != null) {
                    heroe.setMision(null);
                    heroeRepository.save(heroe);
                }

                // Desasociar los poderes del héroe
                for (HeroeHasPoder heroeHasPoder : heroe.getPoderes()) {
                    HeroeHasPoderRepository.delete(heroeHasPoder);
                }

                // Eliminar el héroe
                heroeRepository.delete(heroe);

            }

        });

    }
}