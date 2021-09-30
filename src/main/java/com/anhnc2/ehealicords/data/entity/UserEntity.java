package com.anhnc2.ehealicords.data.entity;

import com.anhnc2.ehealicords.constant.Gender;
import com.anhnc2.ehealicords.constant.RoleType;
import com.anhnc2.ehealicords.constant.UserStatus;
import com.anhnc2.ehealicords.data.auth.AuthUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity implements AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String fullName;
    private String job;
    private String address;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "personal_id")
    private String personalId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "province_id")
    private Integer provinceId;

    @Column(name = "ward_id")
    private Integer wardId;

    @Column(name = "district_id")
    private Integer districtId;

    @Column(name = "updated_time")
    private Long updatedTime;

    @Column(name = "date_of_birth", columnDefinition = "DATE")
    private LocalDate dateOfBirth;

    @Column(name = "avatar_key")
    private String avatarKey;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "balance_update_timestamp")
    private Long balanceUpdateTs;

    private Boolean isValid;

    @JsonIgnore
    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }

    public String getDateOfBirthString() {
        return (dateOfBirth != null) ? dateOfBirth.toString() : null;
    }

    @Override
    @JsonIgnore
    public String[] getListStringOfRoles() {
        return new String[] {RoleType.ROLE_PATIENT.name()};
    }

    @Override
    @JsonIgnore
    public Collection<GrantedAuthority> getAuthority() {
        return Collections.singleton(new SimpleGrantedAuthority(RoleType.ROLE_PATIENT.name()));
    }
}
