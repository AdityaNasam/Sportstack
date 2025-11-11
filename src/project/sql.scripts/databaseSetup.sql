drop table ATHLETE cascade constraints;
drop table BROADCASTS cascade constraints;
drop table COACH cascade constraints;
drop table FUNDS cascade constraints;
drop table GAMEADDRESS cascade constraints;
drop table GAMEADDRESSDATE cascade constraints;
drop table GAMEDATEYEAR cascade constraints;
drop table GAMEHOSTSINSEASON cascade constraints;
drop table GAMETEAMAOPPONENT cascade constraints;
drop table GAMETEAMASCORE cascade constraints;
drop table GAMETEAMBSCORE cascade constraints;
drop table INR cascade constraints;
drop table LEAGUEORGANISES cascade constraints;
drop table LEAGUETEAMA cascade constraints;
drop table LEAGUETEAMB cascade constraints;
drop table NETWORK cascade constraints;
drop table OWNER cascade constraints;
drop table PLAYSFOR cascade constraints;
drop table SEASONCHAMPION cascade constraints;
drop table SPONSOR cascade constraints;
drop table SPORT cascade constraints;
drop table SPORTSPERSON cascade constraints;
drop table SPORTSPERSONAGE cascade constraints;
drop table SPORTSPERSONDEATHDATE cascade constraints;
drop table SPORTSPERSONDOB cascade constraints;
drop table SPORTSPERSONNATIONALITY cascade constraints;
drop table SPORTSPERSONNAME cascade constraints;
drop table SPORTSPERSONSALARY cascade constraints;
drop table STADIUM cascade constraints;
drop table STARTENDDAYSRUN cascade constraints;
drop table TEAMHOMESTADIUM cascade constraints;

CREATE TABLE Sport (
    sportName VARCHAR(255) PRIMARY KEY,
    rules VARCHAR(4000)
);

CREATE TABLE Stadium (
    address VARCHAR(255) PRIMARY KEY,
    sName VARCHAR(255),
    seatCount INT
);

CREATE TABLE TeamHomeStadium (
    tName VARCHAR(255),
    city VARCHAR(255),
    address VARCHAR(255) NOT NULL,
    sportName VARCHAR(255) NOT NULL,
    mascot VARCHAR(100),
    PRIMARY KEY (tName, city),
    FOREIGN KEY (address) REFERENCES Stadium(address) ON DELETE CASCADE,
    FOREIGN KEY (sportName) REFERENCES Sport(sportName) ON DELETE CASCADE
);

CREATE TABLE LeagueOrganises (
    lName VARCHAR(255),
    tier INT,
    revenue FLOAT,
    sportName VARCHAR(255) NOT NULL,
    PRIMARY KEY (lName),
    FOREIGN KEY (sportName) REFERENCES Sport(sportName) ON DELETE CASCADE
);

CREATE TABLE PlaysFor (
    tName VARCHAR(255),
    city VARCHAR(255),
    lName VARCHAR(255),
    PRIMARY KEY (tName, city, lName),
    FOREIGN KEY (tName, city) REFERENCES TeamHomeStadium(tName, city) ON DELETE CASCADE,
    FOREIGN KEY (lName) REFERENCES LeagueOrganises(lName) ON DELETE CASCADE
);

CREATE TABLE StartEndDaysRun (
    startDate DATE,
    endDate DATE,
    daysRun INT NOT NULL,
    PRIMARY KEY (startDate, endDate)
);

CREATE TABLE SeasonChampion (
    lName VARCHAR(255),
    year INT,
    startDate DATE,
    endDate DATE,
    tName VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    PRIMARY KEY (lName, year),
    FOREIGN KEY (lName) REFERENCES LeagueOrganises(lName) ON DELETE CASCADE,
    FOREIGN KEY (startDate, endDate) REFERENCES StartEndDaysRun(startDate, endDate)
);

CREATE TABLE GameHostsInSeason(
    tNameA	VARCHAR(255),
    cityA   VARCHAR(255),
    gameDate DATE,
    PRIMARY KEY (tNameA, cityA, gameDate),
    FOREIGN KEY (tNameA, cityA) REFERENCES TeamHomeStadium(tName, city) ON DELETE CASCADE
);

CREATE TABLE Network (
    nName VARCHAR(255) PRIMARY KEY,
    channel INT
);

CREATE TABLE Broadcasts (
    nName VARCHAR(255),
    tNameA VARCHAR(255),
    cityA VARCHAR(255),
    gameDate DATE,
    liveStartTime VARCHAR(5),
    PRIMARY KEY (nName, tNameA, cityA, gameDate),
    FOREIGN KEY (nName) REFERENCES Network(nName) ON DELETE CASCADE,
    FOREIGN KEY (tNameA, cityA, gameDate) REFERENCES GameHostsInSeason(tNameA, cityA, gameDate) ON DELETE CASCADE
);

CREATE TABLE Sponsor (
    sponName VARCHAR(255) PRIMARY KEY,
    networth FLOAT
);

CREATE TABLE Funds (
    sponName VARCHAR(255),
    lName VARCHAR(255),
    monetaryContribution FLOAT,
    PRIMARY KEY (sponName, lName),
    FOREIGN KEY (sponName) REFERENCES Sponsor(sponName),
    FOREIGN KEY (lName) REFERENCES LeagueOrganises(lName)
);

