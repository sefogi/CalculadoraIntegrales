# 📊 Calculadora de Integrales Definidas

Aplicación de escritorio en Java para calcular integrales definidas, visualizar gráficos y analizar áreas bajo la curva de funciones matemáticas, incluyendo funciones trigonométricas.



## 🎯 Características

- ✅ **Cálculo de integrales definidas** usando el método del trapecio
- ✅ **Cálculo de área total** (valor absoluto)
- ✅ **Separación de áreas positivas y negativas**
- ✅ **Visualización gráfica** con áreas sombreadas
- ✅ **Soporte completo para funciones trigonométricas**
- ✅ **Interfaz gráfica intuitiva** con Swing
- ✅ **Sin dependencias externas** - Java puro

## 🚀 Ejemplo de Uso

El programa resuelve correctamente el caso especial de **x³ entre -3 y 3**:
- **Integral definida**: 0 (las áreas se cancelan)
- **Área total real**: 40.5 unidades² (suma de valores absolutos)

## 📋 Requisitos

- Java JDK 8 o superior
- NetBeans (opcional, pero recomendado)
- No requiere librerías externas

## 🛠️ Instalación y Ejecución

### Opción 1: Con NetBeans

1. Abre NetBeans
2. Ve a `File` → `Open Project`
3. Selecciona la carpeta del proyecto `CalculadoraIntegrales`
4. Presiona `F6` para ejecutar o clic derecho → `Run`

### Opción 2: Desde línea de comandos

```bash
# Navegar al directorio del proyecto
cd CalculadoraIntegrales

# Compilar
javac -d target/classes src/main/java/com/mycompany/calculadoraintegrales/CalculadoraIntegrales.java

# Ejecutar
java -cp target/classes com.mycompany.calculadoraintegrales.CalculadoraIntegrales
```

### Opción 3: Con Maven

```bash
# Compilar
mvn clean compile

# Ejecutar
mvn exec:java -Dexec.mainClass="com.mycompany.calculadoraintegrales.CalculadoraIntegrales"
```

## 📐 Sintaxis de Funciones Soportadas

### Operadores Básicos
```
+   Suma
-   Resta
*   Multiplicación
/   División
^   Potencia
()  Paréntesis
```

### Funciones Matemáticas
```
sin(x)   - Seno
cos(x)   - Coseno
tan(x)   - Tangente
sqrt(x)  - Raíz cuadrada
abs(x)   - Valor absoluto
ln(x)    - Logaritmo natural
log(x)   - Logaritmo base 10
exp(x)   - Exponencial (e^x)
```

### Constantes
```
pi  - Número Pi (3.14159...)
e   - Número de Euler (2.71828...)
```

## 💡 Ejemplos de Funciones

### Polinomios
```
x^3
x^2 - 4
2*x^2 + 3*x - 1
x^4 - 5*x^2 + 4
```

### Funciones Trigonométricas
```
sin(x)
cos(x)
sin(x)*cos(x)
sin(x)^2 + cos(x)^2
tan(x)
```

### Funciones Compuestas
```
sqrt(x^2 + 1)
abs(sin(x))
exp(-x^2)
ln(x+1)
sin(x)/x
```

### Usando Constantes
```
sin(pi*x)
e^x
cos(2*pi*x)
```

## 📊 Interpretación de Resultados

La aplicación calcula cuatro valores importantes:

1. **Integral Definida**: Resultado algebraico de ∫f(x)dx
   - Puede ser negativa o cero
   - Las áreas positivas y negativas se restan

2. **Área Total**: Suma de valores absolutos
   - Siempre positiva
   - Representa el área real bajo la curva

3. **Área Positiva**: Región donde f(x) > 0

4. **Área Negativa**: Región donde f(x) < 0 (en valor absoluto)

## 🎨 Interfaz Gráfica

### Panel de Entrada
- Campo para ingresar la función matemática
- Campos para límite inferior y superior
- Botón "Calcular" para ejecutar

### Panel de Resultados
- Muestra los 4 valores calculados
- Interpretación automática del resultado
- Detecta casos especiales

