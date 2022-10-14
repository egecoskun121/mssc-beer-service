package egecoskun121.msscbeerservice.web.controller;

import egecoskun121.msscbeerservice.services.BeerService;
import egecoskun121.msscbeerservice.web.model.BeerDTO;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

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
