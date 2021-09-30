package com.anhnc2.ehealicords.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationResponse<T>{
    private long total;
    private int page;
    private long totalPage;
    private long pageSize;
    private T items;
}
