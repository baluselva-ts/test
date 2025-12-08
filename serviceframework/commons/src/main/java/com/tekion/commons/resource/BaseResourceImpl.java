package com.tekion.commons.resource;

import com.tekion.commons.domain.BaseDomain;
import com.tekion.commons.entity.BaseEntity;
import com.tekion.commons.service.BaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseResourceImpl<E extends BaseEntity<ID>, D extends BaseDomain, ID> implements BaseResource<E, D, ID> {

    protected abstract BaseService<E, D, ID> getBaseService();

    @Override
    @PostMapping
    @Operation(summary = "Create entity", description = "Creates a new entity")
    public D create(@RequestBody @NonNull D domain) {
        return getBaseService().create(domain);
    }

    @Override
    @PostMapping("/bulk")
    @Operation(summary = "Create multiple entities", description = "Creates multiple entities in bulk")
    public List<D> createBulk(@RequestBody @NonNull List<D> domainList) {
        return getBaseService().createBulk(domainList);
    }

    @Override
    @GetMapping("/id/{id}")
    @Operation(summary = "Get entity by ID", description = "Retrieves an entity by its ID")
    public D getById(@PathVariable @Parameter(description = "Entity ID") @NonNull ID id) {
        return getBaseService().getById(id);
    }

    @Override
    @GetMapping("/ids")
    @Operation(summary = "Get entities by IDs", description = "Retrieves multiple entities by their IDs")
    public List<D> getByIds(@RequestParam @Parameter(description = "List of entity IDs") @NonNull List<ID> idList) {
        return getBaseService().getByIds(idList);
    }

    @Override
    @GetMapping("/all")
    @Operation(summary = "Get all entities", description = "Retrieves all entities")
    public List<D> getAll() {
        return getBaseService().getAll();
    }

    @Override
    @PutMapping
    @Operation(summary = "Upsert entity", description = "Updates an existing entity or creates a new one if it doesn't exist")
    public D upsert(@RequestBody @NonNull D domain) {
        return getBaseService().upsert(domain);
    }

    @Override
    @PutMapping("/bulk")
    @Operation(summary = "Upsert multiple entities", description = "Updates or creates multiple entities in bulk")
    public List<D> upsertBulk(@RequestBody @NonNull List<D> domainList) {
        return getBaseService().upsertBulk(domainList);
    }

    @Override
    @DeleteMapping("/id/{id}")
    @Operation(summary = "Delete entity by ID", description = "Deletes an entity by its ID")
    public D deleteById(@PathVariable @Parameter(description = "Entity ID") @NonNull ID id) {
        return getBaseService().deleteById(id);
    }

    @Override
    @DeleteMapping("/ids")
    @Operation(summary = "Delete multiple entities by IDs", description = "Deletes multiple entities by their IDs")
    public List<D> deleteByIdBulk(@RequestParam @Parameter(description = "List of entity IDs") @NonNull List<ID> idList) {
        return getBaseService().deleteByIdBulk(idList);
    }

    @Override
    @DeleteMapping
    @Operation(summary = "Delete entity", description = "Deletes an entity by domain object")
    public D delete(@RequestBody @NonNull D domain) {
        return getBaseService().delete(domain);
    }

    @Override
    @DeleteMapping("/bulk")
    @Operation(summary = "Delete multiple entities", description = "Deletes multiple entities by domain objects")
    public List<D> deleteBulk(@RequestBody @NonNull List<D> domainList) {
        return getBaseService().deleteBulk(domainList);
    }
}