CREATE TABLE GameDateYear(
    tNameA	VARCHAR(255),
    cityA   VARCHAR(255),
    gameDate DATE,
    yearL INT NOT NULL,
    PRIMARY KEY (tNameA, cityA, gameDate),
    FOREIGN KEY (tNameA, cityA, gameDate) REFERENCES GameHostsInSeason(tNameA, cityA, gameDate) ON DELETE CASCADE
);

CREATE TABLE GameTeamAOpponent(
    tNameA VARCHAR(255),
    cityA VARCHAR(255),
    gameDate DATE,
    tNameB VARCHAR(255) NOT NULL,
    cityB VARCHAR(255) NOT NULL,
    PRIMARY KEY (tNameA, cityA, gameDate),
    FOREIGN KEY (tNameA, cityA, gameDate) REFERENCES GameHostsInSeason(tNameA, cityA, gameDate) ON DELETE CASCADE,
    FOREIGN KEY (tNameB, cityB) REFERENCES TeamHomeStadium(tName, city) ON DELETE CASCADE
);

CREATE TABLE GameAddressDate(
    tNameA	VARCHAR(255),
    cityA   VARCHAR(255),
    gameDate DATE,
    address	VARCHAR(255),
    weather	VARCHAR(255),
    PRIMARY KEY (tNameA, cityA, gameDate, address),
    FOREIGN KEY (address) REFERENCES Stadium(address) ON DELETE CASCADE,
    FOREIGN KEY (tNameA, cityA, gameDate) REFERENCES GameHostsInSeason(tNameA, cityA, gameDate) ON DELETE CASCADE
);

CREATE TABLE GameTeamAScore(
    tNameA VARCHAR(255),
    cityA VARCHAR(255),
    gameDate DATE,
    scoreA INT,
    PRIMARY KEY (tNameA, cityA, gameDate),
    FOREIGN KEY (tNameA, cityA, gameDate) REFERENCES GameHostsInSeason(tNameA, cityA, gameDate) ON DELETE CASCADE
);

CREATE TABLE GameTeamBScore(
    tNameA VARCHAR(255),
    cityA VARCHAR(255),
    gameDate DATE,
    scoreB INT,
    PRIMARY KEY (tNameA, cityA, gameDate),
    FOREIGN KEY (tNameA, cityA, gameDate) REFERENCES GameHostsInSeason(tNameA, cityA, gameDate) ON DELETE CASCADE
);

CREATE TABLE GameAddress(
    tNameA VARCHAR(255),
    cityA VARCHAR(255),
    gameDate DATE,
    address	VARCHAR(255) NOT NULL,
    PRIMARY KEY (tNameA, cityA, gameDate),
    FOREIGN KEY (tNameA, cityA, gameDate) REFERENCES GameHostsInSeason(tNameA, cityA, gameDate) ON DELETE CASCADE,
    FOREIGN KEY (address) REFERENCES Stadium(address) ON DELETE CASCADE
);

CREATE TABLE LeagueTeamA(
    tNameA VARCHAR(255),
    cityA VARCHAR(255),
    lName VARCHAR(255) NOT NULL,
    PRIMARY KEY (tNameA, cityA),
    FOREIGN KEY (lName) REFERENCES LeagueOrganises(lName) ON DELETE CASCADE,
    FOREIGN KEY (tNameA, cityA) REFERENCES TeamHomeStadium(tName, city) ON DELETE CASCADE
);

CREATE TABLE sportsPerson (
    spID INTEGER,
    phoneNumber NUMBER(10,0) UNIQUE,
    PRIMARY KEY(spID)
);

CREATE TABLE InR (
    spID INT,
    tName VARCHAR(255),
    city VARCHAR(255),
    PRIMARY KEY (spID, tName, city),
    FOREIGN KEY (spID) REFERENCES SportsPerson(spID) ON DELETE CASCADE,
    FOREIGN KEY (tName, city) REFERENCES TeamHomeStadium(tName, city) ON DELETE CASCADE
);

CREATE TABLE sportsPersonSalary (
    spID INTEGER,
    salary FLOAT,
    PRIMARY KEY(spID),
    FOREIGN KEY(spID) REFERENCES sportsPerson(spID) ON DELETE CASCADE
);

CREATE TABLE sportsPersonNationality(
    spID INTEGER,
    nationality VARCHAR(255),
    PRIMARY KEY(spID),
    FOREIGN KEY(spID) REFERENCES sportsPerson(spID) ON DELETE CASCADE
);

CREATE TABLE  sportsPersonDOB(
    phoneNumber NUMBER(10,0),
    birthDate DATE NOT NULL,
    PRIMARY KEY(phoneNumber),
    FOREIGN KEY (phoneNumber) REFERENCES sportsPerson(phoneNumber) ON DELETE CASCADE
);

CREATE TABLE sportsPersonName(
     spID INTEGER,
     spName VARCHAR(255),
     PRIMARY KEY(spID),
     FOREIGN KEY(spID) REFERENCES sportsPerson(spID) ON DELETE CASCADE
);

