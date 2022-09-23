package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HabitacionService {
    @Autowired 
    HabitacionRepository habitacionRepository; 

    @Transactional
    public HabitacionEntity createHabitacion(HabitacionEntity habitacionEntity) throws IllegalOperationException{
        log.info("Inicia proceso de creacion de habitacion");
        if (!(habitacionEntity.getNumIdentificacion()<=habitacionEntity.getNumCamas())) 
            throw new IllegalOperationException("habitacion  no valida Num Identificacion > num Camas");
        
        log.info("Termina proceso de creacion de habitacion"); 
        
        return habitacionRepository.save(habitacionEntity); 
    }


}
