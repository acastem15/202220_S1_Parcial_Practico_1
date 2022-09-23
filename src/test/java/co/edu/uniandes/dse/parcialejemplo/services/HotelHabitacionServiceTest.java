package co.edu.uniandes.dse.parcialejemplo.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import({HotelService.class, HotelHabitacionService.class})
public class HotelHabitacionServiceTest {
    
    @Autowired
        private HotelHabitacionService hotelHabitacionService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<HotelEntity> hotelList = new ArrayList<>();
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
            entityManager.getEntityManager().createQuery("delete from HotelEntity");
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

            for (int i = 0; i < 3; i++) {
                    HotelEntity hotelEntity = factory.manufacturePojo(HotelEntity.class); 
                    entityManager.persist(hotelEntity);
                    hotelList.add(hotelEntity);
                    if (i == 0){
                        habitacionList.get(i).setHotel(hotelEntity);
                        hotelEntity.getHabitaciones().add(habitacionList.get(i)); 
                    }
            }

    }
    //Pruebas add habitacion a hotel
    @Test
    void testAddHabitacion() throws EntityNotFoundException, IllegalOperationException{
        HotelEntity hotelEntity = hotelList.get(0); 
        HabitacionEntity habitacionEntity = habitacionList.get(1); 

        HabitacionEntity response = hotelHabitacionService.addHabitacion(hotelEntity.getId(), habitacionEntity.getId()); 

        assertNotNull(response); 

        assertEquals(habitacionEntity.getId(), response.getId()); 

        boolean isIn = hotelEntity.getHabitaciones().contains(habitacionEntity); 

        assertTrue(isIn); 
 
    }
    @Test
    void testAddHabitacionInvalidHabitacion(){
        assertThrows(EntityNotFoundException.class, ()->{
			HotelEntity entity = hotelList.get(0);


			hotelHabitacionService.addHabitacion(entity.getId(), 0L);
		});
 
    }
}