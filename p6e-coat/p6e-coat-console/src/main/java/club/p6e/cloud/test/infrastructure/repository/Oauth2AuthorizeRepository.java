package club.p6e.cloud.test.infrastructure.repository;

import club.p6e.cloud.test.infrastructure.model.Oauth2AuthorizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author lidashuang
 * @version 1.0
 */
public interface Oauth2AuthorizeRepository extends
        JpaRepository<Oauth2AuthorizeModel, Integer>,
        JpaSpecificationExecutor<Oauth2AuthorizeModel> {
}
