package se.sofiatherese.vhvh.place;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping("/createplace/{username}")
    public ResponseEntity<PlaceModel> createPlace (@Valid @RequestBody PlaceModel placeModel, BindingResult result, @PathVariable String username){
        return placeService.createPlace(placeModel, result, username);
    }

    @GetMapping("/showallplaces/{username}")
    public ResponseEntity<List<PlaceModel>> showAllPlaces (@PathVariable String username) {
        return placeService.viewAllPlaces(username);
    }

    @GetMapping("/showallplacesbyname/{username}")
    public ResponseEntity<List<PlaceModel>> showAllPlacesByName (@PathVariable String username) {
        return placeService.viewAllPlacesByName(username);
    }

    @GetMapping("/showoneplace/{placeId}")
    public ResponseEntity<Optional<PlaceModel>> showOnePlace (@PathVariable Long placeId) {
        return placeService.getOnePlace(placeId);
    }

    @DeleteMapping("/deleteplace/{placeId}")
    public ResponseEntity<PlaceModel> deletePlace (@PathVariable Long placeId) {
        return placeService.removePlace(placeId);
    }

    @PutMapping("/updateplace/{placeId}")
    public ResponseEntity<PlaceModel> updatePlace (@PathVariable Long placeId, @Valid @RequestBody PlaceModel placeModel, BindingResult result) {
        return placeService.updatePlace(placeId, placeModel, result);
    }

}
