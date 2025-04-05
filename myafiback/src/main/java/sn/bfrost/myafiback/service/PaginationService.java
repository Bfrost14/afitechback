package sn.bfrost.myafiback.service;

import org.springframework.data.domain.Page;
import sn.bfrost.myafiback.service.dto.Pagination;

public interface PaginationService {

    Pagination instancierPagination(Page page);

}
