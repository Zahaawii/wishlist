# **Datamatikeruddannelsen – 2. semester, Forår 2025**
## **Miniprojekt – Webbaseret Databaseapplikation**

### 📌 **Formål**
Dette projekt har til formål at styrke vores færdigheder inden for kravspecifikation, design og implementering af en webbaseret databaseapplikation.

### 📦 **Leverancer**
#### ✅ **1. Webbaseret databaseapplikation**
En fuldt fungerende webapplikation med en tilhørende database.

#### ✅ **2. GitHub Repository**
Projektets kode opbevares i dette repository og indeholder:
- **GitHub Actions Workflow** med:
    - **Continuous Integration (CI)**:
        - Automatiseret build af feature-branches
        - Kørsel af relevante tests ved pull requests til `main`
    - **Testtyper**:
        - Unit tests
        - Integrationstests
        - Statisk kodeanalyse vha. Qodana
            - Dokumentation vedlægges i `docs`-mappen (fx skærmbillede af Qodana-inspektion)
    - **Continuous Deployment (CD)**:
        - Deployment til Azure Cloud (PaaS)

#### ✅ **3. Dokumentation**
Dette repository indeholder følgende dokumenter:
- 📌 **ER-model** over databasedesignet (`/docs/er-model.png`)
- 📌 **Klassediagram** (`/docs/class-diagram.png`)
    - Skal være manuelt tegnet på et passende detaljeringsniveau
- 📌 **Risikoanalyse** (`/docs/risikoanalyse.md`)
    - Fokus på mitigerende handlinger og prioritering af risici
- 📌 **Interessentanalyse** (`/docs/interessentanalyse.md`)
    - Identifikation og håndtering af interessenter
- 📌 **Bæredygtighed og dataetik** (`/docs/baeredygtighed-dataetik.md`)
    - Beskrivelse af bæredygtighed og dataetik i løsningen
    - Inkluderer overvejelser baseret på de 5 P’er fra undervisningen

#### ✅ **4. Scrum Board**
- 📌 **GitHub Projects** bruges til at holde styr på User Stories og opgaver
- 📌 **Planning Poker** er anvendt til estimering af User Stories

#### ✅ **5. SQL Scripts**
- SQL-scripts til databaseoprettelse og testdata findes i `/resources/`
    - `setup_database.sql`: Opretter databasen og tabeller
    - `insert_test_data.sql`: Indsætter testdata

---

## 🚀 **Problemstilling**
Vi skal udvikle en **digital ønskeseddel**, inspireret af tjenester som [Ønskeskyen](https://www.ønskeskyen.dk/) og [Wishlist.dk](https://www.wishlist.dk/).

### **Funktionelle krav**
1️⃣ **Minimumskrav (skal implementeres):**
- Brugeren kan oprette en digital ønskeseddel

2️⃣ **Ønskelige funktioner (ikke obligatoriske):**
- Brugeren kan dele sin ønskeseddel med andre

3️⃣ **Avancerede funktioner (kun hvis der er tid):**
- Andre brugere kan reservere ønsker på ønskesedlen

🛠 **Produktet skal være et** *Minimal Viable Product (MVP)* **med høj kodekvalitet og minimal teknisk gæld.**

---

## 📅 **Aflevering**
- **GitHub repository link** + GitHub-navne på alle deltagere
- Det er ikke et krav, at applikationen er tilgængelig på Azure ved aflevering, men den skal kunne tilgås ved gennemgang i undervisningen

📌 **Bemærk:**  
Dette projekt er en **obligatorisk opgave**, der skal afleveres for at kunne indstilles til eksamen.

---

## 📚 **Vejledning**
- Vejledning foregår i henhold til Fronter-planerne.  
