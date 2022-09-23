package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HabitacionService.class)
public class HabitacionServiceTest {
    
    @Autowired
        private HabitacionService habitacionService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<HabitacionEntity> habitacionList = new ArrayList<>();


    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
            clearData();
            insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
            entityManager.getEntityManager().createQuery("delete from HabitacionEntity");

        
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
 
            for (int i = 0; i < 3; i++) {
                    HabitacionEntity habitacionEntity = factory.manufacturePojo(HabitacionEntity.class);
                    entityManager.persist(habitacionEntity);
                    habitacionList.add(habitacionEntity);
            }

    }
    //Pruebas creacion
    @Test
    void testCreateHabitacion() throws EntityNotFoundException, IllegalOperationException{
        HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class); 
        newEntity.setNumIdentificacion(3);
        newEntity.setNumCamas(6);

        HabitacionEntity result = habitacionService.createHabitacion(newEntity);
        assertNotNull(result);
        assertEquals(newEntity.getId(), result.getId());
        assertEquals(newEntity.getNumBanio(), result.getNumBanio());
        assertEquals(newEntity.getNumCamas(), result.getNumCamas());
        assertEquals(newEntity.getNumPersonas(), result.getNumPersonas());

        
    }
    //No numeros negativos en la cedula
    @Test
    void testCreateHabitacionNoValido() throws EntityNotFoundException, IllegalOperationException{
        assertThrows(IllegalOperationException.class, () -> {
        HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class); 
        newEntity.setNumIdentificacion(8);
        newEntity.setNumCamas(2);

        HabitacionEntity result = habitacionService.createHabitacion(newEntity);
        assertNotNull(result);
        });  
    }
    }