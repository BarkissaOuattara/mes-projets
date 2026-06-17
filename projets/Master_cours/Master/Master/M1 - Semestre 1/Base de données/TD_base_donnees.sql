#Creation de tables
CREATE TABLE ARTICLE(
	No_art int not null unique
	libelle varchar(30) not null
	stock int not null
	mnt_prix_invent decimal(30) not null
	primary key (No_art)
)

CREATE TABLE FOURNISSEUR(
	No_four int not null unique
	nom_four varchar(30)
	adr_achat varchar(30) 
)

CREATE TABLE ACHETER(
	mnt_prix_achat decimal(30)
	delai int 
	foreign key (No_art, No_four)
		references ARTICLE(No_art)
		references FOURNISSEUR(No_four) 
)