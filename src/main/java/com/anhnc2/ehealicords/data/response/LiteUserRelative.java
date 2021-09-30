package com.anhnc2.ehealicords.data.response;

import com.anhnc2.ehealicords.data.entity.UserRelativeEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LiteUserRelative {
    private Long id;
    private String fullName;
    private String relativeName;

    public static LiteUserRelative fromDAO(UserRelativeEntity relative){
        return LiteUserRelative.builder()
                .id(relative.getId())
                .fullName(relative.getFullName())
                .relativeName(relative.getRelation())
                .build();
    }
}
