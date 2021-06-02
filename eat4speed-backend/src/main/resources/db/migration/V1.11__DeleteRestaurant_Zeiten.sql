# SET FOREIGN_KEY_CHECKS=0;
#
# alter table Restaurant_Zeiten drop constraint `fk_Restaurant_Zeiten_Oeffnungszeiten_ID`;
# alter table Restaurant_Zeiten drop constraint `fk_Restaurant_Zeiten_Restaurant_ID`;
# drop table Restaurant_Zeiten;
#
# ALTER TABLE Oeffnungszeiten ADD Restaurant_ID int;
#
# SET FOREIGN_KEY_CHECKS=1;
