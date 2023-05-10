package se.sofiatherese.vhvh.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceModel, Long> {

    PlaceModel findByPlaceName (String placeName);

    PlaceModel findByPlaceId (Long placeId);
    @Query("SELECT s FROM PlaceModel s ORDER BY s.placeName asc")
    List<PlaceModel> orderByPlaceName ();
}
