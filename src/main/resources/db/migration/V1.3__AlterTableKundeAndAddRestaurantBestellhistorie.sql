#
ALTER TABLE Kunde
    MODIFY COLUMN Bestellhistorie INT,
    ADD CONSTRAINT fk_Kunde_Bestellhistorie
        FOREIGN KEY (Bestellhistorie)
        REFERENCES Bestellhistorie(Bestellhistorien_ID);

create table if not exists  Restaurant_Bestellhistorie
(
    Restaurant_ID int not null,
    Bestellhistorien_ID int not null,
    primary key (Restaurant_ID, Bestellhistorien_ID),
    constraint fk_Restaurant_Bestellhistorie_Restaurant_ID
        foreign key (Restaurant_ID) references Restaurant (Restaurant_ID),
    constraint fk_Restaurant_Bestellhistorie_Bestellhistorien_ID
        foreign key (Bestellhistorien_ID) references Bestellhistorie (Bestellhistorien_ID)
);