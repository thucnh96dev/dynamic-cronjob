package com.thucnh.cronjob.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Map;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :28/10/2021 - 10:43 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryParamsIto {
    private Map<String, Object> filter;

    private int pageNumber = 1;

    private int pageSize = 10;

    @NotBlank
    @Size(min = 1, max = 50)
    private String sortField = "id";

    @NotBlank
    @Size(min = 1, max = 50)
    private String sortOrder;
}
