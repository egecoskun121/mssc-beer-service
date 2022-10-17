package egecoskun121.msscbeerservice.web.mapper;

import egecoskun121.msscbeerservice.domain.Beer;
import egecoskun121.msscbeerservice.services.inventory.BeerInventoryService;
import egecoskun121.msscbeerservice.web.model.BeerDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by jt on 2019-06-08.
 */
public abstract class BeerMapperDecorator implements BeerMapper {
    private BeerInventoryService beerInventoryService;
    private BeerMapper mapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BeerDTO beerToBeerDTO(Beer beer) {
        BeerDTO dto = mapper.beerToBeerDTO(beer);
        return dto;
    }

    public BeerDTO beerToBeerDTOWithInventory(Beer beer) {
        BeerDTO dto = mapper.beerToBeerDTO(beer);
        dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));

        return dto;
    }


    @Override
    public Beer beerDTOToBeer(BeerDTO beerDto) {
        return mapper.beerDTOToBeer(beerDto);
    }
}