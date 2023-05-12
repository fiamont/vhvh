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
    @PostMapping("/createplace/{userId}")
    public ResponseEntity<PlaceModelDTO> createPlace (@Valid @RequestBody PlaceModelDTO placeModelDTO, BindingResult result, @PathVariable Long userId){
        return placeService.createPlace(placeModelDTO, result, userId);
    }

    @CrossOrigin
    @GetMapping("/showallplaces/{userId}")
    public ResponseEntity<List<PlaceModelDTO>> showAllPlaces (@PathVariable Long userId) {
        return placeService.viewAllPlaces(userId);
    }

    @CrossOrigin
    @GetMapping("/showallplacesbyname/{userId}")
    public ResponseEntity<List<PlaceModelDTO>> showAllPlacesByName (@PathVariable Long userId) {
        return placeService.viewAllPlacesByName(userId);
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
