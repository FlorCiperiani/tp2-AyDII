package com.ciperiani.tp2.service;

import org.springframework.stereotype.Service;

import com.ciperiani.tp2.dto.ClienteRequestDTO;
import com.ciperiani.tp2.exception.EmailDuplicadoException;
import com.ciperiani.tp2.model.Cliente;
import com.ciperiani.tp2.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente registrarCliente(ClienteRequestDTO dto, boolean validarEmailDuplicado) {
        if (validarEmailDuplicado) {
            if (clienteRepository.existsByEmail(dto.getEmail())) {
                throw new EmailDuplicadoException("El email ya está registrado");
            }
        }

        Cliente cliente = new Cliente(
                dto.getNombre(),
                dto.getApellido(),
                dto.getEmail(),
                dto.getTelefono()
        );

        return clienteRepository.save(cliente);
    }
}