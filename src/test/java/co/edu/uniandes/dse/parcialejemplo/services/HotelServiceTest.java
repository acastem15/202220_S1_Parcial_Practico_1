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

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;



@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HotelService.class)
public class HotelServiceTest {
    
    @Autowired
        private HotelService hotelService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<HotelEntity> hotelList = new ArrayList<>();


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
            entityManager.getEntityManager().createQuery("delete from HotelEntity");

        
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
 
            for (int i = 0; i < 3; i++) {
                    HotelEntity hotelEntity = factory.manufacturePojo(HotelEntity.class);
                    entityManager.persist(hotelEntity);
                    hotelList.add(hotelEntity);
            }

    }
    //Pruebas creacion
    @Test
    void testCreateHotel() throws EntityNotFoundException, IllegalOperationException{
        HotelEntity newEntity = factory.manufacturePojo(HotelEntity.class); 
        newEntity.setNumeroEstrellas(4);
        newEntity.setNombre("hotelAndes");

        HotelEntity result = hotelService.createHotel(newEntity);
        assertNotNull(result);
        assertEquals(newEntity.getId(), result.getId());
        assertEquals(newEntity.getNombre(), result.getNombre());
        assertEquals(newEntity.getHabitaciones(), result.getHabitaciones());
        assertEquals(newEntity.getNumeroEstrellas(), result.getNumeroEstrellas());
        assertEquals(newEntity.getDireccion(), result.getDireccion()); 
        
    }
    //No numeros negativos en la cedula
    @Test
    void testCreateHotelNoValido() throws EntityNotFoundException, IllegalOperationException{
        assertThrows(IllegalOperationException.class, () -> {
        HotelEntity newEntity = factory.manufacturePojo(HotelEntity.class); 
        newEntity.setNumeroEstrellas(-3);
        newEntity.setNombre("Hoteleando");

        HotelEntity result = hotelService.createHotel(newEntity);
        assertNotNull(result);
        });  
    }
    }