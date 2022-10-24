package egecoskun121.msscbeerservice.services;

import egecoskun121.msscbeerservice.domain.Beer;
import egecoskun121.msscbeerservice.repositories.BeerRepository;
import egecoskun121.msscbeerservice.web.controller.NotFoundException;
import egecoskun121.msscbeerservice.web.mapper.BeerMapper;
import egecoskun121.msscbeerservice.web.model.BeerDTO;
import egecoskun121.msscbeerservice.web.model.BeerPagedList;
import egecoskun121.msscbeerservice.web.model.BeerStyleEnum;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class BeerServiceImpl implements BeerService{

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }

    @Override
    @Cacheable(cacheNames = "beerCache",key = "#beerId",condition = "#showInventoryOnHand==false")
    public BeerDTO getById(UUID beerId,Boolean showInventoryOnHand) {
        if(showInventoryOnHand){
            return beerMapper.beerToBeerDTOWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }else{
            return beerMapper.beerToBeerDTO(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
        }

    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)));
    }

    @Override
    @Cacheable(cacheNames="beerUpcCache")
    public BeerDTO getBeerByUpc(String upc) {
        return beerMapper.beerToBeerDTO(beerRepository.findBeerByUpc(upc));
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

    @Override
    @Cacheable(cacheNames = "beerListCache",condition = "#showInventoryOnHand==false")
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;

        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            beerPage=beerRepository.findAllByBeerNameAndBeerStyle(beerName,beerStyle,pageRequest);
        }else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)){
            beerPage=beerRepository.findAllByBeerName(beerName,pageRequest);
        }else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            beerPage=beerRepository.findAllByBeerStyle(beerStyle,pageRequest);
        }else{
            beerPage=beerRepository.findAll(pageRequest);
        }

        if(showInventoryOnHand){
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDTOWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }else{
            beerPagedList = new BeerPagedList(beerPage
                    .getContent()
                    .stream()
                    .map(beerMapper::beerToBeerDTO)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(beerPage.getPageable().getPageNumber(),
                                    beerPage.getPageable().getPageSize()),
                    beerPage.getTotalElements());
        }


        return beerPagedList;
    }
}
