package com.bfrost.universite.service.impl;

import com.bfrost.universite.service.PaginationService;
import com.bfrost.universite.service.dto.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service

public class PaginationServiceImpl implements PaginationService {

    @Override
    public Pagination instancierPagination(Page page)
    {
        Pagination pagination=new Pagination();
        pagination.setPage(page.getNumber());
        pagination.setLength(page.getTotalElements());
        pagination.setSize(page.getSize());
        pagination.setSort(Sort.by(Sort.Direction.DESC, "id"));
        return pagination;
    }


}
