package com.eventRegistration.eventRegistrationSystem.model;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Entity
@Table(name = "visitors")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Visitor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(name = "photo_path", nullable = false)
    private String photoPath;

    @Column(name = "location", nullable = true)
    private String location;


    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


}
