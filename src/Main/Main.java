package Main;

import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import view.TablaTokens;

public class Main {
    private ArrayList<String> lista;
    private ArrayList<Token> tokens, errores;
    private String linea, lexema, archivo;
    private AutomataTokens automata;
    private boolean key, error;
    private TablaTokens tabla;
    
    private static final String[] palabrasReservadas = {
        "abstract", "assert", "boolean", "break", "byte", "case", "catch",
        "char", "class", "const", "continue", "default", "do", "double", "else",
        "enum", "extends", "final", "finally", "float", "for", "goto", "if",
        "implements", "import", "instanceof", "int", "interface", "long", "native",
        "new", "package", "private", "protected", "public", "return", "short", 
        "static", "strictfp", "super", "switch", "sychronized", "this", "throw",
        "throws", "transient", "try", "void", "volatile", "while"
    };
    
    private static final char[] caracteresEspeciales = {
        '(', ')', '{', '}', '[', ']', '+', '-', '*', '/', '=', ';', '<', '>', '"', '\'', '.', '_'
    };
    
    public Main(){
        automata = new AutomataTokens();
        lista = leerArchivo();
        tokens = new ArrayList();
        errores = new ArrayList();
        lexema = "";
        error = false;
        //System.out.println(lista);
        for (int i = 0; i < lista.size(); i++) {
            linea = lista.get(i);
            for (int j = 0; j < linea.length(); j++) {
                if(!isSpace(linea.charAt(j)) && !isCaracterEspecial(linea.charAt(j))){
                    switch(automata.getEstadoActual()){
                        case "0":
                            if(isDigit(linea.charAt(j))){
                                automata.cambiarEstadoActual(AutomataTokens.IS_DIGIT);
                                lexema += String.valueOf(linea.charAt(j));
                            }
                            else{
                                automata.cambiarEstadoActual(AutomataTokens.OTHER);
                                lexema += String.valueOf(linea.charAt(j));
                            }
                        break;
                        case "1":
                            if(!isDigit(linea.charAt(j))){
                                error = true;
                                automata.cambiarEstadoActual(AutomataTokens.OTHER);
                                lexema += String.valueOf(linea.charAt(j));
                            }
                            else{
                                lexema += String.valueOf(linea.charAt(j));
                            }
                        break;
                        case "2":
                            lexema += String.valueOf(linea.charAt(j));
                        break;
                        case "3":
                            if(isAlpha(linea.charAt(j))){
                                automata.cambiarEstadoActual(AutomataTokens.IS_ALPHA);
                                lexema += String.valueOf(linea.charAt(j));
                            }else if(isOther(linea.charAt(j))){
                                automata.cambiarEstadoActual(AutomataTokens.OTHER);
                                lexema += String.valueOf(linea.charAt(j));
                            }
                        break;
                        case "4":
                            if(isAlpha(linea.charAt(j)) || isDigit(linea.charAt(j))){
                                lexema += String.valueOf(linea.charAt(j));
                            }else if(isOther(linea.charAt(j))){
                                verificarPalabraReservada();
                            }
                        break;
                        case "5":
                            generarToken(261);
                            automata.reiniciar();
                            lexema = "";
                        break;
                        case "6":
                           generarToken(262);
                           automata.reiniciar();
                           lexema = "";
                        break;
                    }
                } else if(isCaracterEspecial(linea.charAt(j))){
                    if(lexema.length() != 0){
                        if(!error)
                            verificarPalabraReservada();
                        else{
                            errores.add(new Token(0, lexema));
                            automata.reiniciar();
                            error = false;
                        }
                        lexema = String.valueOf(linea.charAt(j));
                        generarToken((int)linea.charAt(j));
                        lexema = "";
                    }else{
                        lexema = String.valueOf(linea.charAt(j));
                        generarToken((int)linea.charAt(j));
                        lexema = "";
                    }
                }else if(isSpace(linea.charAt(j)) && !lexema.equals("")){
                    if(lexema.length() > 1 && !error){
                        verificarPalabraReservada();
                    }else if(error){
                        errores.add(new Token(0, lexema));
                        lexema = "";
                        automata.reiniciar();
                    }
                }
            }
        }
        tabla = new TablaTokens(tokens, errores, archivo);
        //System.out.println(errores);
    }
    
    public ArrayList<String> leerArchivo(){
        JFileChooser chooser = new JFileChooser();
        ArrayList<String> lista = new ArrayList();
        String line="";
        if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            archivo = chooser.getSelectedFile().getAbsolutePath();
        }
        try{
            BufferedReader lector = new BufferedReader(new FileReader(chooser.getSelectedFile()));
            while((line = lector.readLine()) != null){
                lista.add(line);
            }
            lector.close();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        return lista;
    }
    
    public void verificarPalabraReservada(){
        key = false;
        for (int k = 0; k < palabrasReservadas.length; k++) {
            if(lexema.equals(palabrasReservadas[k])){
                generarToken(260);
                automata.reiniciar();
                lexema = "";
                key = true;
                break;
            }
        }
        if(!key){
            generarToken(261);
            automata.reiniciar();
            lexema = "";
        }
    }
    
    public void generarToken(int tipo){
        tokens.add(new Token(tipo, lexema));
    }
    
    public boolean isDigit(char caracter){
        String aux = String.valueOf(caracter);
        return aux.matches("[0-9]");
    }
    
    public boolean isAlpha(char caracter){
        String aux = String.valueOf(caracter);
        return aux.matches("[a-z]|[A-Z]");
    }
    
    public boolean isOther(char caracter){
        String aux = String.valueOf(caracter);
        return aux.matches("[^a-zA-Z0-9]");
    }
    
    public boolean isSpace(char caracter){
        String aux = String.valueOf(caracter);
        return aux.equals(" ") || aux.equals("\t");
    }
    
    public boolean isCaracterEspecial(char caracter){
        boolean key = false;
        for (int i = 0; i < caracteresEspeciales.length; i++) {
            if(caracter == caracteresEspeciales[i]){
                key = true;
            }
        }
        return key;
    }
    
    public static void main(String[] args) {
        new Main();
    }
}
