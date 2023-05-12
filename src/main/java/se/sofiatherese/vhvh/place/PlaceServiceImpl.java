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

    public List<PlaceModelDTO> fromModeltoDTO(List<PlaceModel> allPlaces) {
        List<PlaceModelDTO> allPlacesDTO = new ArrayList<>();
        for (PlaceModel placeModel : allPlaces) {
            allPlacesDTO.add(PlaceModelDTO.builder()
                    .placeId(placeModel.getPlaceId())
                    .placeName(placeModel.getPlaceName())
                    .userId(placeModel.getUserModel().getUserId())
                    .build());
        }
        return allPlacesDTO;
    }

    @Override
    public ResponseEntity<List<PlaceModelDTO>> viewAllPlaces (Long userId) {
        try {
            UserModel userModel = userRepository.findById(userId).orElseThrow();
            List<PlaceModel> allPlaces = placeRepository.findByUserModel(userModel);
            List<PlaceModelDTO> allPlacesDTO = fromModeltoDTO(allPlaces);
            return new ResponseEntity<>(allPlacesDTO, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<PlaceModelDTO>> viewAllPlacesByName (Long userId) {
        try {
            UserModel userModel = userRepository.findById(userId).orElseThrow();
            List<PlaceModel> allPlaces = placeRepository.findByUserModelAndOrderByPlaceName(userModel);
            List<PlaceModelDTO> allPlacesDTO = fromModeltoDTO(allPlaces);
            return new ResponseEntity<>(allPlacesDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<PlaceModelDTO> getOnePlace(Long placeId) {
        try {
            PlaceModel placeModel = placeRepository.findByPlaceId(placeId);
            PlaceModelDTO placeModelDTO = PlaceModelDTO.builder()
                    .placeId(placeModel.getPlaceId())
                    .placeName(placeModel.getPlaceName())
                    .userId(placeModel.getUserModel().getUserId())
                    .build();
            return new ResponseEntity<>(placeModelDTO, HttpStatus.OK);
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
