package com.bfrost.universite.service;

import com.bfrost.universite.service.dto.Pagination;
import org.springframework.data.domain.Page;

public interface PaginationService {

    Pagination instancierPagination(Page page);

}
