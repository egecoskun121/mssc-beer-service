package egecoskun121.msscbeerservice.web.controller;

import egecoskun121.msscbeerservice.services.BeerService;
import egecoskun121.msscbeerservice.web.model.BeerDTO;
import egecoskun121.msscbeerservice.web.model.BeerPagedList;
import egecoskun121.msscbeerservice.web.model.BeerStyleEnum;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER=0;
    private static final Integer DEFAULT_PAGE_SIZE=25;

    private final BeerService beerService;

    @GetMapping(produces = {"application/json"})
    public ResponseEntity<BeerPagedList> getBeerById(@RequestParam(value = "pageNumber",required = false) Integer pageNumber,
                                                     @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                                     @RequestParam(value = "beerName",required = false) String beerName,
                                                     @RequestParam(value = "beerStyle",required = false)BeerStyleEnum beerStyle){
        if(pageNumber==null || pageNumber<0){
            pageNumber=DEFAULT_PAGE_NUMBER;
        }

        if(pageSize==null || pageSize<1){
            pageSize=DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerPagedList = beerService.listBeers(beerName,beerStyle, PageRequest.of(pageNumber,pageSize));

        return new ResponseEntity<>(beerPagedList,HttpStatus.OK);
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDTO> getBeerById(@PathVariable("beerId")UUID beerId){
        return new ResponseEntity<>(beerService.getById(beerId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@RequestBody @Validated BeerDTO beerDTO){
        return new ResponseEntity(beerService.saveNewBeer(beerDTO),HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable("beerId")UUID beerId,@Validated @RequestBody BeerDTO beerDTO){

        return new ResponseEntity(beerService.updateBeerById(beerId,beerDTO),HttpStatus.NO_CONTENT);
    }


}
