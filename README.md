# 🃏 Blackjack - LaSantaPI

## 📌 Descripción
Este es un juego de **Blackjack** desarrollado en **Java** utilizando **Maven**. El objetivo es implementar una versión sencilla del juego donde el jugador compite contra la banca, aplicando las reglas clásicas del **21**.

El código está diseñado con una estructura clara y modular, utilizando programación orientada a objetos para facilitar la legibilidad y mantenimiento del código.

## 🎯 Características
✔️ Juego de **Blackjack** con reglas tradicionales.<br>
✔️ Implementado en **Java** con una estructura modular.<br>
✔️ Uso de **Maven** para la gestión de dependencias y automatización de compilación.<br>
✔️ Fácil ejecución y extensión del código.<br>
✔️ Código limpio y bien documentado.<br>

## 🔧 Requisitos
Para ejecutar este proyecto necesitas:
- ✅ **Java 8** o superior.
- ✅ **Apache Maven** instalado y configurado.

## 🚀 Instalación y Ejecución
### 1️⃣ Clonar el repositorio
```sh
git clone https://github.com/byAlexLR/Blackjack-LaSantaPI.git
cd Blackjack-LaSantaPI
```

### 2️⃣ Compilar el proyecto
```sh
mvn clean install
```

### 3️⃣ Ejecutar el juego
```sh
mvn exec:java -Dexec.mainClass="org.example.Blackjack"
```

## 📂 Estructura del Proyecto
```
Blackjack-LaSantaPI/
│── .classpath
│── .project
│── README.md  # Documentación del proyecto
│── .settings/
│   ├── org.eclipse.core.resources.prefs  # Configuraciones del IDE
│── bin/  # Archivos compilados
│   ├── BlackjackGUI.class
│   ├── Carta.class
│   ├── CreditsPanel.class
│   ├── Mano.class
│   ├── Mazo.class
│   ├── RulesPanel.class
│── src/  # Código fuente del proyecto
│   ├── BlackjackGUI.java  # Interfaz gráfica del juego
│   ├── Carta.java  # Representación de una carta
│   ├── CreditsPanel.java  # Panel de créditos
│   ├── Mano.java  # Lógica de la mano del jugador y la banca
│   ├── Mazo.java  # Mazo de cartas y su gestión
│   ├── RulesPanel.java  # Panel con las reglas del juego
```

## 📜 Explicación del Código
El código sigue una estructura orientada a objetos con las siguientes clases principales:

- **Blackjack.java**: Controla la lógica central del juego, incluyendo turnos, reglas y condiciones de victoria.
- **Carta.java**: Representa una carta individual con su valor y palo.
- **Mazo.java**: Administra el mazo de cartas, incluyendo el shuffle y la distribución de cartas.
- **Mano.java**: Representa la mano del jugador y de la banca, permitiendo sumar valores y determinar el ganador.

Cada una de estas clases está bien organizada para hacer el código más comprensible y fácil de modificar.

## 📄 Licencia
Este proyecto está bajo la licencia **MIT**. Puedes usarlo libremente, pero sin garantías.

---
Hecho con ❤️ por LaSantaPI