CREATE TABLE sportsPersonDeathDate(
    spID INTEGER,
    deathDate DATE,
    PRIMARY KEY(spID),
    FOREIGN KEY (spID) REFERENCES sportsPerson(spID) ON DELETE CASCADE
);

CREATE TABLE sportsPersonAge (
    spID INTEGER,
    age INTEGER NOT NULL,
    PRIMARY KEY(spID),
    FOREIGN KEY (spID) REFERENCES sportsPerson(spID) ON DELETE CASCADE
);

CREATE TABLE Athlete (
    spID INTEGER,
    weight FLOAT,
    height FLOAT,
    jerseyID INTEGER,
    numberOfMVPs INTEGER,
    PRIMARY KEY (spID),
    FOREIGN KEY (spID) REFERENCES sportsPerson(spID) ON DELETE CASCADE
);

CREATE TABLE Coach (
    spID INTEGER,
    yearsCoaching INTEGER,
    numberOfChampionships INTEGER,
    PRIMARY KEY (spID),
    FOREIGN KEY (spID) REFERENCES sportsPerson(spID) ON DELETE CASCADE
);

CREATE TABLE Owner (
    spID INTEGER,
    netWorth FLOAT,
    PRIMARY KEY (spID),
    FOREIGN KEY (spID) REFERENCES sportsPerson(spID) ON DELETE CASCADE
);

--insert

INSERT INTO Sport(sportName, rules) VALUES ('Ice Hockey', 'Puck into net');
INSERT INTO Sport(sportName, rules) VALUES ('Soccer', 'Ball into net');
INSERT INTO Sport(sportName, rules) VALUES ('Baseball', 'Bat hit ball');
INSERT INTO Sport(sportName, rules) VALUES ('American Football', 'Run into each other');
INSERT INTO Sport(sportName, rules) VALUES ('Basketball', 'Ball through net');

INSERT INTO Stadium(address, sName, seatCount)
VALUES ('800 Griffiths Way, Vancouver, BC V6B 6G1, Canada', 'Rogers Arena', 19700);
INSERT INTO Stadium(address, sName, seatCount)
VALUES ('10220 104 Ave NW, Edmonton, AB T5J 0H6, Canada', 'Rogers Place', 18347);
INSERT INTO Stadium(address, sName, seatCount)
VALUES ('Sir Matt Busby Way, Old Trafford, Stretford, Manchester M16 0RA, UK', 'Old Trafford', 74310);
INSERT INTO Stadium(address, sName, seatCount)
VALUES ('Etihad Stadium, Ashton New Rd, Manchester M11 3FF, UK', 'Etihad Stadium', 54400);
INSERT INTO Stadium(address, sName, seatCount)
VALUES ('6288 Stadium Rd, Vancouver, BC V6T 1Z3, Canada', 'Thunderbird Stadium', 5054);
INSERT INTO Stadium(address, sName, seatCount)
VALUES ('334 1st Ave N, Seattle, WA 98109, USA', 'Climate Pledge Arena', 18300);
INSERT INTO Stadium(address, sName, seatCount)
VALUES ('1111 S Figueroa St, Los Angeles, CA 90015, USA', 'Crypto Arena', 20000);
INSERT INTO Stadium(address, sName, seatCount)
VALUES ('1 Blue Jays Way, Toronto, Ontario, Canada', 'Rogers Centre', 49286);

INSERT INTO TeamHomeStadium(tName, city, address, sportName, mascot)
VALUES ('Canucks', 'Vancouver', '800 Griffiths Way, Vancouver, BC V6B 6G1, Canada', 'Ice Hockey', 'Fin the Whale');
INSERT INTO TeamHomeStadium(tName, city, address, sportName, mascot)
VALUES ('Oilers', 'Edmonton', '10220 104 Ave NW, Edmonton, AB T5J 0H6, Canada', 'Ice Hockey', 'Hunter the Lynx');
INSERT INTO TeamHomeStadium(tName, city, address, sportName)
VALUES ('Manchester United F.C.', 'Manchester', 'Sir Matt Busby Way, Old Trafford, Stretford, Manchester M16 0RA, UK', 'Soccer');
INSERT INTO TeamHomeStadium(tName, city, address, sportName)
VALUES ('Manchester City F.C.', 'Manchester', 'Etihad Stadium, Ashton New Rd, Manchester M11 3FF, UK', 'Soccer');
INSERT INTO TeamHomeStadium(tName, city, address, sportName, mascot)
VALUES ('UBC Thunderbirds', 'Vancouver', '6288 Stadium Rd, Vancouver, BC V6T 1Z3, Canada', 'American Football', 'Thunderbird');
INSERT INTO TeamHomeStadium(tName, city, address, sportName, mascot)
VALUES ('Kraken', 'Seattle', '334 1st Ave N, Seattle, WA 98109, USA', 'Ice Hockey', 'Buoy');
INSERT INTO TeamHomeStadium(tName, city, address, sportName)
VALUES ('Lakers', 'Los Angeles', '1111 S Figueroa St, Los Angeles, CA 90015, USA', 'Basketball');
INSERT INTO TeamHomeStadium(tName, city, address, sportName)
VALUES ('Blue Jays', 'Toronto', '1 Blue Jays Way, Toronto, Ontario, Canada', 'Baseball');

