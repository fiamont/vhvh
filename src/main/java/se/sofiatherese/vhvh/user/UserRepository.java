package se.sofiatherese.vhvh.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByUsername (String username);

    boolean existsByUsername(String username);

    @Query("SELECT s FROM UserModel s ORDER BY s.firstname asc")
    List<UserModel> orderByFirstname ();

    @Query("SELECT s FROM UserModel s ORDER BY s.lastname asc")
    List<UserModel> orderByLastname ();

    @Query("SELECT s FROM UserModel s ORDER BY s.role asc")
    List<UserModel> orderByRole();
    UserModel deleteByUsername (String username);
}
