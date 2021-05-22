
ALTER TABLE Benutzer
    DROP PRIMARY KEY,
    ADD PRIMARY KEY(Benutzer_ID,Benutzername,EmailAdresse);