INSERT INTO LeagueOrganises(lName, tier, revenue, sportName)
VALUES ('NHL', 1, 222.11, 'Ice Hockey');
INSERT INTO LeagueOrganises(lName, tier, revenue, sportName)
VALUES ('Premier League', 1, 326.22, 'Soccer');
INSERT INTO LeagueOrganises(lName, tier, revenue, sportName)
VALUES ('U Sports', 2, 385.33, 'American Football');
INSERT INTO LeagueOrganises(lName, tier, revenue, sportName)
VALUES ('NBA', 1, 547.44, 'Basketball');
INSERT INTO LeagueOrganises(lName, tier, revenue, sportName)
VALUES ('MLB', 1, 784.55, 'Baseball');

INSERT INTO PlaysFor(tName, city, lName) VALUES ('Canucks', 'Vancouver', 'NHL');
INSERT INTO PlaysFor(tName, city, lName) VALUES ('Oilers', 'Edmonton', 'NHL');
INSERT INTO PlaysFor(tName, city, lName) VALUES ('Manchester United F.C.', 'Manchester', 'Premier League');
INSERT INTO PlaysFor(tName, city, lName) VALUES ('UBC Thunderbirds', 'Vancouver', 'U Sports');
INSERT INTO PlaysFor(tName, city, lName) VALUES ('Kraken', 'Seattle', 'NHL');
INSERT INTO PlaysFor(tName, city, lName) VALUES ('Lakers', 'Los Angeles', 'NBA');
INSERT INTO PlaysFor(tName, city, lName) VALUES ('Blue Jays', 'Toronto', 'MLB');

INSERT INTO StartEndDaysRun(startDate, endDate, daysRun)
VALUES (TO_DATE('2012-01-01', 'YYYY-MM-DD'), TO_DATE('2012-12-31', 'YYYY-MM-DD'), 366);
INSERT INTO StartEndDaysRun(startDate, endDate, daysRun)
VALUES (TO_DATE('2012-01-01', 'YYYY-MM-DD'), TO_DATE('2012-01-01', 'YYYY-MM-DD'), 1);
INSERT INTO StartEndDaysRun(startDate, endDate, daysRun)
VALUES (TO_DATE('2012-01-01', 'YYYY-MM-DD'), TO_DATE('2012-01-02', 'YYYY-MM-DD'), 2);
INSERT INTO StartEndDaysRun(startDate, endDate, daysRun)
VALUES (TO_DATE('2012-01-01', 'YYYY-MM-DD'), TO_DATE('2012-01-03', 'YYYY-MM-DD'), 3);
INSERT INTO StartEndDaysRun(startDate, endDate, daysRun)
VALUES (TO_DATE('2012-01-01', 'YYYY-MM-DD'), TO_DATE('2012-01-04', 'YYYY-MM-DD'), 4);
INSERT INTO StartEndDaysRun(startDate, endDate, daysRun)
VALUES (TO_DATE('2013-01-01','YYYY-MM-DD'), TO_DATE('2013-12-31', 'YYYY-MM-DD'), 365);
INSERT INTO StartEndDaysRun(startDate, endDate, daysRun)
VALUES (TO_DATE('2014-01-01', 'YYYY-MM-DD'), TO_DATE('2014-12-31', 'YYYY-MM-DD'), 365);
INSERT INTO StartEndDaysRun(startDate, endDate, daysRun)
VALUES (TO_DATE('2015-01-01', 'YYYY-MM-DD'), TO_DATE('2015-12-31', 'YYYY-MM-DD'), 365);
INSERT INTO StartEndDaysRun(startDate, endDate, daysRun)
VALUES (TO_DATE('2016-01-01', 'YYYY-MM-DD'), TO_DATE('2016-12-31', 'YYYY-MM-DD'), 365);

INSERT INTO SeasonChampion(lName, year, startDate, endDate, tName, city)
VALUES ('NHL', 2012, TO_DATE('2012-01-01', 'YYYY-MM-DD'), TO_DATE('2012-12-31', 'YYYY-MM-DD'), 'Canucks', 'Vancouver');
INSERT INTO SeasonChampion(lName, year, startDate, endDate, tName, city)
VALUES ('NHL', 2013, TO_DATE('2013-01-01','YYYY-MM-DD'), TO_DATE('2013-12-31', 'YYYY-MM-DD'), 'Canucks', 'Vancouver');
INSERT INTO SeasonChampion(lName, year, startDate, endDate, tName, city)
VALUES ('NHL', 2014, TO_DATE('2014-01-01', 'YYYY-MM-DD'), TO_DATE('2014-12-31', 'YYYY-MM-DD'), 'Canucks', 'Vancouver');
INSERT INTO SeasonChampion(lName, year, startDate, endDate, tName, city)
VALUES ('NHL', 2015, TO_DATE('2015-01-01', 'YYYY-MM-DD'), TO_DATE('2015-12-31', 'YYYY-MM-DD'), 'Canucks', 'Vancouver');
INSERT INTO SeasonChampion(lName, year, startDate, endDate, tName, city)
VALUES ('NHL', 2016, TO_DATE('2016-01-01', 'YYYY-MM-DD'), TO_DATE('2016-12-31', 'YYYY-MM-DD'), 'Canucks', 'Vancouver');

