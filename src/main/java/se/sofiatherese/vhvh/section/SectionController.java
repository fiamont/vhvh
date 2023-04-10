package se.sofiatherese.vhvh.section;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping("/createSection")
    public ResponseEntity<SectionModel> createSection (@Valid @RequestBody SectionModel sectionModel, final PlaceModel placeModel, BindingResult result) {
        return sectionService.makeSection(sectionModel, placeModel, result);
    }

    @GetMapping("/showallsections")
    public ResponseEntity<List<SectionModel>> showAllSections () {
        return sectionService.viewAllSections();
    }

    @GetMapping("/showallsectionsbyname")
    public ResponseEntity<List<SectionModel>> showAllSectionsByName () {
        return sectionService.viewAllSectionsByName();
    }

    @GetMapping("/showonesection/{sectionid}")
    public ResponseEntity<Optional<SectionModel>> showOneSection (@PathVariable Long sectionid) {
        return sectionService.viewOneSection(sectionid);
    }

    @DeleteMapping("/deletesection/{sectionid}")
    public ResponseEntity<SectionModel> deleteSection (Long sectionId) {
        return sectionService.removeSection(sectionId);
    }

    @PutMapping("/updatesection/{sectionid}")
    public ResponseEntity<SectionModel> updateSection (@PathVariable Long sectionid, @Valid @RequestBody SectionModel sectionModel, BindingResult result) {
        return sectionService.changeSection(sectionid, sectionModel, result);
    }

}
