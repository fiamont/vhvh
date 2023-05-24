package se.sofiatherese.vhvh.section;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import se.sofiatherese.vhvh.place.PlaceModel;

import java.util.List;
import java.util.Optional;

@Service
public interface SectionService {

    ResponseEntity<SectionModel> createSection(SectionModel sectionModel, Long placeId, BindingResult result);

    List<SectionModel> sectionModelList (PlaceModel placeModel, List<SectionModel> allSections);

    ResponseEntity<List<SectionModel>> viewAllSections (Long placeId);

    ResponseEntity<List<SectionModel>> viewAllSectionsByName (Long placeId);

    ResponseEntity<Optional<SectionModel>> viewOneSection (Long sectionId);

    ResponseEntity<SectionModel> removeSection (Long sectionId);

    ResponseEntity<SectionModel> changeSection (SectionModel sectionModel, BindingResult result, Long sectionId);

}
