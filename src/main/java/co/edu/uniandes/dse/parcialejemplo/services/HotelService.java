package co.edu.uniandes.dse.parcialejemplo.services;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotelService {
    @Autowired 
    HotelRepository hotelRepository; 

    @Transactional
    public HotelEntity createHotel(HotelEntity hotelEntity) throws IllegalOperationException{
        log.info("Inicia proceso de creacion de hotel");

        if (!hotelRepository.findByNombre(hotelEntity.getNombre()).isEmpty()){
            throw new IllegalOperationException("Ya existe hotel con ese Nombre ");
        }

        if ((hotelEntity.getNumeroEstrellas()> 5 ) && (hotelEntity.getNumeroEstrellas()< 1 )) 
            throw new IllegalOperationException("hotel no valido Num estrellas no en rango");
        
        log.info("Termina proceso de creacion de hotel"); 
        
        return hotelRepository.save(hotelEntity); 
    }



}
