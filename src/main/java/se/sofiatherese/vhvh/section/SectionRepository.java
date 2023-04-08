package se.sofiatherese.vhvh.section;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<SectionModel, Long> {
    //SectionModel findBySectionName (String sectionName);
    @Query("SELECT s FROM SectionModel s ORDER BY s.sectionName asc")
    List<SectionModel> orderBySectionName ();
}
