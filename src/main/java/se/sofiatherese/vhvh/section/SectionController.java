package se.sofiatherese.vhvh.section;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
public class SectionController {

    private final SectionService sectionService;

    @CrossOrigin
    @PostMapping("/createSection/{placeId}")
    public ResponseEntity<SectionModel> createSection (@Valid @RequestBody SectionModel sectionModel, @PathVariable Long placeId, BindingResult result) {
        return sectionService.makeSection(sectionModel, placeId, result);
    }

    @CrossOrigin
    @GetMapping("/showallsections/{placeId}")
    public ResponseEntity<List<SectionModel>> showAllSections (@PathVariable Long placeId) {
        return sectionService.viewAllSections(placeId);
    }

    @CrossOrigin
    @GetMapping("/showallsectionsbyname/{placeId}")
    public ResponseEntity<List<SectionModel>> showAllSectionsByName (@PathVariable Long placeId) {
        return sectionService.viewAllSectionsByName(placeId);
    }

    @CrossOrigin
    @GetMapping("/showonesection/{sectionId}")
    public ResponseEntity<Optional<SectionModel>> showOneSection (@PathVariable Long sectionId) {
        return sectionService.viewOneSection(sectionId);
    }

    @CrossOrigin
    @DeleteMapping("/deletesection/{sectionId}")
    public ResponseEntity<SectionModel> deleteSection (@PathVariable Long sectionId) {
        return sectionService.removeSection(sectionId);
    }

    @CrossOrigin
    @PutMapping("/updatesection/{sectionId}")
    public ResponseEntity<SectionModel> updateSection (@Valid BindingResult result, @PathVariable Long sectionId) {
        return sectionService.changeSection(result, sectionId);
    }

}
