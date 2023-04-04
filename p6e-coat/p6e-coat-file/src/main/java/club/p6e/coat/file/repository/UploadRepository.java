package club.p6e.coat.file.repository;

import club.p6e.coat.file.error.DataBaseException;
import club.p6e.coat.file.model.UploadModel;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 文件上传存储库
 *
 * @author lidashuang
 * @version 1.0
 */
@Component
@ConditionalOnMissingBean(
        value = UploadRepository.class,
        ignored = UploadRepository.class
)
public class UploadRepository extends BaseRepository {

    /**
     * R2dbcEntityTemplate 对象
     */
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    /**
     * 构造方法初始化
     *
     * @param r2dbcEntityTemplate R2dbcEntityTemplate 对象
     */
    public UploadRepository(R2dbcEntityTemplate r2dbcEntityTemplate) {
        this.r2dbcEntityTemplate = r2dbcEntityTemplate;
    }

    /**
     * 创建数据
     *
     * @param model 模型对象
     * @return Mono<UploadModel> 模型对象
     */
    public Mono<UploadModel> create(UploadModel model) {
        if (model == null) {
            throw new DataBaseException(this.getClass(),
                    "fun create() -> " + UploadModel.class + " is null", "UploadModel object data is null");
        }
        if (model.getSize() == null) {
            model.setSize(0);
        }
        if (model.getOperator() == null) {
            model.setOperator("sys");
        }
        model.setId(null);
        model.setLock(0);
        model.setVersion(0);
        model.setRubbish(0);
        model.setCreateDate(LocalDateTime.now());
        model.setUpdateDate(LocalDateTime.now());
        return r2dbcEntityTemplate.insert(model);
    }

    /**
     * 修改数据--锁增加 1
     *
     * @param id ID
     * @return Mono<Long> 受影响的数据条数
     */
    public Mono<Long> acquireLock(int id) {
        return acquireLock(id, 0);
    }

    /**
     * 修改数据--锁增加 1
     *
     * @param id    ID
     * @param retry 重试次数
     * @return Mono<Long> 受影响的数据条数
     */
    private Mono<Long> acquireLock(int id, int retry) {
        if (retry == 0) {
            return acquireLock0(id)
                    .flatMap(c -> c > 0 ? Mono.just(c) : acquireLock(id, (retry + 1)));
        } else if (retry <= MAX_RETRY_COUNT) {
            final long interval = RETRY_INTERVAL_DATE * ThreadLocalRandom.current().nextInt(100) / 100;
            return Mono.delay(Duration.of(interval, ChronoUnit.MILLIS))
                    .flatMap(r -> acquireLock0(id))
                    .flatMap(c -> c > 0 ? Mono.just(c) : acquireLock(id, (retry + 1)));
        } else {
            return Mono.just(0L);
        }
    }

    /**
     * 修改数据--锁增加 1
     *
     * @param id ID
     * @return Mono<Long> 受影响的数据条数
     */
    private Mono<Long> acquireLock0(int id) {
        return this.findById(id)
                .flatMap(m -> r2dbcEntityTemplate
                        .update(UploadModel.class)
                        .matching(Query.query(
                                Criteria.where(UploadModel.ID).is(m.getId())
                                        .and(UploadModel.VERSION).is(m.getVersion())))
                        .apply(Update.update(UploadModel.VERSION, m.getVersion() + 1)
                                .set(UploadModel.LOCK, m.getLock() + 1)));
    }

    /**
     * 修改数据--锁减少 1
     *
     * @param id ID
     * @return Mono<Long> 受影响的数据条数
     */
    public Mono<Long> releaseLock(int id) {
        return releaseLock(id, 0);
    }

    /**
     * 修改数据--锁减少 1
     *
     * @param id    ID
     * @param retry 重试次数
     * @return Mono<Long> 受影响的数据条数
     */
    private Mono<Long> releaseLock(int id, int retry) {
        if (retry == 0) {
            return releaseLock0(id)
                    .flatMap(c -> c > 0 ? Mono.just(c) : releaseLock(id, (retry + 1)));
        } else if (retry <= MAX_RETRY_COUNT) {
            final long interval = RETRY_INTERVAL_DATE * ThreadLocalRandom.current().nextInt(100) / 100;
            return Mono.delay(Duration.of(interval, ChronoUnit.MILLIS))
                    .flatMap(r -> releaseLock0(id))
                    .flatMap(c -> c > 0 ? Mono.just(c) : releaseLock(id, (retry + 1)));
        } else {
            return Mono.just(0L);
        }
    }

