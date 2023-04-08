package se.sofiatherese.vhvh.section;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public ResponseEntity<SectionModel> makeSection (@Valid @RequestBody SectionModel sectionModel, final PlaceModel placeModel, BindingResult result){
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            sectionModel.setPlaceModel(placeModel);
            sectionRepository.save(sectionModel);
            return new ResponseEntity<>(sectionModel, HttpStatus.CREATED)
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<SectionModel>> viewAllSections (){

    }

    public ResponseEntity<List<SectionModel>> viewAllSectionsByName () {

    }

    public ResponseEntity<Optional<SectionModel>> viewOneSection () {

    }

    public ResponseEntity<SectionModel> removeSection () {

    }

    public ResponseEntity<SectionModel> changeSection () {

    }

}
