package com.example.eco_hospedajes.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eco_hospedajes.model.Hospedaje;
import com.example.eco_hospedajes.model.Reserva;
import com.example.eco_hospedajes.repository.HospedajeRepository;
import com.example.eco_hospedajes.repository.ReservaRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private HospedajeRepository hospedajeRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    /**
     * Este endpoint calcula y devuelve todos los datos resumidos
     * para el dashboard de un propietario específico.
     * * Se accede con: GET /api/dashboard/{propietarioId}
     */
    @GetMapping("/{propietarioId}")
    public DashboardData obtenerDatosDashboard(@PathVariable Long propietarioId) {

        // 1. Encontrar todos los hospedajes de este propietario
        List<Hospedaje> misHospedajes = hospedajeRepository.findByPropietarioId(propietarioId);

        if (misHospedajes.isEmpty()) {
            // Si el dueño no tiene hospedajes, devolvemos datos vacíos
            return new DashboardData(List.of(), List.of());
        }

        // 2. Encontrar TODAS las reservas de TODOS sus hospedajes
        List<Reserva> todasMisReservas = reservaRepository.findByHospedajeIn(misHospedajes);

        // 3. Filtrar solo las reservas de los últimos 6 meses (como en la imagen)
        LocalDate seisMesesAtras = LocalDate.now().minusMonths(6);
        List<Reserva> reservasRecientes = todasMisReservas.stream()
                .filter(r -> r.getCheckin() != null && r.getCheckin().isAfter(seisMesesAtras))
                .collect(Collectors.toList());

        // 4. Calcular "Reservas recibidas" por mes
        // Esto agrupa las reservas por mes y las cuenta
        Map<Month, Long> reservasPorMes = reservasRecientes.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCheckin().getMonth(),
                        Collectors.counting()));

        // 5. Calcular "Ingresos recientes" por mes
        // Esto agrupa las reservas por mes y suma su 'precioTotal'
        Map<Month, Double> ingresosPorMes = reservasRecientes.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCheckin().getMonth(),
                        Collectors.summingDouble(Reserva::getPrecioTotal)));

        // 6. Convertir los mapas a un formato simple (DTO) para el frontend
        List<GraficoData> dataReservas = reservasPorMes.entrySet().stream()
                .map(entry -> new GraficoData(entry.getKey().toString(), entry.getValue().doubleValue()))
                .collect(Collectors.toList());

        List<GraficoData> dataIngresos = ingresosPorMes.entrySet().stream()
                .map(entry -> new GraficoData(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());

        // 7. Devolver el objeto DTO final
        return new DashboardData(dataReservas, dataIngresos);
    }

    // --- CLASES DTO (Data Transfer Object) ---
    // Clases internas simples para formatear la respuesta JSON para el frontend

    /**
     * DTO para el objeto de respuesta principal del dashboard.
     */
    class DashboardData {
        private List<GraficoData> reservasPorMes;
        private List<GraficoData> ingresosPorMes;

        // (Omitimos 'ocupacion' por simplicidad para la demo)

        public DashboardData(List<GraficoData> reservasPorMes, List<GraficoData> ingresosPorMes) {
            this.reservasPorMes = reservasPorMes;
            this.ingresosPorMes = ingresosPorMes;
        }

        // Getters
        public List<GraficoData> getReservasPorMes() {
            return reservasPorMes;
        }

        public List<GraficoData> getIngresosPorMes() {
            return ingresosPorMes;
        }
    }

    /**
     * DTO genérico para las gráficas.
     * Chart.js usará 'label' para el eje X y 'valor' para el eje Y.
     */
    class GraficoData {
        private String label; // El mes (ej. "AGOSTO")
        private Double valor; // El total (ej. 6.0 reservas o 3000.0 soles)

        public GraficoData(String label, Double valor) {
            this.label = label;
            this.valor = valor;
        }

        // Getters
        public String getLabel() {
            return label;
        }

        public Double getValor() {
            return valor;
        }
    }
}