#SET FOREIGN_KEY_CHECKS=0;

#alter table Bestellung drop constraint `Bestellung_Bestellhistorien_ID`;
#drop table Restaurant_Bestellhistorie;
#alter table Kunde drop constraint `fk_Kunde_Bestellhistorie`;
#ALTER TABLE Kunde DROP COLUMN Bestellhistorie;
#ALTER TABLE Bestellung DROP COLUMN Bestellhistorien_ID;
#drop Table Bestellhistorie;

#SET FOREIGN_KEY_CHECKS=1;
