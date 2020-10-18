# Система оценки работодателей
https://sergey-kubatko.atlassian.net/wiki/spaces/E/pages/4816923

## Основное приложение

### Создание образа Docker

    ./gradlew bootBuildImage -x test

### Запуск системы

    docker-compose -d up
    
### Останов системы

    docker-compose down
