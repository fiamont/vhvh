package se.sofiatherese.vhvh.place;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.sofiatherese.vhvh.user.UserModel;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceModel, Long> {

    boolean existsByPlaceNameAndUserModel_Username(String placeName, String userModel);

    PlaceModel findByPlaceId(Long placeId);

    @Query("SELECT s FROM PlaceModel s ORDER BY s.placeName asc")
    List<PlaceModel> findByUserModelAndOrderByPlaceName (UserModel userModel);

    List<PlaceModel> findByUserModel(UserModel userModel);

}
