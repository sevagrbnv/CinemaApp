# Тестовое задание

## Используемые библиотеки и технологии
* Retrofit
* Gson
* Picasso
* Coroutines + Flow
* Paging 3
* Dagger hilt

## Инструкция по запуску
1. Клонируйте репозиторий, например через https://github.com/sevagrbnv/CinemaApp.git
2. В директории ```/src/main/java/ru/sevagrbnv/cinemaapp/data/remote``` создайте класс Secrets следующего содержания:
```
class Secrets {
    companion object {
        const val KINOPOISK_API_KEY = "your_api_key" // Вставьте ваш ключ
    }
}
```
3. Соберите проект
4. Возьмите меня на работу

!Важно если у вас возникли проблемы на одном из этапов сборки, переходите сразу к пункту 4 )

## Проблемы и их решение
1. * Проблема: Не было понятно, как реализовать фильтры
   * Решение: Реализовал через BottomSheet со строковыми полями. В подсказке указаны примеры запросов. В случае некоректного фильтра, его обработкой занимается сервер и нужно только отображать снекбар с ошибкой.
2. * Проблема: Можно ли использовать фильтры и поиск одновременно?
   * Решение: Нет, сделано это для упрощения логики работы приложения. Вам этот код еще проверять.