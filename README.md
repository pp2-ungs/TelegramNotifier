# TelegramNotifier

A small Java module that sends **Telegram messages** when a task is assigned in the **TASkOcupado** college project.

This repository contains the "Telegram notifier" part of the system. It is designed to be loaded/discovered by the main TASkOcupado application and used as an **Observer** that reacts to task-assignment events.

© 2024 [Ebertz](https://github.com/xebertz), [López](https://github.com/Gonza-JL), [Rondelli](https://github.com/rondelli)

## What it does

When the main application notifies observers about a new task assignment, this module:

1. Receives an `event` object (a `Map`) containing:
   - "Task": the task description
   - "Name": the person's name
2. Looks up that person's Telegram chat ID from a JSON mapping file (`Telegram.json`).
3. Builds a short notification message.
4. Sends the message to the user via a Telegram Bot using the `telegrambots` Java library.
5. Sends asynchronously (in a new thread) so the main application flow is not blocked.

## How it works (high-level flow)

**Event → Observer.update(event) → lookup chat id → send Telegram message**

- `TelegramNotifier` implements `observer.Observer`
- `TelegramFinder` loads the mapping `person name -> telegram chat id` from JSON
- `Telegram` wraps a `TelegramLongPollingBot` and exposes `sendMessageToUser(chatId, text)`

## Project structure

- `src/telegram/TelegramNotifier.java`  
  The observer implementation. This is the class that the main system is expected to instantiate/register so it can receive task events.

- `src/telegram/TelegramFinder.java`  
  Loads Telegram IDs from `Settings.RESOURCES + "Telegram.json"` using Gson and returns a `Map<String, Number>`.

- `src/telegram/Telegram.java`  
  Telegram Bot client wrapper extending `TelegramLongPollingBot`. Provides:
  - `getBotUsername()`
  - `getBotToken()`
  - `sendMessageToUser(long chatId, String text)`

## Requirements / Dependencies

This module relies on (at least):

- Java (NetBeans-style project layout + Ant `build.xml`)
- `org.telegram.telegrambots` Java library (Telegram Bots API for Java)
- Gson (`com.google.gson.Gson`)
- A `core.Settings` class (provided by the larger TASkOcupado codebase) that defines:
  - `Settings.RESOURCES` (base path where `Telegram.json` is located)
- An `observer.Observer` interface (also provided by the larger codebase)

This repository is not fully standalone: it expects to be used alongside the rest of TASkOcupado.

## Configuration

### Telegram user mapping file (`Telegram.json`)

`TelegramFinder` expects a JSON file located at:

- `Settings.RESOURCES + "Telegram.json"`

Example format:

```json
{
  "Alice": 123456789,
  "Bob": 987654321
}
```

Where the values are Telegram **chat IDs**.

### Telegram bot credentials

The bot username/token are currently defined inside `Telegram.java`.

**Important:** In a real deployment you should move credentials out of source code (e.g., environment variables, config file, secrets manager) to avoid leaking tokens.

## Usage (integration)

The main TASkOcupado application should:

1. Create an instance of `TelegramNotifier`
2. Register it wherever task-assignment observers are managed
3. When a task is assigned, publish an event map like:

```java
Map<String, String> event = new HashMap<>();
event.put("Task", "Fix login bug");
event.put("Name", "Alice");
notifier.update(event);
```

## Notes / Limitations

- Message sending is done on a new thread per event, so failures in the Telegram call won't crash the main application thread.
- `onUpdateReceived` is empty — this bot only sends messages and does not process incoming Telegram updates.
