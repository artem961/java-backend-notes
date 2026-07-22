# Конспект подготовки к интервью

Шаблон конспекта на [MkDocs Material](https://squidfunk.github.io/mkdocs-material/)
с автодеплоем на GitHub Pages.

Содержания пока нет — только стартовая страница и страница `Содержание`,
на которой формируется структура.

## Локальный запуск

```bash
pip install -r requirements.txt
mkdocs serve
```

http://127.0.0.1:8000 — обновляется при сохранении файлов.

## Деплой на GitHub Pages

1. Создать репозиторий на GitHub и запушить:

```bash
git init
git add .
git commit -m "Развернуть шаблон конспекта"
git branch -M main
git remote add origin https://github.com/USERNAME/REPO.git
git push -u origin main
```

2. В репозитории: **Settings → Pages → Build and deployment → Source: GitHub Actions**
3. После пуша в `main` сайт собирается автоматически:
   `https://USERNAME.github.io/REPO/`

## Добавление новой страницы

1. Создать `.md` в `docs/`
2. Добавить в `nav` в `mkdocs.yml`
3. Добавить в `docs/contents.md`
4. Проверить: `mkdocs build --strict`

## Работа с телефона

- **Читать:** сайт GitHub Pages, тема адаптивная, есть поиск
- **Быстро править:** GitHub в браузере или приложение → карандаш → commit
- **Писать нормально:** Obsidian (открыть `docs` как хранилище) + плагин Git

## Claude Code

В корне лежит `CLAUDE.md` с контекстом проекта и правилами.
Запуск из корня репозитория:

```bash
claude
```
