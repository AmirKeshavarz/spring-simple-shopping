-- CATEGORIES
INSERT INTO CATEGORY(id,name,description,parent_id) values ('1','accessories','some descriptions',null);
INSERT INTO CATEGORY(id,name,description,parent_id) values ('2','laptop','some descriptions',null);
INSERT INTO CATEGORY(id,name,description,parent_id) values ('3','gaming-laptop','some descriptions','2');

-- PRODUCTS
INSERT INTO PRODUCT(id,name,description,price,category_id) values ('1','ipad','some descriptions',1000,'1');
INSERT INTO PRODUCT(id,name,description,price,category_id) values ('2','iphone','some descriptions',2000,'2');

-- SECURITY
INSERT INTO AUTHORITY(id,role) values ('1','admin');
INSERT INTO AUTHORITY(id,role) values ('2','user');

-- in both of below inserts the password a 'password' encoded by bcrypt
INSERT INTO USER(ID,ACCOUNT_NON_EXPIRED,ACCOUNT_NON_LOCKED,CREDENTIALS_NON_EXPIRED,ENABLED,PASSWORD,USERNAME)
    values ('1',true,true,true,true,'$2a$10$fFzVOy15.IRghGxhgez46eGSLwLdFr0PEpn61ZLKSEueBXCXdAY3G','admin');
INSERT INTO USER(ID,ACCOUNT_NON_EXPIRED,ACCOUNT_NON_LOCKED,CREDENTIALS_NON_EXPIRED,ENABLED,PASSWORD,USERNAME)
    values ('2',true,true,true,true,'$2a$10$Y0zKe7YNNs7sFvlGG3TC1.jtniwolaReDy7ZtrPlCiw4UOwwnClvO','user');

INSERT INTO USER_AUTHORITY(user_id,authority_id) values ('1','1');
INSERT INTO USER_AUTHORITY(user_id,authority_id) values ('2','2');