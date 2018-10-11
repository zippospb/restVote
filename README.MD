
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/217824fe0337460f8f055f65ae4465de)](https://app.codacy.com/app/zippospb/restVote?utm_source=github.com&utm_medium=referral&utm_content=zippospb/restVote&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://www.travis-ci.org/zippospb/restVote.svg?branch=master)](https://www.travis-ci.org/zippospb/restVote.svg)
[![codecov](https://codecov.io/gh/zippospb/restVote/branch/master/graph/badge.svg)](https://codecov.io/gh/zippospb/restVote/branch/master/graph/badge.svg)

#Restaurant Voting system

##TASK:

Build a voting system for deciding where to have lunch.

- 2 types of users: admin and regular users
- Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
- Menu changes each day (admins do the updates)
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed
- Each restaurant provides new menu each day.

##DESCRIPTION
- There are 4 entities in project:User, Restaurant, Dish and Vote.

- Users have following fields: id; name; email(must be unique); password; Role (ROLE_ADMIN and ROLE_USER).

- Restaurant : id; name; dishes; voteCount (number of users who voted today for the restaurant).

- Dish: id; name; price; date(date of creating);

- Voting: id; user(who vote); restaurant(for what exactly voted); date (of voting); time (of voting).

- For POST and PUT forms, data receiving in JSON. Mandatory fields to transmit for user: name, email, password. Mandatory fields to transmit for restaurant: name. Mandatory to transmit fields for dishes: name, price.

- There is basic authentication, for admin it is: admin@gamil.com password:Admin, for first user it is: login:user@mail.ru password:qwert, for second user it is: login:user2@mail.ru password:asdfg.

**Admins can create, delete, update users,restaurants and dish.**
  
**Authorized admins HTTP requests:**

GET:    `/rest/admin/users` Gets all users

GET:    `/rest/admin/users/{id}` Gets user by id

GET:    `/rest/admin/users/by?email={email}` Gets user by email

POST:   `/rest/admin/users` Create new User

DELETE: `/rest/admin/users/id` Delete user by id

PUT:    `/rest/admin/users/id` Update user by id

GET:    `/rest/admin/restaurants` Gets all restaurants with dishes

GET:    `/rest/admin/restaurants/{restId}` Gets restaurant with dishes by id

POST:   `/rest/admin/restaurants` Create new restaurant

DELETE: `/rest/admin/restaurants/{restId}` Delete restaurant by id

PUT:    `/rest/admin/restaurants/{restId}` Update restaurant by id

GET:    `/rest/admin/restaurants/{restId}/dishes` Gets all dishes by restaurant id

GET:    `/rest/admin/restaurants/{restId}/dishes/{dishId}` Gets dish by restaurant id and dish id

GET:    `/rest/admin/restaurants/{restId}/dishes/by` Gets dish by restaurant id and by date

POST:   `/rest/admin/restaurants/{restId}/dishes` Create new dish for restaurant

DELETE: `/rest/admin/restaurants/{restId}/dishes/{dishId}` Delete dish by restaurant id and dish id

PUT:    `/rest/admin/restaurants/{restId}/dishes/{dishId}` Update dish by restaurant id and dish id

**Users can delete and update self profile, watch restaurants and dish, and can vote for certain restaurant.**

**Authorized Users HTTP requests:**

GET:    `/rest/profile` Get login user

DELETE: `/rest/profile` Delete login user

PUT:    `/rest/profile` Update login user

GET:    `/rest/profile/restaurants` Get all restaurants with current date dishes

GET:    `/rest/profile/restaurants/{restId}` Get restaurant with current date dishes by id

GET:    `/rest/profile/restaurant/{restId}`/votes Vote by restaurant with id

**Unauthorized users HTTP requests:**

POST:   `/rest/profile/register` Register new user

###CURLS
(application deployed in application context restvote).

**Curl samples for Authorized admins:**

get All Users  
`curl -i http://localhost:8080/restvote/rest/admin/users --user admin@gmail.com:lkjh33498yhnbyfgi563`

get user 100000  
`curl -i http://localhost:8080/restvote/rest/admin/users/100000 --user admin@gmail.com:lkjh33498yhnbyfgi563`

get user with email = "user@mail.ru"
`curl -i http://localhost:8080/restvote/rest/admin/users/by?email=user@mail.ru --user admin@gmail.com:lkjh33498yhnbyfgi563`

create user  
`curl -i -X POST -d '{"name":"newName","email":"newemail@ya.ru","roles":["ROLE_USER"],"password":"newPassword"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/restvote/rest/admin/users --user admin@gmail.com:lkjh33498yhnbyfgi563`

delete user with id=100000  
`curl -i -X DELETE http://localhost:8080/restvote/rest/admin/users/100000 --user admin@gmail.com:lkjh33498yhnbyfgi563`

update user with id=100000  
`curl -i -X PUT -d '{"id":100000,"name":"User1","email":"updated@gmail.com","roles":["ROLE_USER"],"password":"qwert"}' -H 'Content-Type: application/json' http://localhost:8080/restvote/rest/admin/users/100000 --user admin@gmail.com:lkjh33498yhnbyfgi563`

get all restaurants  
`curl -i http://localhost:8080/restvote/rest/admin/restaurants --user admin@gmail.com:lkjh33498yhnbyfgi563`

get restaurant with id=100003  
`curl -i http://localhost:8080/restvote/rest/admin/restaurants/100003 --user admin@gmail.com:lkjh33498yhnbyfgi563`

create restaurant  
`curl -i -X POST -d '{"name":"New Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/restvote/rest/admin/restaurants --user admin@gmail.com:lkjh33498yhnbyfgi563`

delete restaurant  
`curl -i -X DELETE http://localhost:8080/restvote/rest/admin/restaurants/100003 --user admin@gmail.com:lkjh33498yhnbyfgi563`

update restaurant  
`curl -i -X PUT -d '{"name":"Updated Restaurant"}' -H 'Content-Type: application/json' http://localhost:8080/restvote/rest/admin/restaurants/100003 --user admin@gmail.com:lkjh33498yhnbyfgi563`

get all dished by restaurant id  
`curl -i http://localhost:8080/restvote/rest/admin/restaurants/100003/dishes --user admin@gmail.com:lkjh33498yhnbyfgi563`

get all dished by restaurant id and dish id  
`curl -i http://localhost:8080/restvote/rest/admin/restaurants/100003/dishes/100006 --user admin@gmail.com:lkjh33498yhnbyfgi563`

get all dished by restaurant id and date  
`curl -i http://localhost:8080/restvote/rest/admin/restaurants/100003/dishes?date=2018-10-01 --user admin@gmail.com:lkjh33498yhnbyfgi563`

create new dish for restaurant with id=100003  
`curl -i -X POST -d '{"name":"new dish","price":500}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/restvote/rest/admin/restaurants/100003/dishes/ --user admin@gmail.com:lkjh33498yhnbyfgi563`

delete dish by restaurant id and dish id  
`curl -i -X DELETE http://localhost:8080/restvote/rest/admin/restaurants/100003/dishes/100006 --user admin@gmail.com:lkjh33498yhnbyfgi563`

update dish by restaurant id and dish id  
`curl -i -X PUT -d '{"name":"updated dish","price":50,"date":"2018-10-01"}' -H 'Content-Type: application/json' http://localhost:8080/restvote/rest/admin/restaurants/100003/dishes/100006 --user admin@gmail.com:lkjh33498yhnbyfgi563`

**Curl samples for Authorized users**  
get the current user  
`curl -i http://localhost:8080/restvote/rest/profile --user user@mail.ru:qwert`

delete the current user  
`curl -i -X DELETE http://localhost:8080/restvote/rest/profile --user user@mail.ru:qwert`

update the current user  
`curl -i -X PUT -d '{"name":"updated user","email":"updated@gmail.com","password":"zxcvb"}' -H 'Content-Type: application/json' http://localhost:8080/restvote/rest/profile --user user@mail.ru:qwert`

get all restaurants with current date dishes  
`curl -i http://localhost:8080/restvote/rest/profile/restaurants --user user@mail.ru:qwert`

get restaurant with current date dishes by id  
`curl -i http://localhost:8080/restvote/rest/profile/restaurants/100003 --user user@mail.ru:qwert`

Vote by restaurant with id  
`curl -i http://localhost:8080/restvote/rest/profile/restaurant/100003/votes  --user user@mail.ru:qwert`