# vCard

# `GET` /search
>Returns html code that allows to generate VCard
 
| parameter | description |
|--|--|
| call | data to search on "www.pkt.pl" |

**example** `200` 

`GET` 
 /search?call=szkoła
 
    Zespół Szkół Centrum Kształcenia Rolniczego w Widz...

 # `GET` /generate
>Returns vcf file from passed data
 
| parameter | description |
|--|--|
| fn | name |
| tel | telephone |
| url | website |
| email | email |

**example** `200` 

`GET` 
 /generate?fn=Zak%C5%82ad%20Doskonalenia%20Zawodowego&tel=EXAMPLE_TELEPHONE&url=EXAMPLE_URL&email=EXAMPLE_EMAIL
 

 
