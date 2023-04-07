package se.sofiatherese.vhvh.place;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import se.sofiatherese.vhvh.user.UserModel;

import java.util.List;
import java.util.Optional;

@Controller
public class PlaceController {

    private final PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping("/createplace")
    public ResponseEntity<PlaceModel> createPlace (@Valid @RequestBody PlaceModel placeModel, final UserModel userModel, BindingResult result){
        return placeService.createPlace(placeModel, userModel, result);
    }

    @GetMapping("/showallplaces")
    public ResponseEntity<List<PlaceModel>> showAllPlaces () {
        return placeService.viewAllPlaces();
    }

    @GetMapping("/showallplacesbyname")
    public ResponseEntity<List<PlaceModel>> showAllPlacesByName () {
        return placeService.viewAllPlacesByName();
    }

    @GetMapping("/showplace/{placeid}")
    public ResponseEntity<Optional<PlaceModel>> showOnePlace (Long placeid) {
        return placeService.getOnePlace(placeid);
    }

}