### Panel Gráfico
- Visualización de la función
- Área positiva sombreada en azul
- Área negativa sombreada en rojo
- Ejes X e Y con etiquetas
- Leyenda explicativa

## 🔍 Método Numérico

La aplicación utiliza el **Método del Trapecio** para calcular las integrales:

- Divide el intervalo [a,b] en n subintervalos (n=1000 por defecto)
- Aproxima el área bajo cada segmento como un trapecio
- Mayor precisión que métodos más simples como rectángulos

Fórmula:
```
∫[a,b] f(x)dx ≈ (h/2) * [f(x₀) + 2f(x₁) + 2f(x₂) + ... + 2f(xₙ₋₁) + f(xₙ)]
```

## 🧮 Casos de Prueba Recomendados

### Caso 1: Función cúbica simétrica
```
Función: x^3
Límites: -3 a 3
Resultado esperado: Integral ≈ 0, Área total ≈ 40.5
```

### Caso 2: Función seno
```
Función: sin(x)
Límites: 0 a 6.28 (2π)
Resultado esperado: Integral ≈ 0, Área total ≈ 4
```

### Caso 3: Parábola
```
Función: x^2 - 4
Límites: -3 a 3
Resultado esperado: Integral ≈ -6, Área mixta
```

### Caso 4: Coseno
```
Función: cos(x)
Límites: 0 a 3.14 (π)
Resultado esperado: Integral ≈ 0, Área total ≈ 2
```

## ⚠️ Limitaciones Conocidas

- No soporta funciones implícitas o paramétricas
- No calcula integrales impropias (con límites infinitos)
- La precisión depende del número de subdivisiones (n=1000)
- Funciones con discontinuidades pueden dar resultados inexactos

## 🐛 Solución de Problemas

### Error: "No se pudo inicializar el motor JavaScript"
✅ **Solución**: Este código no usa JavaScript, usa un evaluador propio. Si ves este mensaje, estás usando una versión antigua del código.

### Error: "Error al evaluar la función"
✅ **Solución**: Verifica la sintaxis. Recuerda:
- Usar `^` para potencias (no `**`)
- Usar `*` explícitamente (escribir `2*x` no `2x`)
- Cerrar todos los paréntesis

### El gráfico no se muestra
✅ **Solución**: Asegúrate de que los límites sean válidos (a < b) y que la función sea evaluable en todo el intervalo.

## 👨‍💻 Estructura del Código

```
CalculadoraIntegrales.java
├── CalculadoraIntegrales (Clase principal)
│   ├── Interfaz gráfica (Swing)
│   ├── Métodos de cálculo
│   └── Gestión de eventos
├── EvaluadorExpresiones (Evaluador matemático)
│   ├── Parser de expresiones
│   ├── Conversión infija a postfija
│   └── Evaluación de funciones
├── PanelGrafico (Visualización)
│   ├── Dibujo de curvas
│   ├── Sombreado de áreas
│   └── Ejes y etiquetas
└── Clases auxiliares
    ├── ResultadoAreas
    └── Punto
```

## 📝 Notas Técnicas

- **Método numérico**: Trapecio compuesto con 1000 subdivisiones
- **Precisión**: Aproximadamente 4-6 decimales correctos
- **Renderizado**: Graphics2D con antialiasing
- **Arquitectura**: MVC simplificado

## 🤝 Contribuciones

Si encuentras algún error o quieres agregar nuevas características:

1. Reporta issues con ejemplos específicos
2. Sugiere mejoras en la interfaz
3. Propón nuevas funciones matemáticas

📄 Licencia y Derechos
© Todos los derechos reservados
Este software es de uso privado y no puede ser:

Redistribuido sin autorización
Modificado y compartido públicamente
Utilizado con fines comerciales sin permiso
Copiado o reproducido sin consentimiento del autor

Para uso académico personal únicamente.
## 👤 Autor

Desarrollado como herramienta educativa por sebastian forero

---

**¿Preguntas o sugerencias?** No dudes en reportar cualquier problema que encuentres.
