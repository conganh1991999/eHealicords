package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.RoomEntity;
import lombok.Builder;
import lombok.Data;

// TODO: clinic

@Data
@Builder
public class RoomDetailResponse {
    private Integer id;
    private String name;
    private Integer medicalSpecialtyId;
    private String medicalSpecialtyName;
    private Integer branchId;
    private Integer capacity;

    public static RoomDetailResponse fromDAO(RoomEntity roomEntity, String medicalSpecialtyName) {
        return RoomDetailResponse.builder()
                .id(roomEntity.getId())
                .name(roomEntity.getName())
                // .medicalSpecialtyId(roomEntity.getMedicalSpecialtyId())
                .medicalSpecialtyName(medicalSpecialtyName)
                .branchId(roomEntity.getBranchId())
                // .capacity(roomEntity.getCapacity())
                .build();
    }
}
