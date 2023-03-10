create table COCKTAIL_SHOPPING_LIST
(
    COCKTAIL_ID UUID,
    SHOPPING_LIST_ID UUID,
    foreign key (COCKTAIL_ID) references COCKTAIL(ID),
    foreign key (SHOPPING_LIST_ID) references SHOPPING_LIST(ID),
    primary key (COCKTAIL_ID, SHOPPING_LIST_ID)
);