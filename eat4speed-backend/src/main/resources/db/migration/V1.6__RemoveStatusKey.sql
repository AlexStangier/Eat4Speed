SET FOREIGN_KEY_CHECKS=0;


alter table Auftrag drop key `fk_Auftrag_Status`;

SET FOREIGN_KEY_CHECKS=1;
