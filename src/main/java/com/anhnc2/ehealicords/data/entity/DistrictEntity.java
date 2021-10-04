package com.anhnc2.ehealicords.data.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "district")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistrictEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String prefix;

    @Column(name = "province_id")
    private Integer provinceId;

    public String getFullName() {
        return String.join(" ", prefix, name);
    }
}
