package de.kaiserpfalzEdv.office.tenants;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@PreAuthorize("hasAuthority('TENANT_READER')")
@RepositoryRestResource(
        collectionResourceRel = "tenants",
        itemResourceRel = "tenant",
        path= "tenants")
public interface TenantRepository extends PagingAndSortingRepository<Tenant, UUID> {

    Page<Tenant> findByDisplayName(@Param("name") String name, Pageable page);

    Page<Tenant> findByDisplayNumber(@Param("number") String number, Pageable page);
}
