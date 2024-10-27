package uo.ri.cws.application.service.spare.providers.crud.command;

import java.util.Optional;

import uo.ri.conf.Factories;
import uo.ri.cws.application.persistence.provider.ProviderGateway;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;
import uo.ri.cws.application.service.spare.ProvidersCrudService.ProviderDto;
import uo.ri.cws.application.service.spare.providers.crud.DtoAssembler;
import uo.ri.cws.application.service.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessChecks;
import uo.ri.util.exception.BusinessException;

public class UpdateProvider implements Command<Void> {

    private ProviderGateway pg = Factories.persistence.forProvider();

    private ProviderDto dto = new ProviderDto();

    ProviderRecord prov;

    public UpdateProvider(ProviderDto provider) {
        ArgumentChecks.isNotNull(provider, "El proveedor no puede ser null");

        ArgumentChecks.isNotBlank(provider.nif, "El nif no puede estar vacio");

        ArgumentChecks.isNotBlank(provider.name,
            "El nombre no puede estar vacio");

        validateEmail(provider.email);

        ArgumentChecks.isNotBlank(provider.phone,
            "El telefono no puede estar vacio");

        dto.nif = provider.nif;
        dto.name = provider.name;
        dto.email = provider.email;
        dto.phone = provider.phone;
        dto.version = provider.version;
    }

    @Override
    public Void execute() throws BusinessException {
        checkNif();
        checkFields();
        checkVersion();

        pg.update(DtoAssembler.toRecord(dto));
        return null;
    }

    /**
     * Comprueba que la version sea correcta
     * @throws BusinessException si no lo es
     */
    private void checkVersion() throws BusinessException {
        BusinessChecks.isTrue(dto.version == prov.version,
            "version desactualizada");

    }

    /**
     * Comprueba que el email tenga un formato valido
     * 
     * @param email
     * @throws IllegalArgumentException si no lo tiene
     */
    private void validateEmail(String email) {
        ArgumentChecks.isNotBlank(email, "El correo no puede estar vacio");
        ArgumentChecks.isTrue(email.contains("@"), "EL correo necesita un @");
        ArgumentChecks.isTrue(email.contains(".com"),
            "EL correo necesita un .com");

    }

    /**
     * Comprueba que no se repitan los campos nombre, email and phone
     * 
     * @throws BusinessException si se repiten
     */
    private void checkFields() throws BusinessException {
        BusinessChecks.doesNotExist(
            pg.findEqualsFields(DtoAssembler.toRecord(dto)),
            "Ya existe un proveedor con esos campos");

    }

    /**
     * Comprueba si existe un proveedor con ese nif
     * 
     * @throws BusinessException si no existe
     */
    private void checkNif() throws BusinessException {
        Optional<ProviderRecord> rec = pg.findByNif(dto.nif);
        BusinessChecks.exists(rec, "no existe un proveedor con ese nif");
        this.dto.id = rec.get().id;
        prov = rec.get();
    }

}
