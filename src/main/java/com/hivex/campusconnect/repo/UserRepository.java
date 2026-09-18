package com.hivex.campusconnect.repo;

import com.hivex.campusconnect.dto.follow.FollowUserResponse;
import com.hivex.campusconnect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {


    /*
     * =========================================================
     * FIND USER BY EMAIL
     * =========================================================
     */

    Optional<User> findByEmail(String email);


    /*
     * =========================================================
     * CHECK EMAIL EXISTS
     * =========================================================
     */

    boolean existsByEmail(String email);


    /*
     * =========================================================
     * GET SUGGESTED USERS
     *
     * Returns:
     *
     * userId
     * fullName
     * email
     * major
     * profileImage
     *
     * Data comes from:
     *
     * users
     * +
     * user_profiles
     *
     * The logged-in user is excluded.
     *
     * Users already followed by the logged-in user
     * are also excluded.
     * =========================================================
     */

    @Query("""
        SELECT new com.hivex.campusconnect.dto.follow.FollowUserResponse(
            u.id,
            u.fullName,
            u.email,
            u.major,
            p.profileImage
        )
        FROM User u
        LEFT JOIN UserProfile p
            ON p.user.id = u.id
        WHERE u.id <> :userId
        AND u.id NOT IN (
            SELECT f.following.id
            FROM UserFollow f
            WHERE f.follower.id = :userId
        )
    """)
    List<FollowUserResponse> getSuggestedUsers(
            @Param("userId") Long userId
    );


    /*
     * =========================================================
     * SEARCH USERS
     * =========================================================
     */

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


    /*
     * =========================================================
     * FIND ALL USERS
     * =========================================================
     */

    List<User> findAll();
}