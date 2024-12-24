import java.util.HashSet; /*Clase de Java para almacenar*/

 /*Clase unica de caracteres*/
public class UniqueCharacters {
    public static boolean hasUniqueCharacters(String ) { /*Metodo que se le pasa como variable la cadena*/ 
        HashSet<Character> charSet = new HashSet<>();/*Almacena los caracteres */
        for (char c : s.toCharArray()) { /*recorre toda la cadena para convertirla en un arreglo de caracteres */
            if (!charSet.add(c)) {
                return false; /*Si no se puede agregar, es porque ya existe*/
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(hasUniqueCharacters("String"));       // true
        System.out.println(hasUniqueCharacters("Some String"));  // false
        System.out.println(hasUniqueCharacters("Java"));         // false
        System.out.println(hasUniqueCharacters("abcdef"));       // true
    }
}
