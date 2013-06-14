--------------------------------------------------------
--  File created - Tuesday-January-15-2013   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence NEWS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "NEWS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table NEWS
--------------------------------------------------------

  CREATE TABLE "NEWS" 
   (	"ID" NUMBER(*,0), 
	"TITLE" VARCHAR2(400), 
	"NEWS_DATE" DATE, 
	"BRIEF" VARCHAR2(1000), 
	"CONTENT" CLOB
   ) ;
--------------------------------------------------------
--  DDL for Trigger NEWS_TRIGGER
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "NEWS_TRIGGER" before insert on "NEWS"    for each row begin     if inserting then       if :NEW."ID" is null then          select NEWS_SEQ.nextval into :NEW."ID" from dual;       end if;    end if; end;
/
ALTER TRIGGER "NEWS_TRIGGER" ENABLE;