INSERT INTO		GameHostsInSeason(tNameA, cityA, gameDate)
VALUES 	('Canucks', 'Vancouver', TO_DATE('2022-10-12', 'YYYY-MM-DD'));
INSERT INTO		GameHostsInSeason(tNameA, cityA, gameDate)
VALUES 	('Kraken', 'Seattle', TO_DATE('2022-10-22', 'YYYY-MM-DD'));
INSERT INTO		GameHostsInSeason(tNameA, cityA, gameDate)
VALUES 	('Manchester United F.C.', 'Manchester', TO_DATE('2018-03-22', 'YYYY-MM-DD'));
INSERT INTO		GameHostsInSeason(tNameA, cityA, gameDate)
VALUES 	('Manchester City F.C.', 'Manchester', TO_DATE('2018-02-04', 'YYYY-MM-DD'));
INSERT INTO		GameHostsInSeason(tNameA, cityA, gameDate)
VALUES 	('Oilers', 'Edmonton', TO_DATE('1999-03-12', 'YYYY-MM-DD'));

INSERT INTO	Network(nName) VALUES ('ESPN');
INSERT INTO	Network(nName, channel) VALUES ('Sportsnet ONE', 418);
INSERT INTO	Network(nName) VALUES ('BBC');
INSERT INTO	Network(nName) VALUES ('CBC Sports');
INSERT INTO	Network(nName) VALUES ('FOX Sports 1');

INSERT INTO		Broadcasts(nName, tNameA, cityA, gameDate, liveStartTime)
VALUES 	('Sportsnet ONE', 'Canucks', 'Vancouver', TO_DATE('2022-10-12', 'YYYY-MM-DD'), '19:00');
INSERT INTO		Broadcasts(nName, tNameA, cityA, gameDate, liveStartTime)
VALUES 	('ESPN', 'Kraken', 'Seattle',TO_DATE('2022-10-22', 'YYYY-MM-DD'), '19:00');
INSERT INTO		Broadcasts(nName, tNameA, cityA, gameDate, liveStartTime)
VALUES 	('BBC', 'Manchester United F.C.', 'Manchester',TO_DATE('2018-03-22', 'YYYY-MM-DD'), '18:30');
INSERT INTO		Broadcasts(nName, tNameA, cityA, gameDate, liveStartTime)
VALUES 	('BBC', 'Manchester City F.C.', 'Manchester',TO_DATE('2018-02-04', 'YYYY-MM-DD'), '17:30');
INSERT INTO		Broadcasts(nName, tNameA, cityA, gameDate)
VALUES 	('CBC Sports', 'Oilers', 'Edmonton',TO_DATE('1999-03-12', 'YYYY-MM-DD'));

INSERT INTO		Sponsor(sponName, networth)
VALUES 	('Tesla', 808000000000);
INSERT INTO		Sponsor(sponName, networth)
VALUES 	('Ford Motor Company', 54000000000);
INSERT INTO		Sponsor(sponName, networth)
VALUES 	('Nike', 157000000000);
INSERT INTO		Sponsor(sponName, networth)
VALUES 	('Adidas', 36000000000);
INSERT INTO		Sponsor(sponName, networth)
VALUES 	('Scotiabank', 51000000000);

INSERT INTO		Funds(sponName, lName, monetaryContribution)
VALUES 	('Adidas', 'Premier League', 1500000);
INSERT INTO		Funds(sponName, lName, monetaryContribution)
VALUES 	('Scotiabank', 'NHL', 12000000);
INSERT INTO		Funds(sponName, lName)
VALUES 	('Tesla', 'MLB');
INSERT INTO		Funds(sponName, lName, monetaryContribution)
VALUES 	('Nike', 'NBA', 15000000);
INSERT INTO		Funds(sponName, lName)
VALUES 	('Ford Motor Company', 'NHL');

INSERT INTO		GameDateYear(tNameA, cityA, gameDate, yearL)
VALUES 	('Canucks', 'Vancouver', TO_DATE('2022-10-12', 'YYYY-MM-DD'), 2022);
INSERT INTO		GameDateYear(tNameA, cityA, gameDate, yearL)
VALUES 	('Kraken', 'Seattle', TO_DATE('2022-10-22', 'YYYY-MM-DD'), 2022);
INSERT INTO		GameDateYear(tNameA, cityA, gameDate, yearL)
VALUES 	('Manchester United F.C.', 'Manchester', TO_DATE('2018-03-22', 'YYYY-MM-DD'), 2018);
INSERT INTO		GameDateYear(tNameA, cityA, gameDate, yearL)
VALUES 	('Manchester City F.C.', 'Manchester', TO_DATE('2018-02-04', 'YYYY-MM-DD'), 2018);
INSERT INTO		GameDateYear(tNameA, cityA, gameDate, yearL)
VALUES 	('Oilers', 'Edmonton', TO_DATE('1999-03-12', 'YYYY-MM-DD'), 2018);

