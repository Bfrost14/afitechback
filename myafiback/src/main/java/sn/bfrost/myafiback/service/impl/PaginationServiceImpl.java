package sn.bfrost.myafiback.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sn.bfrost.myafiback.service.PaginationService;
import sn.bfrost.myafiback.service.dto.Pagination;

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
