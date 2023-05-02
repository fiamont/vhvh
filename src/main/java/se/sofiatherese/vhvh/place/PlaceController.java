package se.sofiatherese.vhvh.place;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @PostMapping("/createplace")
    public ResponseEntity<PlaceModel> createPlace (@Valid @RequestBody PlaceModel placeModel, BindingResult result, Principal principal){
        return placeService.createPlace(placeModel, result, principal);
    }

    @GetMapping("/showallplaces")
    public ResponseEntity<List<PlaceModel>> showAllPlaces () {
        return placeService.viewAllPlaces();
    }

    @GetMapping("/showallplacesbyname")
    public ResponseEntity<List<PlaceModel>> showAllPlacesByName () {
        return placeService.viewAllPlacesByName();
    }

    @GetMapping("/showoneplace/{placeid}")
    public ResponseEntity<Optional<PlaceModel>> showOnePlace (Long placeid) {
        return placeService.getOnePlace(placeid);
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
