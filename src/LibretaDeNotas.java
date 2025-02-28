import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LibretaDeNotas {
    // Se añade una opción más para ingresar estudiantes y notas que serán guardados en el array.
    // No se puede acceder a las demás opciones sin antes ingresar al menos 1 estudiante.
    // Por honor al tiempo no se creó un menú y submenú en el que en primera instancia pregunte por ingresar estudiantes y notas
    // y otro que nos lleve a las acciones de cálculo.
    // Se pueden mejorar también la redundancia al revisar si un estudiante existe o no
    // y la variable promedioCohorte solo se puede actualizar manualmente

    public static Map<String, ArrayList<Double>> notas = new HashMap<>();
    public static double promedioCohorte;

    public static int obtenerCantidadAlumnos(Scanner input) {
        while (true) {
            try {
                System.out.println("Ingrese la cantidad de alumnos: ");
                int cantidadAlumnos = input.nextInt();
                input.nextLine();

                if (cantidadAlumnos > 0) {
                    return cantidadAlumnos;
                } else {
                    System.out.println("*** La cantidad de alumnos debe ser un número positivo ***");
                }
            } catch (Exception e) {
                System.out.println("*** Ingrese un número válido ***");
                input.nextLine();
            }
        }
    }

    public static int obtenerCantidadNotas(Scanner input) {
        while (true) {
            try {
                System.out.println("Ingrese la cantidad de notas por alumno: ");
                int cantidadNotas = input.nextInt();
                input.nextLine();

                if (cantidadNotas > 0) {
                    return cantidadNotas;
                } else {
                    System.out.println("*** La cantidad de notas debe ser un número positivo ***");
                }
            } catch (Exception e) {
                System.out.println("*** Ingrese un número válido ***");
                input.nextLine();
            }
        }
    }

    public static void ingresarDatos(Scanner input) {
        int cantidadAlumnos = obtenerCantidadAlumnos(input);
        int cantidadNotas = obtenerCantidadNotas(input);

        for (int i = 0; i < cantidadAlumnos; i++) {
            System.out.println("Ingrese el nombre del alumno " + (i + 1) + ": ");
            String nombre = input.next().toLowerCase();

            ArrayList<Double> notasAlumno = new ArrayList<>();
            for (int j = 0; j < cantidadNotas; j++) {
                while (true) {
                    try {
                        System.out.println("Ingrese la nota " + (j + 1) + " de " + nombre + ": ");
                        double nota = input.nextDouble();

                        if (nota >= 0 && nota <= 7) {
                            notasAlumno.add(nota);
                            break;
                        } else {
                            System.out.println("*** Nota inválida. Debe estar entre 0 y 7 ***");
                        }
                    } catch (Exception e) {
                        System.out.println("*** Ingrese un número válido ***");
                        input.nextLine();
                    }
                }
            }

            notas.put(nombre, notasAlumno);
        }

        calcularPromedioCohorte();
        System.out.println("*** Datos ingresados correctamente ***");
    }

    public static void calcularPromedioCohorte() {
        double sumaTotal = 0;
        int totalNotas = 0;

        for (ArrayList<Double> notasAlumno : notas.values()) {
            for (double nota : notasAlumno) {
                sumaTotal += nota;
                totalNotas++;
            }
        }

        promedioCohorte = sumaTotal / totalNotas;
    }

    public static void mostrarPromedioEstudiantes() {
        if (notas.isEmpty()) {
            System.out.println("*** No hay datos ingresados ***");
            return;
        }

        for (Map.Entry<String, ArrayList<Double>> entry : notas.entrySet()) {
            String nombre = entry.getKey();
            ArrayList<Double> notasAlumno = entry.getValue();
            double promedio = calcularPromedio(notasAlumno);
            System.out.println("El promedio de " + nombre + " es: " + promedio);
        }
        System.out.println("El promedio general es: " + promedioCohorte);
    }

    public static double calcularPromedio(ArrayList<Double> notasAlumno) {
        double suma = 0;
        for (double nota : notasAlumno) {
            suma += nota;
        }
        return suma / notasAlumno.size();
    }

    public static void verificarAprobacion(Scanner input) {
        if (notas.isEmpty()) {
            System.out.println("*** No hay datos ingresados ***");
            return;
        }

        System.out.println("Ingrese el nombre del estudiante: ");
        String nombre = input.next().toLowerCase();

        if (notas.containsKey(nombre)) {
            System.out.println("Ingrese la nota a verificar: ");
            double nota = input.nextDouble();

            if (nota >= 6) {
                System.out.println("*** La nota es Aprobatoria ***");
            } else {
                System.out.println("*** La nota es Reprobatoria ***");
            }
        } else {
            System.out.println("*** Estudiante no encontrado ***");
        }
    }

    public static void verificarNotaConPromedio(Scanner input) {
        if (notas.isEmpty()) {
            System.out.println("*** No hay datos ingresados ***");
            return;
        }

        System.out.println("Ingrese el nombre del estudiante: ");
        String nombre = input.next().toLowerCase();

        if (notas.containsKey(nombre)) {
            System.out.println("Ingrese la nota a verificar: ");
            double nota = input.nextDouble();

            if (nota > promedioCohorte) {
                System.out.println("*** La nota está por sobre el promedio de la cohorte ***");
            } else if (nota < promedioCohorte) {
                System.out.println("*** La nota está por debajo del promedio de la cohorte ***");
            } else {
                System.out.println("*** La nota es igual al promedio de la cohorte ***");
            }
        } else {
            System.out.println("*** Estudiante no encontrado ***");
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int option;
        String menu = "=== Evaluador de Clases 5000 ===" +
                System.lineSeparator() +
                "1) Ingresar datos de estudiantes y notas" +
                System.lineSeparator() +
                "2) Mostrar promedio de los alumnos y promedio general" +
                System.lineSeparator() +
                "3) Verificar si una nota es aprobatoria o reprobatoria para un estudiante" +
                System.lineSeparator() +
                "4) Verificar si una nota está sobre o bajo el promedio del curso para un estudiante" +
                System.lineSeparator() +
                "0) Salir" +
                System.lineSeparator() +
                "Escoja una opción:";

        do {
            System.out.println(menu);
            try {
                option = input.nextInt();
                input.nextLine();

                switch (option) {
                    case 1:
                        ingresarDatos(input);
                        break;
                    case 2:
                        mostrarPromedioEstudiantes();
                        break;
                    case 3:
                        verificarAprobacion(input);
                        break;
                    case 4:
                        verificarNotaConPromedio(input);
                        break;
                    case 0:
                        System.out.println("=== Nos Vemos! ===");
                        break;
                    default:
                        System.out.println("**** Opción no valida. Intente nuevamente ****");
                        break;
                }
            } catch (Exception e) {
                System.out.println("**** Ingrese un número válido ****");
                input.nextLine();
                option = -1;
            }
        } while (option != 0);
        input.close();
    }
}