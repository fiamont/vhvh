package se.sofiatherese.vhvh.place;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import se.sofiatherese.vhvh.user.UserModel;
import se.sofiatherese.vhvh.user.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    private final PlaceRepository placeRepository;

    private final UserRepository userRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository, UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<PlaceModel> createPlace (@Valid @RequestBody PlaceModel placeModel, BindingResult result, Principal principal) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String username = principal.getName();

        UserModel userModel = userRepository.findByUsername(username).orElseThrow();

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

    public ResponseEntity<PlaceModel> removePlace(Long placeId) {
        try {
            placeRepository.deleteById(placeId);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<PlaceModel> updatePlace(@PathVariable Long placeId, @Valid @RequestBody PlaceModel placeModel, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            Optional<PlaceModel> usedPlace = placeRepository.findById(placeId);
            PlaceModel updatedPlace = usedPlace.get();

            updatedPlace.setPlaceName(placeModel.getPlaceName());
            placeRepository.save(updatedPlace);
            return new ResponseEntity<>(updatedPlace, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
