package com.mycompany.calculadoraintegrales;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

public class CalculadoraIntegrales extends JFrame {
    
    private JTextField txtFuncion;
    private JTextField txtLimiteInf;
    private JTextField txtLimiteSup;
    private JTextArea txtResultados;
    private PanelGrafico panelGrafico;
    private EvaluadorExpresiones evaluador;
    
    public CalculadoraIntegrales() {
        setTitle("Calculadora de Integrales Definidas");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        evaluador = new EvaluadorExpresiones();
        
        // Panel superior - Entrada de datos
        JPanel panelEntrada = crearPanelEntrada();
        add(panelEntrada, BorderLayout.NORTH);
        
        // Panel central dividido
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        // Panel izquierdo - Gráfico
        panelGrafico = new PanelGrafico();
        splitPane.setLeftComponent(panelGrafico);
        
        // Panel derecho - Resultados
        JPanel panelResultados = crearPanelResultados();
        splitPane.setRightComponent(panelResultados);
        
        splitPane.setDividerLocation(600);
        add(splitPane, BorderLayout.CENTER);
        
        // Panel inferior - Ayuda
        JPanel panelAyuda = crearPanelAyuda();
        add(panelAyuda, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    private JPanel crearPanelEntrada() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos de Entrada"));
        panel.setBackground(new Color(240, 248, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Función
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Función f(x):"), gbc);
        
        gbc.gridx = 1; gbc.weightx = 1.0;
        txtFuncion = new JTextField("x^3", 20);
        panel.add(txtFuncion, gbc);
        
        // Límite inferior
        gbc.gridx = 2; gbc.weightx = 0;
        panel.add(new JLabel("Límite Inferior:"), gbc);
        
        gbc.gridx = 3; gbc.weightx = 0.3;
        txtLimiteInf = new JTextField("-3", 8);
        panel.add(txtLimiteInf, gbc);
        
        // Límite superior
        gbc.gridx = 4; gbc.weightx = 0;
        panel.add(new JLabel("Límite Superior:"), gbc);
        
        gbc.gridx = 5; gbc.weightx = 0.3;
        txtLimiteSup = new JTextField("3", 8);
        panel.add(txtLimiteSup, gbc);
        
        // Botón calcular
        gbc.gridx = 6; gbc.weightx = 0;
        JButton btnCalcular = new JButton("Calcular");
        btnCalcular.setBackground(new Color(70, 130, 180));
        btnCalcular.setForeground(Color.WHITE);
        btnCalcular.setFont(new Font("Arial", Font.BOLD, 12));
        btnCalcular.addActionListener(e -> calcular());
        panel.add(btnCalcular, gbc);
        
        return panel;
    }
    
    private JPanel crearPanelResultados() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Resultados"));
        
        txtResultados = new JTextArea();
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResultados.setMargin(new Insets(10, 10, 10, 10));
        
        JScrollPane scroll = new JScrollPane(txtResultados);
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelAyuda() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Sintaxis"));
        panel.setBackground(new Color(255, 255, 224));
        
        JTextArea ayuda = new JTextArea(3, 50);
        ayuda.setEditable(false);
        ayuda.setBackground(new Color(255, 255, 224));
        ayuda.setText(
            "Sintaxis soportada:\n" +
            "• Operadores: +, -, *, /, ^ (potencia), paréntesis ()\n" +
            "• Funciones: sin(x), cos(x), tan(x), sqrt(x), abs(x), ln(x), log(x), exp(x)\n" +
            "• Constantes: pi, e\n" +
            "• Ejemplos: x^3, sin(x)*cos(x), 2*x^2+3*x-1, sqrt(x^2+1)"
        );
        ayuda.setFont(new Font("Arial", Font.PLAIN, 10));
        panel.add(ayuda, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void calcular() {
        try {
            String funcion = txtFuncion.getText().trim();
            double a = Double.parseDouble(txtLimiteInf.getText().trim());
            double b = Double.parseDouble(txtLimiteSup.getText().trim());
            
            if (a >= b) {
                JOptionPane.showMessageDialog(this, 
                    "El límite inferior debe ser menor que el superior",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Validar función
            evaluador.evaluar(funcion, 0);
            
            // Calcular integrales
            int n = 1000;
            double integralDefinida = calcularIntegralTrapecio(funcion, a, b, n);
            double areaTotal = calcularAreaTotal(funcion, a, b, n);
            ResultadoAreas areas = calcularAreasPorRegion(funcion, a, b, n);
            
            // Generar datos para gráfico
            ArrayList<Punto> puntos = generarPuntosGrafico(funcion, a, b, 200);
            panelGrafico.setDatos(puntos, a, b);
            
            // Mostrar resultados
            StringBuilder sb = new StringBuilder();
            sb.append("═══════════════════════════════════════════\n");
            sb.append("           RESULTADOS DEL CÁLCULO\n");
            sb.append("═══════════════════════════════════════════\n\n");
            
            sb.append(String.format("Función: %s\n", funcion));
            sb.append(String.format("Intervalo: [%.4f, %.4f]\n\n", a, b));
            
            sb.append("───────────────────────────────────────────\n");
            sb.append("1. INTEGRAL DEFINIDA (Suma algebraica)\n");
            sb.append("───────────────────────────────────────────\n");
            sb.append(String.format("   ∫[%.2f,%.2f] f(x)dx = %.6f\n\n", a, b, integralDefinida));
            sb.append("   • Puede ser negativa o cero\n");
            sb.append("   • Áreas positivas y negativas se restan\n\n");
            
            sb.append("───────────────────────────────────────────\n");
            sb.append("2. ÁREA TOTAL (Valor absoluto)\n");
            sb.append("───────────────────────────────────────────\n");
            sb.append(String.format("   Área Total = %.6f unidades²\n\n", areaTotal));
            sb.append("   • Siempre positiva\n");
            sb.append("   • Suma de |áreas positivas| + |áreas negativas|\n\n");
            
            sb.append("───────────────────────────────────────────\n");
            sb.append("3. ÁREAS POR REGIÓN\n");
            sb.append("───────────────────────────────────────────\n");
            sb.append(String.format("   Área Positiva (f(x) > 0): %.6f\n", areas.areaPositiva));
            sb.append(String.format("   Área Negativa (f(x) < 0): %.6f\n\n", areas.areaNegativa));
            
            sb.append("═══════════════════════════════════════════\n");
            sb.append("              INTERPRETACIÓN\n");
            sb.append("═══════════════════════════════════════════\n\n");
            
            if (Math.abs(integralDefinida) < 0.0001 && areaTotal > 0.0001) {
                sb.append("⚠ CASO ESPECIAL DETECTADO:\n");
                sb.append("  La integral definida es ≈0 porque las áreas\n");
                sb.append("  positiva y negativa se cancelan.\n");
                sb.append("  Sin embargo, el ÁREA REAL bajo la curva es:\n");
                sb.append(String.format("  %.6f unidades cuadradas.\n", areaTotal));
            } else if (integralDefinida < 0) {
                sb.append("• La integral es negativa porque hay más\n");
                sb.append("  área bajo el eje X que sobre él.\n");
            } else {
                sb.append("• La integral es positiva porque hay más\n");
                sb.append("  área sobre el eje X que bajo él.\n");
            }
            
            txtResultados.setText(sb.toString());
            txtResultados.setCaretPosition(0);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese números válidos para los límites",
                "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error al evaluar la función:\n" + e.getMessage() +
                "\n\nVerifique la sintaxis de la función.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private double calcularIntegralTrapecio(String funcion, double a, double b, int n) throws Exception {
        double h = (b - a) / n;
        double suma = 0;
        
        for (int i = 0; i <= n; i++) {
            double x = a + i * h;
            double y = evaluador.evaluar(funcion, x);
            
            if (i == 0 || i == n) {
                suma += y;
            } else {
                suma += 2 * y;
            }
        }
        
        return (h / 2) * suma;
    }
    
    private double calcularAreaTotal(String funcion, double a, double b, int n) throws Exception {
        double h = (b - a) / n;
        double area = 0;
        
        for (int i = 0; i < n; i++) {
            double x = a + i * h;
            double y1 = Math.abs(evaluador.evaluar(funcion, x));
            double y2 = Math.abs(evaluador.evaluar(funcion, x + h));
            area += (y1 + y2) * h / 2;
        }
        
        return area;
    }
    
    private ResultadoAreas calcularAreasPorRegion(String funcion, double a, double b, int n) throws Exception {
        double h = (b - a) / n;
        double areaPositiva = 0;
        double areaNegativa = 0;
        
        for (int i = 0; i < n; i++) {
            double x = a + i * h;
            double y1 = evaluador.evaluar(funcion, x);
            double y2 = evaluador.evaluar(funcion, x + h);
            double areaSegmento = (y1 + y2) * h / 2;
            
            if (areaSegmento > 0) {
                areaPositiva += areaSegmento;
            } else {
                areaNegativa += Math.abs(areaSegmento);
            }
        }
        
        return new ResultadoAreas(areaPositiva, areaNegativa);
    }
    
    private ArrayList<Punto> generarPuntosGrafico(String funcion, double a, double b, int numPuntos) throws Exception {
        ArrayList<Punto> puntos = new ArrayList<>();
        
        for (int i = 0; i <= numPuntos; i++) {
            double x = a + (b - a) * i / numPuntos;
            double y = evaluador.evaluar(funcion, x);
            puntos.add(new Punto(x, y));
        }
        
        return puntos;
    }
    
    // Clase para evaluar expresiones matemáticas
    static class EvaluadorExpresiones {
        
        public double evaluar(String expresion, double x) throws Exception {
            if (expresion == null || expresion.trim().isEmpty()) {
                throw new Exception("Expresión vacía");
            }
            
            // Convertir a minúsculas y eliminar espacios
            expresion = expresion.toLowerCase().trim();
            
            // Reemplazar x por su valor (con paréntesis para números negativos)
            String valorX = x < 0 ? "(" + x + ")" : String.valueOf(x);
            expresion = expresion.replaceAll("x", valorX);
            
            // Reemplazar constantes
            expresion = expresion.replace("pi", String.valueOf(Math.PI));
            
            // Manejar 'e' con cuidado para no reemplazar dentro de 'exp'
            expresion = reemplazarConstanteE(expresion);
            
            // Procesar funciones
            expresion = procesarFunciones(expresion);
            
            return evaluarExpresion(expresion);
        }
        
        private String reemplazarConstanteE(String expr) {
            // Reemplazar 'e' solo cuando no es parte de 'exp'
            StringBuilder resultado = new StringBuilder();
            for (int i = 0; i < expr.length(); i++) {
                if (expr.charAt(i) == 'e') {
                    // Verificar si es parte de 'exp'
                    if (i > 0 && i + 1 < expr.length() && 
                        expr.charAt(i-1) == 'x' && expr.charAt(i+1) == 'p') {
                        resultado.append('e');
                    } else if (i == 0 || !Character.isLetter(expr.charAt(i-1))) {
                        // Es la constante e
                        if (i + 1 < expr.length() && expr.charAt(i+1) == 'x') {
                            resultado.append('e');
                        } else {
                            resultado.append(Math.E);
                        }
                    } else {
                        resultado.append('e');
                    }
                } else {
                    resultado.append(expr.charAt(i));
                }
            }
            return resultado.toString();
        }
        
        private String procesarFunciones(String expr) throws Exception {
            String[] funciones = {"sin", "cos", "tan", "sqrt", "abs", "ln", "log", "exp"};
            
            for (String func : funciones) {
                while (expr.contains(func + "(")) {
                    int inicio = expr.indexOf(func + "(");
                    int finParentesis = encontrarParentesisCierre(expr, inicio + func.length());
                    
                    String argumento = expr.substring(inicio + func.length() + 1, finParentesis);
                    double valorArg = evaluarExpresion(argumento);
                    double resultado = aplicarFuncion(func, valorArg);
                    
                    expr = expr.substring(0, inicio) + resultado + expr.substring(finParentesis + 1);
                }
            }
            
            return expr;
        }
        
        private int encontrarParentesisCierre(String expr, int inicio) {
            int contador = 1;
            for (int i = inicio + 1; i < expr.length(); i++) {
                if (expr.charAt(i) == '(') contador++;
                if (expr.charAt(i) == ')') contador--;
                if (contador == 0) return i;
            }
            throw new IllegalArgumentException("Paréntesis sin cerrar");
        }
        
        private double aplicarFuncion(String func, double arg) {
            switch (func) {
                case "sin": return Math.sin(arg);
                case "cos": return Math.cos(arg);
                case "tan": return Math.tan(arg);
                case "sqrt": return Math.sqrt(arg);
                case "abs": return Math.abs(arg);
                case "ln": return Math.log(arg);
                case "log": return Math.log10(arg);
                case "exp": return Math.exp(arg);
                default: throw new IllegalArgumentException("Función desconocida: " + func);
            }
        }
        
        private double evaluarExpresion(String expr) throws Exception {
            expr = expr.trim();
            
            // Eliminar espacios
            expr = expr.replaceAll("\\s+", "");
            
            // Convertir a postfijo y evaluar
            return evaluarPostfijo(infixToPostfix(expr));
        }
        
        private String infixToPostfix(String infix) {
            StringBuilder result = new StringBuilder();
            Stack<Character> stack = new Stack<>();
            
            for (int i = 0; i < infix.length(); i++) {
                char c = infix.charAt(i);
                
                if (Character.isDigit(c) || c == '.') {
                    result.append(c);
                } else if (c == '-' && (i == 0 || infix.charAt(i-1) == '(' || esOperador(infix.charAt(i-1)))) {
                    // Es un menos unario
                    result.append(c);
                } else if (c == '(') {
                    stack.push(c);
                } else if (c == ')') {
                    result.append(' ');
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        result.append(stack.pop()).append(' ');
                    }
                    if (!stack.isEmpty()) stack.pop();
                } else if (esOperador(c)) {
                    result.append(' ');
                    while (!stack.isEmpty() && precedencia(stack.peek()) >= precedencia(c)) {
                        result.append(stack.pop()).append(' ');
                    }
                    stack.push(c);
                }
            }
            
            result.append(' ');
            while (!stack.isEmpty()) {
                result.append(stack.pop()).append(' ');
            }
            
            return result.toString();
        }
        
        private double evaluarPostfijo(String postfix) throws Exception {
            Stack<Double> stack = new Stack<>();
            String[] tokens = postfix.trim().split("\\s+");
            
            for (String token : tokens) {
                if (token.isEmpty()) continue;
                
                try {
                    if (esOperador(token.charAt(0)) && token.length() == 1) {
                        if (stack.size() < 2) {
                            throw new Exception("Expresión inválida: operador sin operandos suficientes");
                        }
                        double b = stack.pop();
                        double a = stack.pop();
                        stack.push(aplicarOperador(a, b, token.charAt(0)));
                    } else {
                        stack.push(Double.parseDouble(token));
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Token inválido: " + token);
                }
            }
            
            if (stack.isEmpty()) {
                throw new Exception("Expresión inválida: resultado vacío");
            }
            
            return stack.pop();
        }
        
        private boolean esOperador(char c) {
            return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
        }
        
        private int precedencia(char op) {
            switch (op) {
                case '+':
                case '-':
                    return 1;
                case '*':
                case '/':
                    return 2;
                case '^':
                    return 3;
                default:
                    return 0;
            }
        }
        
        private double aplicarOperador(double a, double b, char op) {
            switch (op) {
                case '+': return a + b;
                case '-': return a - b;
                case '*': return a * b;
                case '/': return a / b;
                case '^': return Math.pow(a, b);
                default: throw new IllegalArgumentException("Operador desconocido: " + op);
            }
        }
    }
    
    // Clase para almacenar resultados de áreas
    static class ResultadoAreas {
        double areaPositiva;
        double areaNegativa;
        
        ResultadoAreas(double pos, double neg) {
            this.areaPositiva = pos;
            this.areaNegativa = neg;
        }
    }
    
    // Clase para puntos del gráfico
    static class Punto {
        double x, y;
        
        Punto(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    
    // Panel personalizado para dibujar el gráfico
    class PanelGrafico extends JPanel {
        private ArrayList<Punto> puntos;
        private double minX, maxX;
        private double minY, maxY;
        
        public PanelGrafico() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createTitledBorder("Gráfico de la Función"));
            setPreferredSize(new Dimension(600, 500));
        }
        
        public void setDatos(ArrayList<Punto> puntos, double minX, double maxX) {
            this.puntos = puntos;
            this.minX = minX;
            this.maxX = maxX;
            
            // Calcular rango Y
            minY = Double.MAX_VALUE;
            maxY = -Double.MAX_VALUE;
            
            for (Punto p : puntos) {
                if (p.y < minY) minY = p.y;
                if (p.y > maxY) maxY = p.y;
            }
            
            // Agregar margen
            double margenY = (maxY - minY) * 0.1;
            minY -= margenY;
            maxY += margenY;
            
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            if (puntos == null || puntos.isEmpty()) {
                g.setColor(Color.GRAY);
                g.drawString("Ingrese una función y presione Calcular", 200, 250);
                return;
            }
            
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = getWidth();
            int height = getHeight();
            int margen = 50;
            
            // Área de dibujo
            int graphWidth = width - 2 * margen;
            int graphHeight = height - 2 * margen;
            
            // Dibujar ejes
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            
            // Eje X
            int ejeY = margen + (int)(graphHeight * (maxY / (maxY - minY)));
            g2.drawLine(margen, ejeY, width - margen, ejeY);
            
            // Eje Y
            int ejeX = margen + (int)(graphWidth * (-minX / (maxX - minX)));
            g2.drawLine(ejeX, margen, ejeX, height - margen);
            
            // Dibujar áreas coloreadas
            for (int i = 0; i < puntos.size() - 1; i++) {
                Punto p1 = puntos.get(i);
                Punto p2 = puntos.get(i + 1);
                
                int x1 = margen + (int)(graphWidth * (p1.x - minX) / (maxX - minX));
                int x2 = margen + (int)(graphWidth * (p2.x - minX) / (maxX - minX));
                int y1 = margen + (int)(graphHeight * (maxY - p1.y) / (maxY - minY));
                int y2 = margen + (int)(graphHeight * (maxY - p2.y) / (maxY - minY));
                
                // Área positiva (azul)
                if (p1.y > 0 && p2.y > 0) {
                    g2.setColor(new Color(100, 149, 237, 100));
                    int[] xPoints = {x1, x2, x2, x1};
                    int[] yPoints = {y1, y2, ejeY, ejeY};
                    g2.fillPolygon(xPoints, yPoints, 4);
                }
                // Área negativa (rojo)
                else if (p1.y < 0 && p2.y < 0) {
                    g2.setColor(new Color(255, 99, 71, 100));
                    int[] xPoints = {x1, x2, x2, x1};
                    int[] yPoints = {y1, y2, ejeY, ejeY};
                    g2.fillPolygon(xPoints, yPoints, 4);
                }
            }
            
            // Dibujar curva
            g2.setColor(new Color(0, 0, 139));
            g2.setStroke(new BasicStroke(2));
            
            for (int i = 0; i < puntos.size() - 1; i++) {
                Punto p1 = puntos.get(i);
                Punto p2 = puntos.get(i + 1);
                
                int x1 = margen + (int)(graphWidth * (p1.x - minX) / (maxX - minX));
                int x2 = margen + (int)(graphWidth * (p2.x - minX) / (maxX - minX));
                int y1 = margen + (int)(graphHeight * (maxY - p1.y) / (maxY - minY));
                int y2 = margen + (int)(graphHeight * (maxY - p2.y) / (maxY - minY));
                
                g2.drawLine(x1, y1, x2, y2);
            }
            
            // Etiquetas de los ejes
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.PLAIN, 10));
            
            // Etiquetas eje X
            for (int i = 0; i <= 5; i++) {
                double x = minX + (maxX - minX) * i / 5;
                int posX = margen + (int)(graphWidth * i / 5.0);
                g2.drawString(String.format("%.2f", x), posX - 15, ejeY + 20);
                g2.drawLine(posX, ejeY - 3, posX, ejeY + 3);
            }
            
            // Etiquetas eje Y
            for (int i = 0; i <= 5; i++) {
                double y = minY + (maxY - minY) * i / 5;
                int posY = height - margen - (int)(graphHeight * i / 5.0);
                g2.drawString(String.format("%.2f", y), ejeX - 40, posY + 5);
                g2.drawLine(ejeX - 3, posY, ejeX + 3, posY);
            }
            
            // Leyenda
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.setColor(new Color(100, 149, 237));
            g2.fillRect(width - 180, 20, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawString("Área positiva", width - 160, 32);
            
            g2.setColor(new Color(255, 99, 71));
            g2.fillRect(width - 180, 40, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawString("Área negativa", width - 160, 52);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalculadoraIntegrales calculadora = new CalculadoraIntegrales();
            calculadora.setVisible(true);
        });
    }
}