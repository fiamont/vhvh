package se.sofiatherese.vhvh.section;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import se.sofiatherese.vhvh.place.PlaceModel;

import java.util.List;
import java.util.Optional;

@Service
public interface SectionService {

    public ResponseEntity<SectionModel> makeSection (@Valid SectionModel sectionModel, Long placeId, BindingResult result);

    public List<SectionModel> sectionModelList (PlaceModel placeModel, List<SectionModel> allSections);

    public ResponseEntity<List<SectionModel>> viewAllSections (Long placeId);

    public ResponseEntity<List<SectionModel>> viewAllSectionsByName (Long placeId);

    public ResponseEntity<Optional<SectionModel>> viewOneSection (Long sectionId);

    public ResponseEntity<SectionModel> removeSection (Long sectionId);

    public ResponseEntity<SectionModel> changeSection (@Valid BindingResult result, Long sectionId);

}
