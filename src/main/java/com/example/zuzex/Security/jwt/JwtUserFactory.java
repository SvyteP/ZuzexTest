package com.example.zuzex.Security.jwt;

import com.example.zuzex.entity.UserEntity;
/**
 * Implementation of Factory Method for class {@link JwtUser}.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(UserEntity user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getPassword()
        );
    }


}
