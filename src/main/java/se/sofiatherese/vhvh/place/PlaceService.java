package se.sofiatherese.vhvh.place;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import se.sofiatherese.vhvh.user.UserModel;

import java.util.List;
import java.util.Optional;

@Service
public interface PlaceService {

    ResponseEntity<PlaceModel> createPlace (@Valid PlaceModel placeModel, BindingResult result, Long userId);

    List<PlaceModel> placeModelList (UserModel userModel, List<PlaceModel> allPlaces);

    ResponseEntity<List<PlaceModel>> viewAllPlaces (Long userId);

    ResponseEntity<List<PlaceModel>> viewAllPlacesByName (Long userId);

    ResponseEntity<Optional<PlaceModel>> getOnePlace(Long placeid);

    ResponseEntity<PlaceModel> removePlace(Long placeId);

    ResponseEntity<PlaceModel> updatePlace(@PathVariable Long placeId, @Valid @RequestBody PlaceModel placeModel, BindingResult result);
}
