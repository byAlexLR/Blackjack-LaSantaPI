# 🃏 Blackjack - LaSantaPI

## 📌 Descripción
Este es un juego de **Blackjack** desarrollado en **Java** con una **interfaz gráfica**. El objetivo es implementar una versión interactiva del juego donde el jugador compite contra la banca, aplicando las reglas clásicas del **21**.

El código está diseñado con una estructura clara y modular, utilizando programación orientada a objetos para facilitar la legibilidad y mantenimiento del código.

**_→ Presentación:_** [Blackjack (Metodología Scrum Ágil) - LaSantaPI](https://www.canva.com/design/DAGei2Tg2Yw/v0DXi25yt5pu8jIuSqqTDA/view?utm_content=DAGei2Tg2Yw&utm_campaign=designshare&utm_medium=link2&utm_source=uniquelinks&utlId=h0580eb759c)

## 🎯 Características
✔️ Juego de **Blackjack** con reglas tradicionales.<br>
✔️ Implementado en **Java** con una estructura modular.<br>
✔️ Uso de **Swing** para la interfaz gráfica.<br>
✔️ Fácil ejecución y extensión del código.<br>
✔️ Código limpio y bien documentado.<br>

## 🔧 Requisitos
Para ejecutar este proyecto necesitas:
- ✅ **Java 8** o superior.

## 🚀 Instalación y Ejecución
### 1️⃣ Clonar el repositorio
```sh
git clone https://github.com/byAlexLR/Blackjack-LaSantaPI.git
cd Blackjack-LaSantaPI
```

### 2️⃣ Compilar el proyecto
```sh
javac -d bin src/*.java
```

### 3️⃣ Ejecutar el juego
```sh
java -cp bin BlackjackGUI
```

## 📂 Estructura del Proyecto
```
Blackjack-LaSantaPI/
│── README.md                # Documentación del proyecto
│── bin/                      # Archivos compilados
│   ├── BlackjackGUI.class
│   ├── Carta.class
│   ├── CreditsPanel.class
│   ├── Mano.class
│   ├── Mazo.class
│   ├── RulesPanel.class
│── src/                      # Código fuente del proyecto
│   ├── music/                # Música del proyecto
│   │   └── music.wav         # Música de fondo
│   ├── BlackjackGUI.java     # Interfaz gráfica del juego
│   ├── Carta.java            # Representación de una carta
│   ├── CreditsPanel.java     # Panel de créditos
│   ├── Mano.java             # Lógica de la mano del jugador y la banca
│   ├── Mazo.java             # Mazo de cartas y su gestión
│   ├── RulesPanel.java       # Panel con las reglas del juego
```

## 📜 Explicación del Código
El código sigue una estructura orientada a objetos con las siguientes clases principales:

- **BlackjackGUI.java**: Controla la interfaz gráfica del juego.
- **Carta.java**: Representa una carta individual con su valor y palo.
- **Mazo.java**: Administra el mazo de cartas, incluyendo el shuffle y la distribución de cartas.
- **Mano.java**: Representa la mano del jugador y de la banca, permitiendo sumar valores y determinar el ganador.
- **CreditsPanel.java** y **RulesPanel.java**: Paneles adicionales con información sobre los créditos y las reglas del juego.

Cada una de estas clases está bien organizada para hacer el código más comprensible y fácil de modificar.

## 📄 Licencia
Este proyecto está bajo la licencia **MIT**. Puedes usarlo libremente, pero sin garantías.

---
Hecho con ❤️ por LaSantaPI

