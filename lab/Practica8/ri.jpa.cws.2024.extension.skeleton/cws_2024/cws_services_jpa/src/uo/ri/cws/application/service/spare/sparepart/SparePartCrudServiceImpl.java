package uo.ri.cws.application.service.spare.sparepart;

import java.util.Optional;

import uo.ri.cws.application.service.spare.SparePartCrudService;
import uo.ri.util.exception.BusinessException;

public class SparePartCrudServiceImpl implements SparePartCrudService {

    @Override
    public SparePartDto add(SparePartDto dto) throws BusinessException {
        return null;
    }

    @Override
    public void delete(String code) throws BusinessException {

    }

    @Override
    public void update(SparePartDto dto) throws BusinessException {

    }

    @Override
    public Optional<SparePartDto> findByCode(String code)
        throws BusinessException {
        return Optional.empty();
    }

}
