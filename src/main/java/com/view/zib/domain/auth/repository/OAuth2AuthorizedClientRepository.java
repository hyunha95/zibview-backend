package com.view.zib.domain.auth.repository;

import com.view.zib.domain.auth.entity.OAuth2AuthorizedClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2AuthorizedClientRepository extends JpaRepository<OAuth2AuthorizedClient, String> {


}
