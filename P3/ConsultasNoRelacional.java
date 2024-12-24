/*Establece la conexión con la base de datos MongoDB local y obtiene las colecciones necesarias. */
import com.mongodb.client.*;
import org.bson.Document;

import java.util.*;

public class ConsultasNoRelacional {
    public static void main(String[] args) {
        //Conexión a MongoDB
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("escuela");

            MongoCollection<Document> alumnos = database.getCollection("alumnos");
            MongoCollection<Document> materias = database.getCollection("materias");
            MongoCollection<Document> calificaciones = database.getCollection("calificaciones");

            // A)Alumno con mejor promedio
            /*Llama a la función calcularPromedios para obtener los promedios de los alumnos, luego busca el alumno con el mejor promedio y lo imprime */
            Map<String, Double> promedios = calcularPromedios(calificaciones);
            String mejorAlumno = promedios.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();

            System.out.println("El alumno con mejor promedio es: " + buscarAlumno(mejorAlumno, alumnos));

            //B)Alumnos ordenados por promedio de mayor a menor
            /*Ordena a los alumnos por su promedio de mayor a menor y los imprime junto con su promedio. */
            System.out.println("\nAlumnos ordenados por promedio de mayor a menor:");
            promedios.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .forEach(entry -> System.out.println(buscarAlumno(entry.getKey(), alumnos) + " - Promedio: " + entry.getValue()));

            // C) Alumnos que reprobaron una materia
            /*Recorre las calificaciones, identifica a los alumnos con calificaciones menores a 6 y los imprime con la materia y la calificación correspondiente. */
            System.out.println("\nAlumnos que reprobaron una materia:");
            for (Document c : calificaciones.find()) {
                int calificacion = c.getInteger("calificacion");
                if (calificacion < 6) {
                    String curp = c.getString("curp");
                    int idMateria = c.getInteger("idMateria");
                    System.out.println(buscarAlumno(curp, alumnos) + " - Materia: " + buscarMateria(idMateria, materias) + " - Calificación: " + calificacion);
                }
            }
        }
    }

    public static Map<String, Double> calcularPromedios(MongoCollection<Document> calificaciones) {
        Map<String, List<Integer>> agrupadas = new HashMap<>();
        for (Document c : calificaciones.find()) {
            String curp = c.getString("curp");
            int calificacion = c.getInteger("calificacion");
            agrupadas.computeIfAbsent(curp, k -> new ArrayList<>()).add(calificacion);
        }

        Map<String, Double> promedios = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : agrupadas.entrySet()) {
            double promedio = entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0);
            promedios.put(entry.getKey(), promedio);
        }
        return promedios;
    }

    public static String buscarAlumno(String curp, MongoCollection<Document> alumnos) {
        Document alumno = alumnos.find(new Document("curp", curp)).first();
        if (alumno != null) {
            return alumno.getString("nombres") + " " + alumno.getString("apellidoP") + " " + alumno.getString("apellidoM");
        }
        return "Desconocido";
    }

    public static String buscarMateria(int idMateria, MongoCollection<Document> materias) {
        Document materia = materias.find(new Document("idMateria", idMateria)).first();
        if (materia != null) {
            return materia.getString("materia");
        }
        return "Desconocida";
    }
}
