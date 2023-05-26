package se.sofiatherese.vhvh.place;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public interface PlaceService {

    ResponseEntity<PlaceModelDTO> createPlace(PlaceModelDTO placeModelDTO, BindingResult result, String username, PlaceModel placeModel);

    ResponseEntity<List<PlaceModelDTO>> viewAllPlaces(String username);

    ResponseEntity<List<PlaceModelDTO>> viewAllPlacesByName(String username);

    ResponseEntity<PlaceModelDTO> getOnePlace(Long placeId);

    ResponseEntity<PlaceModel> removePlace(Long placeId);

    ResponseEntity<PlaceModel> updatePlace(Long placeId, PlaceModel placeModel, BindingResult result);

}
