package fr.jadde.service;

import fr.jadde.database.entity.UserEntity;
import fr.jadde.service.util.SecurityUtils;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.SecurityContext;

@ApplicationScoped
public class UserService {

    public Uni<UserEntity> createIfNecessary(final SecurityContext context) {
        return this.createIfNecessary(SecurityUtils.extractUserId(context));
    }

    public Uni<UserEntity> createIfNecessary(final String userIdentifier) {
        return UserEntity.<UserEntity>findById(userIdentifier)
                .onItem()
                .ifNull()
                .switchTo(() -> {
                    final UserEntity newUser = new UserEntity();
                    newUser.setId(userIdentifier);
                    return newUser.persistAndFlush();
                });
    }

}
