package se.sofiatherese.vhvh.place;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import se.sofiatherese.vhvh.user.UserModel;

import java.util.List;
import java.util.Optional;

@Service
public interface PlaceService {

    ResponseEntity<PlaceModelDTO> createPlace (PlaceModelDTO placeModelDTO, BindingResult result, Long userId);

    List<PlaceModel> placeModelList (UserModel userModel, List<PlaceModel> allPlaces);

    ResponseEntity<List<PlaceModel>> viewAllPlaces (Long userId);

    ResponseEntity<List<PlaceModel>> viewAllPlacesByName (Long userId);

    ResponseEntity<Optional<PlaceModel>> getOnePlace(Long placeId);

    ResponseEntity<PlaceModel> removePlace(Long placeId);

    ResponseEntity<PlaceModel> updatePlace(Long placeId, PlaceModel placeModel, BindingResult result);
}
