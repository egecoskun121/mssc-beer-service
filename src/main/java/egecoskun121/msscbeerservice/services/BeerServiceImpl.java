package egecoskun121.msscbeerservice.services;

import egecoskun121.msscbeerservice.domain.Beer;
import egecoskun121.msscbeerservice.repositories.BeerRepository;
import egecoskun121.msscbeerservice.web.controller.NotFoundException;
import egecoskun121.msscbeerservice.web.mapper.BeerMapper;
import egecoskun121.msscbeerservice.web.model.BeerDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class BeerServiceImpl implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDTO getById(UUID beerId) {
        return beerMapper.beerToBeerDTO(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)));
    }

    @Override
    public BeerDTO updateBeerById(UUID beerId, BeerDTO beerDTO) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
        beer.setBeerName(beerDTO.getBeerName());
        beer.setBeerStyle(beerDTO.getBeerStyle().name());
        beer.setPrice(beerDTO.getPrice());
        beer.setUpc(beerDTO.getUpc());

        return beerMapper.beerToBeerDTO(beerRepository.save(beer));
    }
}
