package com.hivex.campusconnect.repo;

import com.hivex.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
        SELECT u
        FROM User u
        WHERE u.id <> :userId
        AND u.id NOT IN (
            SELECT f.following.id
            FROM UserFollow f
            WHERE f.follower.id = :userId
        )
    """)
    List<User> getSuggestedUsers(
            @Param("userId") Long userId
    );

    @Query("""
        SELECT u
        FROM User u
        WHERE LOWER(u.fullName)
        LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(u.email)
        LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<User> searchUsers(
            @Param("keyword") String keyword
    );
}