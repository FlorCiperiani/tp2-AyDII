package com.ciperiani.tp2.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import com.ciperiani.tp2.dto.ConversionResponseDTO;
import com.ciperiani.tp2.dto.FrankfurterResponseDTO;
import com.ciperiani.tp2.dto.HistorialResponseDTO;
import com.ciperiani.tp2.exception.BadGatewayException;
import com.ciperiani.tp2.exception.BadRequestException;
import com.ciperiani.tp2.model.HistorialConversion;
import com.ciperiani.tp2.repository.HistorialConversionRepository;

@Service
public class DivisaService {

    private final RestClient restClient;
    private final HistorialConversionRepository historialConversionRepository;

    public DivisaService(RestClient frankfurterRestClient,
                          HistorialConversionRepository historialConversionRepository) {
        this.restClient = frankfurterRestClient;
        this.historialConversionRepository = historialConversionRepository;
    }

    /**
     * Ejercicio 3: convierte un monto entre dos monedas sin persistir nada.
     */
    public ConversionResponseDTO convertir(double monto, String origen, String destino) {
        String origenNormalizado = origen.toUpperCase();
        String destinoNormalizado = destino.toUpperCase();

        FrankfurterResponseDTO respuesta = obtenerCotizacion(monto, origenNormalizado, destinoNormalizado);

        BigDecimal montoOriginal = BigDecimal.valueOf(monto);
        BigDecimal tasaCambio = BigDecimal.valueOf(respuesta.getRate());
        BigDecimal montoConvertido = montoOriginal.multiply(tasaCambio);

        return new ConversionResponseDTO(
                montoOriginal,
                origenNormalizado,
                destinoNormalizado,
                tasaCambio,
                montoConvertido,
                respuesta.getDate()
        );
    }

    /**
     * Ejercicio 6: hace la misma consulta que convertir(), pero además
     * guarda la consulta en historial_conversiones.
     */
    public ConversionResponseDTO convertirDivisa(double monto, String origen, String destino) {
        ConversionResponseDTO resultado = convertir(monto, origen, destino);

        HistorialConversion historial = new HistorialConversion();
        historial.setMonedaOrigen(resultado.getMonedaOrigen());
        historial.setMonedaDestino(resultado.getMonedaDestino());
        historial.setMonto(resultado.getMontoOriginal());
        historial.setMontoConvertido(resultado.getMontoConvertido());
        historial.setTasa(resultado.getTasaCambio());
        historial.setFechaConsulta(LocalDateTime.now());

        historialConversionRepository.save(historial);

        return resultado;
    }

    /**
     * Ejercicio 6: recupera el historial de consultas para un par de monedas,
     * de la más reciente a la más antigua.
     */
    public List<HistorialResponseDTO> obtenerHistorial(String origen, String destino) {
        String origenNormalizado = origen.toUpperCase();
        String destinoNormalizado = destino.toUpperCase();

        List<HistorialConversion> historial = historialConversionRepository
                .findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(origenNormalizado, destinoNormalizado);

        return historial.stream()
                .map(h -> new HistorialResponseDTO(h.getFechaConsulta(), h.getTasa()))
                .collect(Collectors.toList());
    }

    private FrankfurterResponseDTO obtenerCotizacion(double monto, String origen, String destino) {
        try {
            return restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/latest")
                            .queryParam("amount", monto)
                            .queryParam("from", origen)
                            .queryParam("to", destino)
                            .build())
                    .retrieve()
                    .body(FrankfurterResponseDTO.class);
        } catch (HttpClientErrorException e) {
            throw new BadRequestException("La moneda de origen o destino no existe o no es válida");
        } catch (RestClientException e) {
            throw new BadGatewayException(
                    "No se pudo obtener la cotización desde el servicio externo de divisas");
        }
    }
}