package com.example;

import org.springframework.data.repository.CrudRepository;

interface TenantRepository extends CrudRepository<PersistentTenant, Long> {

}
