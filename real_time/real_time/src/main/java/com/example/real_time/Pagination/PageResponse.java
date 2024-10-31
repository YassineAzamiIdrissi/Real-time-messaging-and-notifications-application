package com.example.real_time.Pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResponse<T> {
    private boolean isFirst;
    private boolean isLast;
    private List<T> content;
    private long totalElements;
    private int totalPages;
    private int number;
    private int size;
}
