package co.edu.uniandes.dse.parcialejemplo.services;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;

@Slf4j
@Service
public class HotelHabitacionService {
    @Autowired 
    HabitacionRepository habitacionRepository;

    @Autowired 
    HotelRepository hotelRepository;

    //Añade habitacions a un un hotel con un id dado

    @Transactional
	public HabitacionEntity addHabitacion( Long hotelId,Long habitacionId) throws EntityNotFoundException {
		log.info("Inicia proceso de agregarle un habitacion al hotel con id = {0}", hotelId);
		
		Optional<HabitacionEntity> habitacionEntity = habitacionRepository.findById(habitacionId);
		if(habitacionEntity.isEmpty())
			throw new EntityNotFoundException("habitacion is not valid");
		
		Optional<HotelEntity> hotelEntity = hotelRepository.findById(hotelId);
		if(hotelEntity.isEmpty())
			throw new EntityNotFoundException("hotel is not valid");
		
		hotelEntity.get().getHabitaciones().add(habitacionEntity.get()); 
		
		log.info("Termina proceso de agregarle un habitacion al hotel con id = {0}",  hotelId);
		return habitacionEntity.get();
	}

}
