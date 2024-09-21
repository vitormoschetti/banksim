package com.banksim.domain.account.repository;

import com.banksim.domain.account.entity.Account;
import com.banksim.domain.shared.repository.IGenericRepository;

import java.util.UUID;

public interface AccountRepository extends IGenericRepository<Account, UUID> {
}
