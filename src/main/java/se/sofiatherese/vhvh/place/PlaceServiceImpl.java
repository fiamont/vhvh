package se.sofiatherese.vhvh.place;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<PlaceModelDTO> createPlace (PlaceModelDTO placeModelDTO, BindingResult result, Long userId) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PlaceModel placeModel = PlaceModel.builder().placeName(placeModelDTO.getPlaceName()).build();
        UserModel userModel = userRepository.findById(userId).orElseThrow();
        placeModel.setUserModel(userModel);
        placeRepository.save(placeModel);
        PlaceModelDTO createdPlaceModelDTO = PlaceModelDTO.builder()
                        .placeId(placeModel.getPlaceId())
                        .placeName(placeModel.getPlaceName())
                        .userId(placeModel.getUserModel().getUserId())
                        .build();
        return new ResponseEntity<>(createdPlaceModelDTO, HttpStatus.CREATED);
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
    public ResponseEntity<Optional<PlaceModel>> getOnePlace(Long placeId) {
        try {
            return ResponseEntity.ok(this.placeRepository.findById(placeId));
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
    public ResponseEntity<PlaceModel> updatePlace(Long placeId, PlaceModel placeModel, BindingResult result) {
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
