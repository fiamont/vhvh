package se.sofiatherese.vhvh.place;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import se.sofiatherese.vhvh.user.UserModel;
import se.sofiatherese.vhvh.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService{

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<PlaceModel> createPlace (@Valid PlaceModel placeModel, BindingResult result, Long userId) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        UserModel userModel = userRepository.findById(userId).orElseThrow();

        placeModel.setUserModel(userModel);
        placeRepository.save(placeModel);
        return new ResponseEntity<>(placeModel, HttpStatus.CREATED);
    }

    @Override
    public List<PlaceModel> placeModelList (UserModel userModel, List<PlaceModel> allPlaces) {
        List<PlaceModel> userPlaces = new ArrayList<>();
        for (PlaceModel place : allPlaces) {
            if (place.getUserModel().equals(userModel)) {
                userPlaces.add(place);
            }
        }
        return userPlaces;
    }

    @Override
    public ResponseEntity<List<PlaceModel>> viewAllPlaces (Long userId) {
        try {
            UserModel userModel = userRepository.findById(userId).orElseThrow();
            List<PlaceModel> allPlaces = placeRepository.findAll();
            List<PlaceModel> userPlaces = placeModelList(userModel, allPlaces);
            return new ResponseEntity<>(userPlaces, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<PlaceModel>> viewAllPlacesByName (Long userId) {
        try {
            UserModel userModel = userRepository.findById(userId).orElseThrow();
            List<PlaceModel> allPlaces = placeRepository.orderByPlaceName();
            List<PlaceModel> userPlaces = placeModelList(userModel, allPlaces);
            return new ResponseEntity<>(userPlaces, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Optional<PlaceModel>> getOnePlace(Long placeid) {
        try {
            return ResponseEntity.ok(this.placeRepository.findById(placeid));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PlaceModel> removePlace(Long placeId) {
        try {
            placeRepository.deleteById(placeId);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
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
