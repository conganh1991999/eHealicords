package com.anhnc2.ehealicords.data.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "clinical_details")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClinicalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long patientId;

    private Long historyId;

    private String reason;

    private String pathologicalProgress;

    private Double pulse;

    private Double temperature;

    private String bp;

    private Double breathing;

    private Double height;

    private Double weight;

    private String fullBodyExamination;

    private String circulatorySystem;

    private String respiratorySystem;

    private String digestiveSystem;

    private String genitourinarySystem;

    private String nerveSystem;

    private String musculoskeletalSystem;

    private String entSystem;

    private String maxillofacialSystem;

    private String eye;

    private String nutritionalAndEndocrinologyEtc;

    private String briefFileUrl;
}
