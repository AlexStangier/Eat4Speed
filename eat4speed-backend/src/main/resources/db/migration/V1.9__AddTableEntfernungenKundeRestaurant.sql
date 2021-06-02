
create table if not exists  EntfernungKundeRestaurant
(
    Kundennummer int not null,
    Restaurant_ID int not null,
    Entfernung double,
    primary key (Kundennummer, Restaurant_ID),
    constraint fk_EntfernungKundeRestaurant_Kundennummer
    foreign key (Kundennummer) references Kunde (Kundennummer),
    constraint fk_EntfernungKundeRestaurant_Restaurant_ID
    foreign key (Restaurant_ID) references Restaurant (Restaurant_ID)
);