INSERT INTO		GameAddressDate(tNameA, cityA, gameDate, address, weather)
VALUES 	('Canucks', 'Vancouver', TO_DATE('2022-10-12', 'YYYY-MM-DD'), '800 Griffiths Way, Vancouver, BC V6B 6G1, Canada', 'Rain');
INSERT INTO		GameAddressDate(tNameA, cityA, gameDate, address, weather)
VALUES 	('Kraken', 'Seattle', TO_DATE('2022-10-22', 'YYYY-MM-DD'), '334 1st Ave N, Seattle, WA 98109, USA', 'Rain');
INSERT INTO		GameAddressDate(tNameA, cityA, gameDate, address, weather)
VALUES 	('Manchester United F.C.', 'Manchester', TO_DATE('2018-03-22', 'YYYY-MM-DD'), 'Sir Matt Busby Way, Old Trafford, Stretford, Manchester M16 0RA, UK', 'Rain');
INSERT INTO		GameAddressDate(tNameA, cityA, gameDate, address, weather)
VALUES 	('Manchester City F.C.', 'Manchester', TO_DATE('2018-02-04', 'YYYY-MM-DD'), 'Etihad Stadium, Ashton New Rd, Manchester M11 3FF, UK', 'Clear');
INSERT INTO		GameAddressDate(tNameA, cityA, gameDate, address, weather)
VALUES 	('Oilers', 'Edmonton', TO_DATE('1999-03-12', 'YYYY-MM-DD'), '10220 104 Ave NW, Edmonton, AB T5J 0H6, Canada', 'Snow');

INSERT INTO		GameTeamAScore(tNameA, cityA, gameDate, scoreA)
VALUES 	('Canucks', 'Vancouver', TO_DATE('2022-10-12', 'YYYY-MM-DD'), 4);
INSERT INTO		GameTeamAScore(tNameA, cityA, gameDate, scoreA)
VALUES 	('Kraken', 'Seattle', TO_DATE('2022-10-22', 'YYYY-MM-DD'), 7);
INSERT INTO		GameTeamAScore(tNameA, cityA, gameDate, scoreA)
VALUES 	('Manchester United F.C.', 'Manchester', TO_DATE('2018-03-22', 'YYYY-MM-DD'), 1);
INSERT INTO		GameTeamAScore(tNameA, cityA, gameDate, scoreA)
VALUES 	('Manchester City F.C.', 'Manchester', TO_DATE('2018-02-04', 'YYYY-MM-DD'), 1);
INSERT INTO		GameTeamAScore(tNameA, cityA, gameDate, scoreA)
VALUES 	('Oilers', 'Edmonton', TO_DATE('1999-03-12', 'YYYY-MM-DD'), 0);

INSERT INTO		GameTeamBScore(tNameA, cityA, gameDate, scoreB)
VALUES 	('Canucks', 'Vancouver', TO_DATE('2022-10-12', 'YYYY-MM-DD'), 3);
INSERT INTO		GameTeamBScore(tNameA, cityA, gameDate, scoreB)
VALUES 	('Kraken', 'Seattle', TO_DATE('2022-10-22', 'YYYY-MM-DD'), 2);
INSERT INTO		GameTeamBScore(tNameA, cityA, gameDate, scoreB)
VALUES 	('Manchester United F.C.', 'Manchester', TO_DATE('2018-03-22', 'YYYY-MM-DD'), 0);
INSERT INTO		GameTeamBScore(tNameA, cityA, gameDate, scoreB)
VALUES 	('Manchester City F.C.', 'Manchester', TO_DATE('2018-02-04', 'YYYY-MM-DD'), 1);
INSERT INTO		GameTeamBScore(tNameA, cityA, gameDate, scoreB)
VALUES 	('Oilers', 'Edmonton', TO_DATE('1999-03-12', 'YYYY-MM-DD'), 5);

INSERT INTO		GameAddress(tNameA, cityA, gameDate, address)
VALUES 	('Canucks', 'Vancouver', TO_DATE('2022-10-12', 'YYYY-MM-DD'), '800 Griffiths Way, Vancouver, BC V6B 6G1, Canada');
INSERT INTO		GameAddress(tNameA, cityA, gameDate, address)
VALUES 	('Kraken', 'Seattle', TO_DATE('2022-10-22', 'YYYY-MM-DD'), '334 1st Ave N, Seattle, WA 98109, USA');
INSERT INTO		GameAddress(tNameA, cityA, gameDate, address)
VALUES 	('Manchester United F.C.', 'Manchester', TO_DATE('2018-03-22', 'YYYY-MM-DD'), 'Sir Matt Busby Way, Old Trafford, Stretford, Manchester M16 0RA, UK');
INSERT INTO		GameAddress(tNameA, cityA, gameDate, address)
VALUES 	('Manchester City F.C.', 'Manchester', TO_DATE('2018-02-04', 'YYYY-MM-DD'), 'Etihad Stadium, Ashton New Rd, Manchester M11 3FF, UK');
INSERT INTO		GameAddress(tNameA, cityA, gameDate, address)
VALUES 	('Oilers', 'Edmonton', TO_DATE('1999-03-12', 'YYYY-MM-DD'), '10220 104 Ave NW, Edmonton, AB T5J 0H6, Canada');

