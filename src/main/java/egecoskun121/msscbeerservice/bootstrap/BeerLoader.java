package egecoskun121.msscbeerservice.bootstrap;

import egecoskun121.msscbeerservice.domain.Beer;
import egecoskun121.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count()==0){
            beerRepository.save(Beer.builder().
                    beerName("Mango Bobs").
                    beerStyle("IPA").
                    quantityToBrew(200).
                    minOnHand(12).
                    upc(BEER_1_UPC).
                    price(new BigDecimal("12.95")).
                    build());

            beerRepository.save(Beer.builder().
                    beerName("Galaxy Cat").
                    beerStyle("PALE_ALE").
                    quantityToBrew(200).
                    minOnHand(12).
                    upc(BEER_2_UPC).
                    price(new BigDecimal("11.95")).
                    build());

            beerRepository.save(Beer.builder().
                    beerName("No Hammers On The Bar").
                    beerStyle("PALE_ALE").
                    quantityToBrew(200).
                    minOnHand(12).
                    upc(BEER_3_UPC).
                    price(new BigDecimal("11.95")).
                    build());

        }


    }
}
