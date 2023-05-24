package se.sofiatherese.vhvh.place;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class PlaceController {

    private final PlaceService placeService;

    @CrossOrigin
    @PostMapping("/createplace/{username}")
    public ResponseEntity<PlaceModelDTO> createPlace (@Valid @RequestBody PlaceModelDTO placeModelDTO, PlaceModel placeModels, BindingResult result, @PathVariable String username){
        return placeService.createPlace(placeModelDTO, result, username, placeModels);
    }

    @CrossOrigin
    @GetMapping("/showallplaces/{username}")
    public ResponseEntity<List<PlaceModelDTO>> showAllPlaces (@PathVariable String username) {
        return placeService.viewAllPlaces(username);
    }

    @CrossOrigin
    @GetMapping("/showallplacesbyname/{username}")
    public ResponseEntity<List<PlaceModelDTO>> showAllPlacesByName (@PathVariable String username) {
        return placeService.viewAllPlacesByName(username);
    }

    @CrossOrigin
    @GetMapping("/showoneplace/{placeId}")
    public ResponseEntity<PlaceModelDTO> showOnePlace (@PathVariable Long placeId) {
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
