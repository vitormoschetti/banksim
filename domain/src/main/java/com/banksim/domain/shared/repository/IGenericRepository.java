package com.banksim.domain.shared.repository;


import com.banksim.domain.shared.entity.IAggregateRoot;

import java.util.List;

public interface IGenericRepository<T extends IAggregateRoot, I> {

    void create(final T model);

    void update(final T model);

    T findById(final I id);

    List<T> findAll();

}
