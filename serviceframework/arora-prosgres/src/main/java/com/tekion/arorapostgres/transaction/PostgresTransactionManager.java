package com.tekion.arorapostgres.transaction;

import com.tekion.arorapostgres.domain.BasePostgresDomain;
import com.tekion.arorapostgres.entity.BasePostgresEntity;
import com.tekion.arorapostgres.repo.BasePostgresRepo;
import com.tekion.arorapostgres.repo.BasePostgresRepoImpl;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

import static org.jooq.impl.DSL.using;

@Slf4j
@Component
public class PostgresTransactionManager {

    private static final ThreadLocal<DSLContext> CURRENT_TRANSACTION = new ThreadLocal<>();

    public static DSLContext getCurrentTransaction() {
        return CURRENT_TRANSACTION.get();
    }

    public static boolean isInTransaction() {
        return CURRENT_TRANSACTION.get() != null;
    }

    public <E extends BasePostgresEntity<ID>, D extends BasePostgresDomain, ID, T>
    T executeInTransaction(BasePostgresRepo<E, D, ID> repo, Supplier<T> operation) {
        DSLContext existingTx = CURRENT_TRANSACTION.get();

        if (existingTx != null) {
            log.debug("Reusing existing transaction for repository: {}", repo.getClass().getSimpleName());
            return operation.get();
        }

        log.debug("Starting new transaction using repository: {}", repo.getClass().getSimpleName());

        DSLContext dsl = ((BasePostgresRepoImpl<?, E, D, ID>) repo).getDefaultDslContext();

        return dsl.transactionResult(configuration -> {
            DSLContext txDsl = using(configuration);
            CURRENT_TRANSACTION.set(txDsl);

            try {
                T result = operation.get();
                log.debug("Transaction completed successfully");
                return result;
            } catch (Exception e) {
                log.error("Transaction failed, rolling back", e);
                throw e;
            } finally {
                CURRENT_TRANSACTION.remove();
                log.debug("ThreadLocal transaction context cleaned up");
            }
        });
    }

    public <E extends BasePostgresEntity<ID>, D extends BasePostgresDomain, ID>
    void executeInTransactionVoid(BasePostgresRepo<E, D, ID> repo, Runnable operation) {
        executeInTransaction(repo, () -> {
            operation.run();
            return null;
        });
    }
}
