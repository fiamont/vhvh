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
@RequiredArgsConstructor
public class SectionController {

    private final SectionService sectionService;

    @PostMapping("/createSection/{placeId}")
    public ResponseEntity<SectionModel> createSection (@Valid @RequestBody SectionModel sectionModel, @PathVariable Long placeId, BindingResult result) {
        return sectionService.makeSection(sectionModel, placeId, result);
    }

    @GetMapping("/showallsections/{placeId}")
    public ResponseEntity<List<SectionModel>> showAllSections (@PathVariable Long placeId) {
        return sectionService.viewAllSections(placeId);
    }

    @GetMapping("/showallsectionsbyname/{placeId}")
    public ResponseEntity<List<SectionModel>> showAllSectionsByName (@PathVariable Long placeId) {
        return sectionService.viewAllSectionsByName(placeId);
    }

    @GetMapping("/showonesection/{sectionId}")
    public ResponseEntity<Optional<SectionModel>> showOneSection (@PathVariable Long sectionId) {
        return sectionService.viewOneSection(sectionId);
    }

    @DeleteMapping("/deletesection/{sectionId}")
    public ResponseEntity<SectionModel> deleteSection (@PathVariable Long sectionId) {
        return sectionService.removeSection(sectionId);
    }

    @PutMapping("/updatesection/{sectionId}")
    public ResponseEntity<SectionModel> updateSection (@Valid BindingResult result, @PathVariable Long sectionId) {
        return sectionService.changeSection(result, sectionId);
    }

}
