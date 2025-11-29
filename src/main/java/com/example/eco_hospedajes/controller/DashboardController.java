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

    @GetMapping("/{propietarioId}")
    public DashboardData obtenerDatosDashboard(@PathVariable Long propietarioId) {

        List<Hospedaje> misHospedajes = hospedajeRepository.findByPropietarioId(propietarioId);

        if (misHospedajes.isEmpty()) {
            return new DashboardData(List.of(), List.of());
        }

        List<Reserva> todasMisReservas = reservaRepository.findByHospedajeIn(misHospedajes);

        LocalDate seisMesesAtras = LocalDate.now().minusMonths(6);
        List<Reserva> reservasRecientes = todasMisReservas.stream()
                .filter(r -> r.getCheckin() != null && r.getCheckin().isAfter(seisMesesAtras))
                .collect(Collectors.toList());

        Map<Month, Long> reservasPorMes = reservasRecientes.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCheckin().getMonth(),
                        Collectors.counting()));

        Map<Month, Double> ingresosPorMes = reservasRecientes.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getCheckin().getMonth(),
                        Collectors.summingDouble(Reserva::getPrecioTotal)));

        List<GraficoData> dataReservas = reservasPorMes.entrySet().stream()
                .map(entry -> new GraficoData(entry.getKey().toString(), entry.getValue().doubleValue()))
                .collect(Collectors.toList());

        List<GraficoData> dataIngresos = ingresosPorMes.entrySet().stream()
                .map(entry -> new GraficoData(entry.getKey().toString(), entry.getValue()))
                .collect(Collectors.toList());

        return new DashboardData(dataReservas, dataIngresos);
    }


    class DashboardData {
        private List<GraficoData> reservasPorMes;
        private List<GraficoData> ingresosPorMes;


        public DashboardData(List<GraficoData> reservasPorMes, List<GraficoData> ingresosPorMes) {
            this.reservasPorMes = reservasPorMes;
            this.ingresosPorMes = ingresosPorMes;
        }

        public List<GraficoData> getReservasPorMes() {
            return reservasPorMes;
        }

        public List<GraficoData> getIngresosPorMes() {
            return ingresosPorMes;
        }
    }

    class GraficoData {
        private String label; 
        private Double valor; 

        public GraficoData(String label, Double valor) {
            this.label = label;
            this.valor = valor;
        }

        public String getLabel() {
            return label;
        }

        public Double getValor() {
            return valor;
        }
    }
}