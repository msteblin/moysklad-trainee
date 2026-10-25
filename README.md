# МойСклад

## Сборка
```shell
$ docker compose build
```

## Запуск
> [!IMPORTANT]
> Перед запуском необходимо создать файл `.env` в корне проекта:
> ```dotenv
> POSTGRES_DB=database        # название бд
> POSTGRES_USER=username      # имя пользователя
> POSTGRES_PASSWORD=password  # пароль
> SPRING_PROFILES_ACTIVE=prod # профиль Spring (dev или prod, по умолчанию prod)
> ```
```shell
$ docker compose up
```
