# java-filmorate

![DB flow chart](ER_sheme.JPG)

Сложность возникла только с таблицей ДРУЗЕЙ, а именно добавление пользователя в друзья. и пришел к выводу, что:

1. пользователь 1 присылает предложение в друзья пользователю 2. создается запись типа

| user_id | friend_user_id | confirmed |
|---------|----------------|-----------|
| 1       | 2              | 0         |

2. когда пользователь 2 принимает дружбу создаем аналогичную запись, но наоборот и меняем поле confirmed на 0

| user_id | friend_user_id | confirmed |
|---------|----------------|-----------|
| 1       | 2              | 1         |
| 2       | 1              | 1         |

хоть размер таблицы увеличиться в двое, тем самым мы уменьшаем скорость работы SELECT. сравним:

``` sql
select * 
  from user u
where u.user_id = in (select f.friend_user_id
                        from friendship f
                      where f.user_id = 1
                     union
                     select f.user_id
                       from friendship f
                     where f.friend_user_id = 1)
``` 

против

``` sql
select * 
  from user u
  inner join friendship f on u.user_id = f.user_id
where u.user_id = 1
``` 

============== SELECT запросы ==============

1) Get friends of user 1

``` sql
SELECT *
FROM   USER u
inner join friendship f
ON u.user_id = f.user_id
WHERE  u.user_id = 1
```

2) Get common friends of users 1 and 2

``` sql
select *
  from user u
 where u.id in (select f.friend_user_id
                  from friendship f
                 where f.user_id = 1
                    or f.user_id = 2
                 group by f.friend_user_id
                having count(*) > 1)

```

3) Get all users

``` sql
SELECT * FROM user;
```

4) Get user with id 1

``` sql
SELECT * FROM user WHERE user_id = 1;
```

5) Get film with id 1

``` sql
SELECT * FROM film WHERE film_id = 1;
```

6) Find most popular films (show all films whether or not liked, ordered by amount of likes)

``` sql
SELECT f.*
FROM film f
LEFT JOIN film_likes fl on f.id = fl.film_id
GROUP BY f.id, f.name
order by count(*) desc 
limit 
case 
  when p_limit is not null
     then p_limit
  else 10 end
```
