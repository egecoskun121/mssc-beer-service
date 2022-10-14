package egecoskun121.msscbeerservice.services;

import egecoskun121.msscbeerservice.web.model.BeerDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface BeerService {
    BeerDTO getById(UUID beerId);

    BeerDTO saveNewBeer(BeerDTO beerDTO);

    BeerDTO updateBeerById(UUID beerId, BeerDTO beerDTO);
}
