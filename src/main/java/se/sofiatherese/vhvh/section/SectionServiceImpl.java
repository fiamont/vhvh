package se.sofiatherese.vhvh.section;

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
public class SectionServiceImpl implements SectionService{

    private final SectionRepository sectionRepository;
    private final PlaceRepository placeRepository;

    @Override
    public ResponseEntity<SectionModel> createSection(SectionModel sectionModel, Long placeId, BindingResult result){
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Optional<PlaceModel> placeModelOptional = placeRepository.findById(placeId);
            if (placeModelOptional.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            PlaceModel placeModel = placeModelOptional.get();

            if(sectionRepository.existsBySectionNameAndPlaceModel(sectionModel.getSectionName(), placeModel)) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
            sectionModel.setPlaceModel(placeModel);
            sectionRepository.save(sectionModel);
            return new ResponseEntity<>(sectionModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<SectionModel> sectionModelList (PlaceModel placeModel, List<SectionModel> allSections) {
        List<SectionModel> placeSections = new ArrayList<>();
        for (SectionModel section : allSections) {
            if (section.getPlaceModel().equals(placeModel)) {
                placeSections.add(section);
            }
        }
        return placeSections;
    }

    @Override
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

    @Override
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

    @Override
    public ResponseEntity<Optional<SectionModel>> viewOneSection (Long sectionId) {
        try {
            return new ResponseEntity<>(this.sectionRepository.findById(sectionId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<SectionModel> removeSection (Long sectionId) {
        try {
            sectionRepository.deleteById(sectionId);
            return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<SectionModel> changeSection (SectionModel sectionModel, BindingResult result, Long sectionId) {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Optional<SectionModel> usedSection = sectionRepository.findById(sectionId);
            if(usedSection.isPresent()){
                SectionModel updatedSection = usedSection.get();
                updatedSection.setSectionName(sectionModel.getSectionName());

                sectionRepository.save(updatedSection);
                return new ResponseEntity<>(updatedSection, HttpStatus.OK);
            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