    /**
     * 修改数据--锁减少 1
     *
     * @param id ID
     * @return Mono<Long> 受影响的数据条数
     */
    private Mono<Long> releaseLock0(int id) {
        return this.findById(id)
                .flatMap(m -> r2dbcEntityTemplate.update(UploadModel.class)
                        .matching(Query.query(
                                Criteria.where(UploadModel.ID).is(m.getId())
                                        .and(UploadModel.VERSION).is(m.getVersion())))
                        .apply(Update.update(UploadModel.VERSION, m.getVersion() + 1)
                                .set(UploadModel.LOCK, m.getLock() - 1)));
    }

    /**
     * 关闭锁
     *
     * @param id 模型 ID
     * @return Mono<UploadModel> 受影响的数据条数
     */
    public Mono<Long> closeLock(int id) {
        return closeLock(id, 0);
    }

    /**
     * 关闭锁
     *
     * @param id    模型 ID
     * @param retry 重试次数
     * @return Mono<UploadModel> 受影响的数据条数
     */
    private Mono<Long> closeLock(int id, int retry) {
        if (retry == 0) {
            return closeLock0(id)
                    .flatMap(c -> c > 0 ? Mono.just(c) : closeLock(id, (retry + 1)));
        } else if (retry <= MAX_RETRY_COUNT) {
            final long interval = RETRY_INTERVAL_DATE * ThreadLocalRandom.current().nextInt(100) / 100;
            return Mono.delay(Duration.of(interval, ChronoUnit.MILLIS))
                    .flatMap(t -> closeLock0(id))
                    .flatMap(c -> c > 0 ? Mono.just(c) : closeLock(id, (retry + 1)));
        } else {
            return Mono.empty();
        }
    }

    /**
     * 关闭锁
     *
     * @param id 模型 ID
     * @return Mono<UploadModel> 受影响的数据条数
     */
    private Mono<Long> closeLock0(int id) {
        return this.findById(id)
                .flatMap(m -> {
                    if (m.getLock() == 0) {
                        return r2dbcEntityTemplate.update(UploadModel.class)
                                .matching(Query.query(
                                        Criteria.where(UploadModel.ID).is(m.getId())
                                                .and(UploadModel.VERSION).is(m.getVersion())))
                                .apply(Update.update(UploadModel.VERSION, m.getVersion() + 1).set(UploadModel.LOCK, -1));
                    } else {
                        return Mono.just(0L);
                    }
                });
    }

    /**
     * 根据 ID 查询数据
     *
     * @param id 模型 ID
     * @return Mono<UploadModel> 模型对象
     */
    public Mono<UploadModel> findById(int id) {
        return r2dbcEntityTemplate.selectOne(Query.query(
                Criteria.where(UploadModel.ID).is(id)), UploadModel.class);
    }

    /**
     * 根据 ID 或者创建时间范围查询数据
     *
     * @param id        模型 ID
     * @param startDate 创建时间的开始时间
     * @param endDate   创建时间的结束时间
     * @return Mono<UploadModel> 模型对象
     */
    public Mono<UploadModel> findByCreateDateOne(Integer id, LocalDateTime startDate, LocalDateTime endDate) {
        final Criteria criteria = Criteria.empty();
        if (id != null) {
            criteria.and(UploadModel.ID).greaterThan(id);
        }
        if (startDate != null) {
            criteria.and(UploadModel.CREATE_DATE).greaterThan(startDate);
        }
        if (endDate != null) {
            criteria.and(UploadModel.CREATE_DATE).lessThan(endDate);
        }
        return r2dbcEntityTemplate.selectOne(Query.query(criteria), UploadModel.class);
    }

    /**
     * 修改数据
     *
     * @param model 模型对象
     * @return Mono<UploadModel> 受影响的数据条数
     */
    public Mono<Long> update(UploadModel model) {
        Update update = null;
        if (model.getSize() != null) {
            update = Update.update(UploadModel.SIZE, model.getSize());
        }
        if (model.getRubbish() != null) {
            if (update == null) {
                update = Update.update(UploadModel.RUBBISH, model.getLock());
            } else {
                update.set(UploadModel.RUBBISH, model.getRubbish());
            }
        }
        if (update == null) {
            return Mono.just(0L);
        } else {
            final Update fUpdate = update;
            return this.findById(model.getId())
                    .flatMap(m -> r2dbcEntityTemplate
                            .update(UploadModel.class)
                            .matching(Query.query(
                                    Criteria.where(UploadModel.ID).is(m.getId())
                                            .and(UploadModel.VERSION).is(m.getVersion())))
                            .apply(fUpdate)
                    );
        }
    }

    /**
     * 根据 ID 删除数据
     *
     * @param id ID
     * @return Mono<Long> 受影响的数据条数
     */
    public Mono<Long> deleteById(int id) {
        return r2dbcEntityTemplate.delete(Query.query(Criteria.where(UploadModel.ID).is(id)), UploadModel.class);
    }
}
