package uo.ri.cws.application.service.spare.providers.crud.command;

import java.util.UUID;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.providers.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class AddProvider implements Command<ProviderDto> {

    ProviderGateway pg = Factories.persistence.forProvider();
    
    ProviderDto dto = new ProviderDto();
    
    public AddProvider(ProviderDto provider) {
        ArgumentChecks.isNotNull(provider,"El proveedor no puede ser null");
        
        ArgumentChecks.isNotBlank(provider.nif, "El nif no puede estar vacio");
        ArgumentChecks.isNotBlank(provider.name,"El nombre no puede estar vacio");
        validateEmail(provider.email);
        ArgumentChecks.isNotBlank(provider.phone,"El telefono no puede estar vacio");
            
        dto.id=UUID.randomUUID().toString();
        dto.version=1L;
        
        dto.nif = provider.nif;
        dto.name = provider.name;
        dto.email = provider.email;
        dto.phone=provider.phone;
    }
    
    @Override
    public ProviderDto execute() throws BusinessException {
        checkNif();
        checkFields();
        
        
        
        pg.add(DtoAssembler.toRecord(dto));
        
        return dto;
    }

    private void checkFields() throws BusinessException {
        BusinessChecks.doesNotExist( pg.findEqualsFields(DtoAssembler.toRecord(dto)),
            "Ya existe un proveedor con ese nif");
        
    }

    private void checkNif() throws BusinessException {
       BusinessChecks.doesNotExist( pg.findByNif(dto.nif),
           "Ya existe un proveedor con ese nif");
        
    }
    
    private void validateEmail(String email) {
        ArgumentChecks.isNotBlank(email,"El correo no puede estar vacio");
        ArgumentChecks.isTrue(email.contains("@"),"EL correo es invalido");
        ArgumentChecks.isTrue(email.contains(".com"),"EL correo es invalido");
        
    }

}