INSERT INTO		GameTeamAOpponent(tNameA, cityA, gameDate, tNameB, cityB)
VALUES 	('Canucks', 'Vancouver', TO_DATE('2022-10-12', 'YYYY-MM-DD'), 'Oilers', 'Edmonton');
INSERT INTO		GameTeamAOpponent(tNameA, cityA, gameDate, tNameB, cityB)
VALUES 	('Kraken', 'Seattle', TO_DATE('2022-10-22', 'YYYY-MM-DD'), 'Canucks', 'Vancouver');
INSERT INTO		GameTeamAOpponent(tNameA, cityA, gameDate, tNameB, cityB)
VALUES 	('Manchester United F.C.', 'Manchester', TO_DATE('2018-03-22', 'YYYY-MM-DD'), 'Manchester City F.C.', 'Manchester');
INSERT INTO		GameTeamAOpponent(tNameA, cityA, gameDate, tNameB, cityB)
VALUES 	('Manchester City F.C.', 'Manchester', TO_DATE('2018-02-04', 'YYYY-MM-DD'), 'Manchester United F.C.', 'Manchester');
INSERT INTO		GameTeamAOpponent(tNameA, cityA, gameDate, tNameB, cityB)
VALUES 	('Oilers', 'Edmonton', TO_DATE('1999-03-12', 'YYYY-MM-DD'), 'Canucks', 'Vancouver');

INSERT INTO		LeagueTeamA(tNameA, cityA, lName)
VALUES 	('Canucks', 'Vancouver', 'NHL');
INSERT INTO		LeagueTeamA(tNameA, cityA, lName)
VALUES 	('Kraken', 'Seattle', 'NHL');
INSERT INTO		LeagueTeamA(tNameA, cityA, lName)
VALUES 	('Manchester United F.C.', 'Manchester', 'Premier League');
INSERT INTO		LeagueTeamA(tNameA, cityA, lName)
VALUES 	('Manchester City F.C.', 'Manchester', 'Premier League');
INSERT INTO		LeagueTeamA(tNameA, cityA, lName)
VALUES 	('Oilers', 'Edmonton', 'NHL');

INSERT INTO sportsPerson VALUES (100012,9982991031);
INSERT INTO sportsPerson VALUES (10221,2232112313);
INSERT INTO sportsPerson VALUES (223422,4533513411);
INSERT INTO sportsPerson VALUES (233134,4224121445);
INSERT INTO sportsPerson VALUES (758230,2908394142);
INSERT INTO sportsPerson VALUES (111111,1234567890);
INSERT INTO sportsPerson VALUES (222222,1234567891);
INSERT INTO sportsPerson VALUES (333333,1234567892);
INSERT INTO sportsPerson VALUES (444444,1234567893);
INSERT INTO sportsPerson VALUES (55555,1234567894);
INSERT INTO sportsPerson VALUES (12231,1234567895);
INSERT INTO sportsPerson VALUES (22,1234567896);
INSERT INTO sportsPerson VALUES (22324,1234567897);
INSERT INTO sportsPerson VALUES (45321,1234567898);
INSERT INTO sportsPerson VALUES (5231,1234567899);
INSERT INTO sportsPerson VALUES (346473,1234567880);
INSERT INTO sportsPerson VALUES (234,1234567881);
INSERT INTO sportsPerson VALUES (5345,1234567882);
INSERT INTO sportsPerson VALUES (3233,1234567883);
INSERT INTO sportsPerson VALUES (14325,1234567884);

