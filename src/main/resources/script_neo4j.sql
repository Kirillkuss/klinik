MATCH (document:Document)
  RETURN document

MATCH (document:Document)
  SET document.typeDocument = 'typeDocument'

MATCH (document:Document)
  SET document.seria = 'seria'

MATCH (document:Document)
  SET document.numar = 'numar'
   
MATCH (document:Document)
  SET document.snils = 'snils'

MATCH (document:Document)
  SET document.polis = 'polis' 
    	   

MATCH (person:Person)
  RETURN person

MATCH (person:Person)
  SET person.surname = 'surname'   
   
MATCH (person:Person)
  SET person.name = 'name'  
   
MATCH (person:Person)
  SET person.fullName = 'fullName'   
   
MATCH (person:Person)
  SET person.gender = 'MAN'  

MATCH (person:Person)
  SET person.phone = 'polis'   
   
MATCH (person:Person)
  SET person.address = 'address'  