# 📱 CurrencyConverter

**CurrencyConverter** — Android-приложение для обмена валют с использованием **Jetpack Compose**, **Hilt**, **Room** и **Clean Architecture**.  

---

## 🧩 Архитектура

Проект построен по **Clean Architecture** с разделением на уровни:

- `data` — источники данных: Room, Retrofit
- `domain` — бизнес-логика: use cases, модели
- `presentation` — UI, ViewModel, события

Используется **MVVM**. Взаимодействие UI и логики — через `StateFlow` и `ViewModel`.

Основная валюта баланса, видимость баланса, выбранна карточка валюты сохранятюся в `SharedPreferences`.

---

## 💡 Особенности

### Главный экран с валютами
- **Режим списка**: отображение всех валют относительно выбранной. По клику на другую валюту она становится выбранной и премещается наверх.

<p align="center">
  <img src="https://github.com/user-attachments/assets/0d9296e1-adb6-4a45-8582-b952f9575d9c" alt="mainScreen1" width="40%" style="display: inline-block;" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/c0ece949-3b68-4a7a-b0db-1dec403a2e88" alt="mainScreen2" width="40%" style="display: inline-block;" />
</p>

- **Режим ввода**: пользователь вводит сумму валюты, отображаются только те, у которых хватает средств на балансе.

<p align="center">
  <img src="https://github.com/user-attachments/assets/fb738163-d7b1-4224-93e5-fc30363375a6" alt="mainScreen3" width="40%" style="display: inline-block;" />
</p>

### TopAppBar
- **Динамичный TopAppBar**: сворачивается и разворачивается при свайпе списка валют

<p align="center">
  <img src="https://github.com/user-attachments/assets/2cd46201-0cb3-40a2-85f3-d25d09d100f9" alt="topAppBarScreen1" width="40%" style="display: inline-block;" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/4322fc52-dede-4c81-b7e2-938d6482bfc6" alt="topAppBarScreen2" width="40%" style="display: inline-block;" />
</p>

- **Скрытие и отображение общего баланса**: по нажатию можно скрывать и показывать баланс

<p align="center">
  <img src="https://github.com/user-attachments/assets/2cd46201-0cb3-40a2-85f3-d25d09d100f9" alt="balanceScreen1" width="40%" style="display: inline-block;" />
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/9f4ea45b-a714-4991-8af9-9c8b8f7949ac" alt="balanceScreen2" width="40%" style="display: inline-block;" />
</p>

- **Изменение валюты общего баланса**: по нажатию на баланс можно изменить валюту

<p align="center">
  <img src="https://github.com/user-attachments/assets/6dc17c60-a4ae-4792-97d4-fc5c7e9c3394" alt="image" width="40%" style="display: inline-block;" />
</p>

### Экран "Обмен"

**Показывает:**
- Сумму покупки
- Сумму оплаты
- Курс

**По нажатию на кнопку обмена:**
- Обновляются балансы в `accounts`
- Записывается `TransactionDbo`
- Возврат на экран "Валюты"

<p align="center">
  <img src="https://github.com/user-attachments/assets/42af8fc5-fb3a-4cf0-9fdf-5752e5ecd04f" alt="image" width="40%" style="display: inline-block;" />
</p>

### Экран "Профиль"

- **Отображение общего баланса**
  - Изменение валюты баланса
  - Скрытие баланса
- **Отображение акивов на балансе**

<p align="center">
  <img src="https://github.com/user-attachments/assets/b3f4be28-490c-4d59-a71f-f18eee600210" alt="image" width="40%" style="display: inline-block;" />
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <img src="https://github.com/user-attachments/assets/01d727b1-046b-4f96-b0b1-13c30af664e6" alt="image" width="40%" style="display: inline-block;" />
</p>

### Экран "Транзакции"

- Список всех транзакций с указанием пар валют, сумм и времени.

<p align="center">
  <img src="https://github.com/user-attachments/assets/bd547e6f-aaf1-4b5b-a9c1-d0b7036c9fad" alt="image" width="40%" style="display: inline-block;" />
</p>

---

## 🎨 UI/UX решения

- Перемещение выбранной валюты вверх списка с `animateItemPlacement`
- Флаги валют с `com.github.murgupluoglu:flagkit-android:1.0.5`
- Поддержка Edge-to-edge

---

## Тестирование

Проект покрыт юнит-тестами ключевой бизнес-логики и репозиториев.  
Покрываются следующие use case'ы:

- `EnsureInitialCurrencyExistsUseCaseTest`
- `FilterPurchasableCurrenciesUseCaseTest`
- `GetBalanceUseCaseTest`
- `GetRatesFlowUseCaseTest`
- `MakeExchangeUseCaseTest`
- `ObserveAccountsUseCaseTest`

А также реализация репозиториев:

- `AccountRepositoryImplTest`
- `RatesRepositoryImplTest`
- `TransactionRepositoryImplTest`

---