INSERT INTO InR(spID, tName, city) VALUES (111111, 'Canucks', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (222222, 'Canucks', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (333333, 'Oilers', 'Edmonton');
INSERT INTO InR(spID, tName, city) VALUES (444444, 'Oilers', 'Edmonton');
INSERT INTO InR(spID, tName, city) VALUES (55555, 'UBC Thunderbirds', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (100012, 'Canucks', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (10221, 'Canucks', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (5345, 'Canucks', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (3233, 'Canucks', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (14325, 'Canucks', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (100012, 'UBC Thunderbirds', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (10221, 'UBC Thunderbirds', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (3233, 'UBC Thunderbirds', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (14325, 'UBC Thunderbirds', 'Vancouver');
INSERT INTO InR(spID, tName, city) VALUES (100012, 'Manchester United F.C.', 'Manchester');
INSERT INTO InR(spID, tName, city) VALUES (10221, 'Manchester United F.C.', 'Manchester');
INSERT INTO InR(spID, tName, city) VALUES (5345, 'Manchester United F.C.', 'Manchester');
INSERT INTO InR(spID, tName, city) VALUES (3233, 'Manchester United F.C.', 'Manchester');
INSERT INTO InR(spID, tName, city) VALUES (14325, 'Manchester United F.C.', 'Manchester');
INSERT INTO InR(spID, tName, city) VALUES (100012, 'Lakers', 'Los Angeles');
INSERT INTO InR(spID, tName, city) VALUES (10221, 'Lakers', 'Los Angeles');
INSERT INTO InR(spID, tName, city) VALUES (5345, 'Lakers', 'Los Angeles');
INSERT INTO InR(spID, tName, city) VALUES (3233, 'Lakers', 'Los Angeles');
INSERT INTO InR(spID, tName, city) VALUES (14325, 'Lakers', 'Los Angeles');
INSERT INTO InR(spID, tName, city) VALUES (100012, 'Blue Jays', 'Toronto');
INSERT INTO InR(spID, tName, city) VALUES (10221, 'Blue Jays', 'Toronto');
INSERT INTO InR(spID, tName, city) VALUES (5345, 'Blue Jays', 'Toronto');
INSERT INTO InR(spID, tName, city) VALUES (3233, 'Blue Jays', 'Toronto');
INSERT INTO InR(spID, tName, city) VALUES (14325, 'Blue Jays', 'Toronto');

INSERT INTO sportsPersonSalary VALUES(100012,23333332.24);
INSERT INTO sportsPersonSalary VALUES(223422,332424);
INSERT INTO sportsPersonSalary VALUES(758230,4758392);
INSERT INTO sportsPersonSalary VALUES(10221,245353242.2);
INSERT INTO sportsPersonSalary VALUES(233134,2242);

INSERT INTO sportsPersonNationality VALUES(100012,'China');
INSERT INTO sportsPersonNationality VALUES(223422,'Russia');
INSERT INTO sportsPersonNationality VALUES(10221,'Germany');
INSERT INTO sportsPersonNationality VALUES(233134,'Canada');
INSERT INTO sportsPersonNationality VALUES(758230,'Brazil');

INSERT INTO sportsPersonDOB VALUES (9982991031,TO_DATE('2000-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (2232112313,TO_DATE('2000-02-01', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (4533513411,TO_DATE('1980-10-11', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (4224121445,TO_DATE('1999-05-19', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (2908394142,TO_DATE('2001-04-01', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567890,TO_DATE('1989-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567891,TO_DATE('1989-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567892,TO_DATE('1999-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567893,TO_DATE('1978-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567894,TO_DATE('1989-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567895,TO_DATE('1987-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567896,TO_DATE('1983-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567897,TO_DATE('1989-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567898,TO_DATE('1981-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567899,TO_DATE('1989-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567880,TO_DATE('1980-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567881,TO_DATE('1989-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567882,TO_DATE('1984-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567883,TO_DATE('1977-10-12', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDOB VALUES (1234567884,TO_DATE('1982-10-12', 'YYYY-MM-DD'));

INSERT INTO sportsPersonName VALUES (100012,'Lebron James');
INSERT INTO sportsPersonName VALUES (10221,'Lionel Messi');
INSERT INTO sportsPersonName VALUES (223422,'Luka Doncic');
INSERT INTO sportsPersonName VALUES (233134,'James Harden');
INSERT INTO sportsPersonName VALUES (758230,'Aaron Rodgers');
INSERT INTO sportsPersonName VALUES (5345,'Frank DeMan');
INSERT INTO sportsPersonName VALUES (3233,'Alice Keys');
INSERT INTO sportsPersonName VALUES (14325,'Bob Shared');

INSERT INTO sportsPersonDeathDate VALUES (100012,NULL);
INSERT INTO sportsPersonDeathDate VALUES (10221,NULL);
INSERT INTO sportsPersonDeathDate VALUES (223422,TO_DATE('2013-11-20', 'YYYY-MM-DD'));
INSERT INTO sportsPersonDeathDate VALUES (233134,NULL);
INSERT INTO sportsPersonDeathDate VALUES (758230,NULL);

INSERT INTO sportsPersonAge VALUES (100012, 23);
INSERT INTO sportsPersonAge VALUES (10221, 22);
INSERT INTO sportsPersonAge VALUES (223422, 24);
INSERT INTO sportsPersonAge VALUES (233134, 33);
INSERT INTO sportsPersonAge VALUES (758230, 23);
INSERT INTO sportsPersonAge VALUES (111111,33);
INSERT INTO sportsPersonAge VALUES (222222,33);
INSERT INTO sportsPersonAge VALUES (333333,33);
INSERT INTO sportsPersonAge VALUES (444444,33);
INSERT INTO sportsPersonAge VALUES (55555,33);
INSERT INTO sportsPersonAge VALUES (12231,33);
INSERT INTO sportsPersonAge VALUES (22,33);
INSERT INTO sportsPersonAge VALUES (22324,33);
INSERT INTO sportsPersonAge VALUES (45321,33);
INSERT INTO sportsPersonAge VALUES (5231,33);
INSERT INTO sportsPersonAge VALUES (346473,33);
INSERT INTO sportsPersonAge VALUES (234,33);
INSERT INTO sportsPersonAge VALUES (5345,33);
INSERT INTO sportsPersonAge VALUES (3233,33);
INSERT INTO sportsPersonAge VALUES (14325,33);

INSERT INTO Athlete VALUES(100012,80,167,21,2);
INSERT INTO Athlete VALUES(10221,100,200,20,0);
INSERT INTO Athlete VALUES(223422,139,180,1,1);
INSERT INTO Athlete VALUES(233134,75,170,122,1);
INSERT INTO Athlete VALUES(758230,80,169,99,0);

INSERT INTO Coach VALUES(12231,10,2);
INSERT INTO Coach VALUES(22,2,2);
INSERT INTO Coach VALUES(22324,30,4);
INSERT INTO Coach VALUES(45321,20,1);
INSERT INTO Coach VALUES(5231,10,5);

INSERT INTO Owner VALUES(346473,773465863);
INSERT INTO Owner VALUES(234,23333);
INSERT INTO Owner VALUES(5345,787823);
INSERT INTO Owner VALUES(3233,900);
INSERT INTO Owner VALUES(14325,283283898);

Commit;