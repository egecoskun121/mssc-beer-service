package egecoskun121.msscbeerservice.web.mapper;

import egecoskun121.msscbeerservice.domain.Beer;
import egecoskun121.msscbeerservice.web.model.BeerDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    BeerDTO beerToBeerDTO(Beer beer);

    BeerDTO beerToBeerDTOWithInventory(Beer beer);

    Beer beerDTOToBeer(BeerDTO beerDTO);
}
