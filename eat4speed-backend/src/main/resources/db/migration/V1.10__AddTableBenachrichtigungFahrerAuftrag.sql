
create table if not exists  BenachrichtigungFahrerAuftrag
(
    Auftrags_ID int not null,
    Benachrichtigungs_ID int not null,
    primary key (Auftrags_ID, Benachrichtigungs_ID),
    constraint fk_BenachrichtigungFahrerAuftrag_Auftrags_ID
    foreign key (Auftrags_ID) references Auftrag (Auftrags_ID),
    constraint fk_BenachrichtigungFahrerAuftrag_Benachrichtigungs_ID
    foreign key (Benachrichtigungs_ID) references Benachrichtigung_Fahrer (Benachrichtigungs_ID)
);
