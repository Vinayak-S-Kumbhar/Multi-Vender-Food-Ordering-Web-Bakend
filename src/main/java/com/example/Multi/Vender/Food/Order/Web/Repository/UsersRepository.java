package com.example.Multi.Vender.Food.Order.Web.Repository;//package com.example.Multi.Vender.Food.Order.Web.Repository;

import com.example.Multi.Vender.Food.Order.Web.Entity.User;
import com.example.Multi.Vender.Food.Order.Web.config.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByProviderTypeAndProviderId(ProviderType providerType , String providerId);

}
