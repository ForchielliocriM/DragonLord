# 🐉 DragonLord

RPG a turni ispirato a Dragon Quest, sviluppato in Java come progetto
per il corso di Metodologie di Programmazione e Modellazione e Gestione
della Conoscenza (AA 2025/26 — Università di Camerino).
Il gioco include un menù principale, una mappa esplorabile e un sistema
di combattimento a turni con caricamento dati da XML e persistenza tramite database.

---

## 🚀 Come eseguire il progetto

### Prerequisiti
- Java 25 (LTS)
- Gradle (incluso nel progetto tramite Gradle Wrapper)
- Connessione internet al primo avvio (per scaricare le dipendenze)

### Istruzioni

```bash
git clone https://github.com/ForchielliocriM/dragonlord.git
cd dragonlord
```

### Build del progetto
**Linux/macOS**
```bash
./gradlew build
```
**Windows**
```bash
gradlew build
```

### Esecuzione
**Linux/macOS**
```bash
./gradlew run
```
**Windows**
```bash
gradlew run
```

---

## 🎮 Come si gioca

- **Menu principale:** scegli "Nuova Partita" per iniziare o "Carica Partita" 
per caricare l'ultima partita salvata
- **Movimento:** tasti `WASD`
- **Combattimento:** a turni e si attiva casualmente camminando su erba o foresta
- **Salvataggio:** pulsante `[Salva]` durante la partita

---

## 🤖 Uso di strumenti di AI

È stato utilizzato **Claude** come supporto durante certe fasi 
dello sviluppo del progetto.

Nello specifico è stato utilizzato per:

* Revisione della struttura di alcuni package, classi e interfacce

* Supporto nella comprensione e implementazione di:
    * persistence layer (Hibernate, HeroRepository, HeroMapper...)
    * interfaccia grafica JavaFX (FXML, CSS, Controller, SceneManager)

* Chiarire errori di compilazione

Il codice prodotto con il supporto dell'AI è stato analizzato,
compreso e adattato alle esigenze del progetto durante lo sviluppo.
L'uso dell'AI ha avuto uno scopo didattico e di supporto,
per comprendere meglio alcuni concetti teorici.

---

📌 Per una descrizione più dettagliata dell'uso dell'AI, consultare
la **[Wiki](https://github.com/ForchielliocriM/DragonLord/wiki) del repository**.