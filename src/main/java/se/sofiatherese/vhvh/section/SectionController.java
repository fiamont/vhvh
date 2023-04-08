package se.sofiatherese.vhvh.section;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/showallsectionsbyname")


    @GetMapping("/showonesection")

    @DeleteMapping("/deletesection/{sectionid}")


    @PutMapping("/updatesection/{sectionid}")


}
