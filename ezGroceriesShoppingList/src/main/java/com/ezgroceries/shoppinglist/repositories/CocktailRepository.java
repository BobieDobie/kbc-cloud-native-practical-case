package com.ezgroceries.shoppinglist.repositories;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CocktailRepository extends CrudRepository<CocktailEntity, UUID> {
}
