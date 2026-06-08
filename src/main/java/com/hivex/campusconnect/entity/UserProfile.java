package com.hivex.campusconnect.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profileImage;
    private String coverImage;

    @Column(length = 2000)
    private String bio;

    private String university;
    private String batchYear;

    private String skills;
    private String linkedinUrl;
    private String githubUrl;
    private String website;
    private String phone;
    private String location;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
}

