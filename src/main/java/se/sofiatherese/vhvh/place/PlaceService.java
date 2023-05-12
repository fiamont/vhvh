package se.sofiatherese.vhvh.place;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public interface PlaceService {

    ResponseEntity<PlaceModelDTO> createPlace (PlaceModelDTO placeModelDTO, BindingResult result, Long userId);

    ResponseEntity<List<PlaceModelDTO>> viewAllPlaces (Long userId);

    ResponseEntity<List<PlaceModelDTO>> viewAllPlacesByName (Long userId);

    ResponseEntity<PlaceModelDTO> getOnePlace(Long placeId);

    ResponseEntity<PlaceModel> removePlace(Long placeId);

    ResponseEntity<PlaceModel> updatePlace(Long placeId, PlaceModel placeModel, BindingResult result);

}
