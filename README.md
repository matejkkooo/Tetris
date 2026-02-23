🧩 Tetris – Java (Swing)

📌 Project Overview
Tento projekt predstavuje jednoduchú implementáciu klasickej hry Tetris vytvorenú v jazyku Java pomocou knižnice Swing.
Projekt demonštruje objektovo orientovaný návrh, prácu s grafickým rozhraním, spracovanie vstupov z klávesnice a implementáciu hernej logiky ako sú kolízie, odstraňovanie riadkov, skórovanie a dynamické zrýchľovanie hry.

🎯 Purpose of the Project
Cieľom projektu je:
- vytvoriť funkčnú a plynulú verziu hry Tetris,
- precvičiť si prácu s Java Swing,
- implementovať herný cyklus (game loop),
- oddeliť logiku hry do samostatných tried,
- vytvoriť prehľadnú štruktúru projektu vhodnú na ďalšie rozširovanie.

🛠 Technologies Used
- Java
- Java Swing
- OOP (Object-Oriented Programming)

🚀 How to Run
1. Otvorte projekt v IntelliJ IDEA alebo inom Java IDE.
2. Spustite triedu Main.java.
3. Potvrďte dialógové okno na spustenie hry.

🎮 Controls
← – pohyb bloku doľava
→ – pohyb bloku doprava
↑ – otočenie bloku
SPACE – okamžité zhodenie bloku

🧠 Game Rules
- Hráč ovláda padajúce bloky (tetromino tvary) a snaží sa ich ukladať tak, aby vytvoril úplne zaplnené vodorovné riadky.
- Po zaplnení riadok zmizne a hráč získa body.
- Hra končí, keď sa bloky nahromadia až po vrch hracej plochy a nový tvar už nie je možné umiestniť.

🏗 Project Structure
Projekt je rozdelený do viacerých tried:

- Main – vstupný bod aplikácie
- GameWindow – hlavné herné okno
- HraTetris – herná logika a vykresľovanie
- HernaPlocha – správa hernej mriežky
- TvarBloku – definícia a rotácie blokov
- SpravcaSkore – správa bodovania
- OvladanieHry – spracovanie vstupov

Takéto rozdelenie umožňuje jednoduché rozširovanie projektu (napr. animácie, nové režimy hry, ukladanie skóre).

📅 Version
Version 1.0 – October 2025

👨‍💻 Author
Matej Holeš
