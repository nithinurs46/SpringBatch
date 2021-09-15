 CREATE TABLE "SHIPPED_ORDER" 
   (	"ORDER_ID" NUMBER(*,0), 
	"FIRST_NAME" VARCHAR2(50 BYTE), 
	"LAST_NAME" VARCHAR2(50 BYTE), 
	"EMAIL" VARCHAR2(50 BYTE), 
	"COST" VARCHAR2(50 BYTE), 
	"ITEM_ID" VARCHAR2(80 BYTE), 
	"ITEM_NAME" VARCHAR2(15 BYTE), 
	"SHIP_DATE" VARCHAR2(45 BYTE),
	"STATUS" VARCHAR2(15 BYTE), 
	CONSTRAINT "PK_ORDER_ID" PRIMARY KEY ("ORDER_ID")
   ); 

 CREATE TABLE "SHIPPED_ORDER_OUTPUT" 
   (	"ORDER_ID" NUMBER(*,0), 
	"FIRST_NAME" VARCHAR2(50 BYTE), 
	"LAST_NAME" VARCHAR2(50 BYTE), 
	"EMAIL" VARCHAR2(50 BYTE), 
	"COST" VARCHAR2(50 BYTE), 
	"ITEM_ID" VARCHAR2(80 BYTE), 
	"ITEM_NAME" VARCHAR2(15 BYTE), 
	"SHIP_DATE" VARCHAR2(45 BYTE),
	"STATUS" VARCHAR2(15 BYTE), 
	CONSTRAINT "PK_OUTPUT_ORDER_ID" PRIMARY KEY ("ORDER_ID")
   ); 

Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (1,'Doro','Odda','dodda0@jugem.jp','50.53','63aa1159-8ec4-4154-9b4e-620e67f4bdc5','Stretch Pants','2019-02-22');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (2,'Cyb','Dedham','cdedham1@elpais.com','15.01','dc64ab3d-154c-4059-af52-494549a044e7','Ski Coat','2019-07-25');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (3,'Lorette','Castletine','lcastletine2@archive.org','49.68','84e6ede5-3337-45cc-b21a-5c4c89aae737','Tank Top','2019-07-02');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (4,'Giff','Loncaster','gloncaster3@constantcontact.com','38.04','0b5385e2-34c1-4cb5-997f-0c6797d03f90','Fleece Jacket','2019-08-22');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (5,'Freddi','Torrecilla','ftorrecilla4@about.com','54.14','4d7f4a97-1f9a-4373-ac2e-a056c1eeef2b','Fleece Jacket','2019-09-02');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (6,'Sharity','Jandak','sjandak5@php.net','3.33','6c36f8df-ae4e-4965-a740-ed13133d4f02','Tank Top','2019-02-04');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (7,'Pattie','Aguirrezabal','paguirrezabal6@flickr.com','59.40','b177d610-479f-4372-852d-db5afcadde5d','Leggings','2019-04-16');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (8,'Starla','Gallymore','sgallymore7@blogger.com','57.35','a56d9480-116d-402e-ab62-2965f27f1214','Sweater','2019-07-10');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (9,'Hyacinthe','Saket','hsaket8@biblegateway.com','61.81','87457d7e-9fc0-463b-b186-da077e630ebc','Jeans','2019-04-15');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (10,'Caro','Brade','cbrade9@xing.com','35.04','53133ad1-dbdf-4579-a52b-25959d86e0ff','Vest','2019-04-28');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (11,'Joseito','Oxlee','joxleea@examiner.com','43.06','4399e0e3-a25b-49ce-ae8d-81fa8bbf8ac5','Polo Shirt','2019-12-10');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (12,'Killian','Ivermee','kivermeeb@toplist.cz','30.07','8d6f6f0b-fcd3-4e1c-bbe1-869d4a319989','Jeans','2019-06-09');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (13,'Prissie','Kobpa','pkobpac@dailymail.co.uk','57.42','b17532b0-6160-4fdc-8380-f1b750afefc1','Fleece Jacket','2019-12-25');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (14,'Tabby','Sargeaunt','tsargeauntd@xing.com','13.05','0bc102be-653e-41a7-822d-583c112c9de1','Ski Coat','2019-10-03');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (15,'Neddy','Dorking','ndorkinge@alexa.com','13.12','a90fabc8-aed8-499b-9e42-b9f554c1ca82','Fleece Jacket','2019-01-14');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (16,'Erek','Reuble','ereublef@wikipedia.org','63.31','91270aa5-1a25-4f30-8d03-b71d0ea5f357','Tank Top','2019-03-15');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (17,'Hilliard','Keyworth','hkeyworthg@jigsy.com','56.82','1119759d-c243-475d-a121-ef15ac4a0844','Vest','2019-02-24');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (18,'Cassey','Peasegood','cpeasegoodh@mashable.com','49.05','b36128b0-5b32-4d6a-b63a-642d32215474','Sneakers','2019-06-23');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (19,'Granger','Ludwikiewicz','gludwikiewiczi@ftc.gov','65.26','d0d74724-5e9a-4ba3-813a-f7a24d25f0f3','Stretch Pants','2019-01-12');
Insert into SHIPPED_ORDER (ORDER_ID,FIRST_NAME,LAST_NAME,EMAIL,COST,ITEM_ID,ITEM_NAME,SHIP_DATE) values (20,'Adriaens','Thomsson','athomssonj@imageshack.us','85.86','077d4892-024c-4df3-8e3b-a6a894f95896','Stretch Pants','2019-12-14');

commit;
