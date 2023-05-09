package se.sofiatherese.vhvh.place;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class PlaceController {

    private final PlaceService placeService;

    @CrossOrigin
    @PostMapping("/createplace/{username}")
    public ResponseEntity<PlaceModel> createPlace (@Valid @RequestBody PlaceModel placeModel, BindingResult result, @PathVariable String username){
        return placeService.createPlace(placeModel, result, username);
    }

    @CrossOrigin
    @GetMapping("/showallplaces/{username}")
    public ResponseEntity<List<PlaceModel>> showAllPlaces (@PathVariable String username) {
        return placeService.viewAllPlaces(username);
    }

    @CrossOrigin
    @GetMapping("/showallplacesbyname/{username}")
    public ResponseEntity<List<PlaceModel>> showAllPlacesByName (@PathVariable String username) {
        return placeService.viewAllPlacesByName(username);
    }

    @CrossOrigin
    @GetMapping("/showoneplace/{placeId}")
    public ResponseEntity<Optional<PlaceModel>> showOnePlace (@PathVariable Long placeId) {
        return placeService.getOnePlace(placeId);
    }

    @CrossOrigin
    @DeleteMapping("/deleteplace/{placeId}")
    public ResponseEntity<PlaceModel> deletePlace (@PathVariable Long placeId) {
        return placeService.removePlace(placeId);
    }

    @CrossOrigin
    @PutMapping("/updateplace/{placeId}")
    public ResponseEntity<PlaceModel> updatePlace (@PathVariable Long placeId, @Valid @RequestBody PlaceModel placeModel, BindingResult result) {
        return placeService.updatePlace(placeId, placeModel, result);
    }

}
