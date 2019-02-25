CREATE TABLE adminuser(
id INT AUTO_INCREMENT PRIMARY KEY,
loginname CHARACTER(100),
password CHARACTER(100),
validflag BOOLEAN DEFAULT TRUE
);
INSERT INTO adminuser(id,loginname, password,validflag) VALUES (1,'admin','admin',true);

CREATE TABLE tbservice(
  serviceid INT AUTO_INCREMENT PRIMARY KEY,
  title CHARACTER(100),
  comments CHARACTER(200),
  imgurl  CHARACTER(200),
  dmltime DATE,
  details TEXT,
  validflag BOOLEAN DEFAULT TRUE
);

CREATE TABLE technology(
  technologyid INT AUTO_INCREMENT PRIMARY KEY,
  title CHARACTER(100),
  comments CHARACTER(200),
  imgurl  CHARACTER(200),
  dmltime DATE,
  details TEXT,
  validflag BOOLEAN DEFAULT TRUE
);