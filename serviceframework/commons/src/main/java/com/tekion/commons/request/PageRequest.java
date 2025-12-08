package com.tekion.commons.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageRequest {
    
    String cursor;
    @Builder.Default Integer pageSize = 20;
    @Builder.Default String sortDirection = "DESC";
    @Builder.Default String sortBy = "id";
    
    public void validate() {
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        pageSize = Math.min(pageSize, 100);
        if (sortDirection == null || (!sortDirection.equalsIgnoreCase("ASC") && !sortDirection.equalsIgnoreCase("DESC"))) {
            sortDirection = "DESC";
        }
        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }
    }
}

