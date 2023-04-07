package se.sofiatherese.vhvh.place;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import se.sofiatherese.vhvh.user.UserModel;

import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public ResponseEntity<PlaceModel> createPlace (@Valid @RequestBody PlaceModel placeModel, final UserModel userModel, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        placeModel.setUserModel(userModel);
        placeRepository.save(placeModel);
        return new ResponseEntity<>(placeModel, HttpStatus.CREATED);
    }

    public ResponseEntity<List<PlaceModel>> viewAllPlaces () {
        try {
            return ResponseEntity.ok(this.placeRepository.findAll());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<PlaceModel>> viewAllPlacesByName () {
        try {
            return ResponseEntity.ok(this.placeRepository.orderByPlaceName());
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Optional<PlaceModel>> getOnePlace(Long placeid) {
        try {
            return ResponseEntity.ok(this.placeRepository.findById(placeid));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
