import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProcesarEmpleados {
    // Procesamiento de flujos de objetos Empleado.

    public static void main(String[] args) {
        // inicializa arreglo de objetos Empleado
        Empleado[] empleados = {
                new Empleado("Jason", "Red", 5000, "TI"),
                new Empleado("Ashley", "Green", 7600, "TI"),
                new Empleado("Matthew", "Indigo", 3587.5, "Ventas"),
                new Empleado("James", "Indigo", 4700.77, "Marketing"),
                new Empleado("Luke", "Indigo", 6200, "TI"),
                new Empleado("Jason", "Blue", 3200, "Ventas"),
                new Empleado("Wendy", "Brown", 4236.4, "Marketing")};

        // obtiene vista List de los objetos Empleado
        List<Empleado> lista = Arrays.asList(empleados);
        // muestra todos los objetos Empleado
        System.out.println("Lista completa de empleados:");
        lista.stream().forEach(System.out::println);

        // Predicado que devuelve true para salarios en el rango $4000-$6000
        Predicate<Empleado> cuatroASeisMil =
                e -> (e.getSalario() >= 4000 && e.getSalario() <= 6000);

        // Muestra los empleados con salarios en el rango $4000-$6000
        // en orden ascendente por salario
        System.out.printf(
                "%nEmpleados que ganan $4000-$6000 mensuales ordenados por salario:%n");
        lista.stream()
                .filter(cuatroASeisMil)
                .sorted(Comparator.comparing(Empleado::getSalario))
                .forEach(System.out::println);

        // Muestra el primer empleado con salario en el rango $4000-$6000
        System.out.printf("%nPrimer empleado que gana $4000-$6000:%n%s%n",
                lista.stream()
                        .filter(cuatroASeisMil)
                        .findFirst()
                        .get());
        // Funciones para obtener primer nombre y apellido de un Empleado
        Function<Empleado, String> porPrimerNombre = Empleado::getPrimerNombre;
        Function<Empleado, String> porApellidoPaterno = Empleado::getApellidoPaterno;

        // Comparator para comparar empleados por primer nombre y luego por apellido paterno
        Comparator<Empleado> apellidoLuegoNombre =
                Comparator.comparing(porApellidoPaterno).thenComparing(porPrimerNombre);

        // ordena empleados por apellido paterno y luego por primer nombre
        System.out.printf(
                "%nEmpleados en orden ascendente por apellido y luego por nombre:%n");
        lista.stream()
                .sorted(apellidoLuegoNombre)
                .forEach(System.out::println);

        // ordena empleados en forma descendente por apellido, luego por nombre
        System.out.printf(
                "%nEmpleados en orden descendente por apellido y luego por nombre:%n");
        lista.stream()
                .sorted(apellidoLuegoNombre.reversed())
                .forEach(System.out::println);
        // muestra apellidos de empleados únicos ordenados
        System.out.printf("%nApellidos de empleados unicos:%n");
        lista.stream()
                .map(Empleado::getApellidoPaterno)
                .distinct()
                .sorted()
                .forEach(System.out::println);

        // muestra sólo nombre y apellido
        System.out.printf(
                "%nNombres de empleados en orden por apellido y luego por nombre:%n");
        lista.stream()
                .sorted(apellidoLuegoNombre)
                .map(Empleado::getPrimerNombre)
                .forEach(System.out::println);

        // agrupa empleados por departamento
        System.out.printf("%nEmpleados por departamento:%n");
        Map<String, List<Empleado>> agrupadoPorDepartamento =
                lista.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento));
        agrupadoPorDepartamento.forEach(
                (departamento, empleadosEnDepartamento) ->
                {
                    System.out.println(departamento);
                    empleadosEnDepartamento.forEach(
                            empleado -> System.out.printf(" %s%n", empleado));
                }
        );

        // cuenta el número de empleados en cada departamento
        System.out.printf("%nConteo de empleados por departamento:%n");
        Map<String, Long> conteoEmpleadosPorDepartamento =
                lista.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento,
                                TreeMap::new, Collectors.counting()));
        conteoEmpleadosPorDepartamento.forEach(
                (departamento, conteo) -> System.out.printf(
                        "%s tiene %d empleado(s)%n", departamento, conteo));

        // suma de salarios de empleados con el método sum de DoubleStream
        System.out.printf(
                "%nSuma de los salarios de los empleados (mediante el metodo sum): %.2f%n",
                lista.stream()
                        .mapToDouble(Empleado::getSalario)
                        .sum());

        // calcula la suma de los salarios de los empleados con el método reducede Stream
        System.out.printf("Suma de los salarios de los empleados (mediante el metodo reduce): %.2f%n",
                lista.stream()
                        .mapToDouble(Empleado::getSalario)
                        .reduce(0, (valor1, valor2) -> valor1 + valor2));

        // promedio de salarios de empleados con el método average de DoubleStream
        System.out.printf("Promedio de salarios de los empleados: %.2f%n",
                lista.stream()
                        .mapToDouble(Empleado::getSalario)
                        .average()
                        .getAsDouble());
    } // fin de main
} // fin de la clase Procesar empleados

