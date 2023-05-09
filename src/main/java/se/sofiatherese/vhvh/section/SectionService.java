package se.sofiatherese.vhvh.section;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import se.sofiatherese.vhvh.place.PlaceModel;
import se.sofiatherese.vhvh.place.PlaceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectionService {

    private final SectionRepository sectionRepository;
    private final PlaceRepository placeRepository;

    public ResponseEntity<SectionModel> makeSection (@Valid SectionModel sectionModel, Long placeId, BindingResult result){
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            PlaceModel placeModel = placeRepository.findByPlaceId(placeId);
            sectionModel.setPlaceModel(placeModel);
            sectionRepository.save(sectionModel);
            return new ResponseEntity<>(sectionModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public List<SectionModel> sectionModelList (PlaceModel placeModel, List<SectionModel> allSections) {
        List<SectionModel> placeSections = new ArrayList<>();
        for (SectionModel section : allSections) {
            if (section.getPlaceModel().equals(placeModel)) {
                placeSections.add(section);
            }
        }
        return placeSections;
    }

    public ResponseEntity<List<SectionModel>> viewAllSections (Long placeId){
        try {
            PlaceModel placeModel = placeRepository.findByPlaceId(placeId);
            List<SectionModel> allSections = sectionRepository.findAll();
            List<SectionModel> placeSections = sectionModelList(placeModel, allSections);
            return new ResponseEntity<>(placeSections, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<SectionModel>> viewAllSectionsByName (Long placeId) {
        try{
            PlaceModel placeModel = placeRepository.findByPlaceId(placeId);
            List<SectionModel> allSections = sectionRepository.orderBySectionName();
            List<SectionModel> placeSections = sectionModelList(placeModel, allSections);
            return new ResponseEntity<>(placeSections, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Optional<SectionModel>> viewOneSection (Long sectionId) {
        try {
            return new ResponseEntity<>(this.sectionRepository.findById(sectionId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<SectionModel> removeSection (Long sectionId) {
        try {
            sectionRepository.deleteById(sectionId);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<SectionModel> changeSection (@Valid BindingResult result, Long sectionId) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Optional<SectionModel> usedSection = sectionRepository.findById(sectionId);
            SectionModel updatedSection = usedSection.get();

            sectionRepository.save(updatedSection);
            return new ResponseEntity<>(updatedSection, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
