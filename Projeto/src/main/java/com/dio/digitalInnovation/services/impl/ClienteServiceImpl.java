package com.dio.digitalInnovation.services.impl;

import com.dio.digitalInnovation.model.Cliente;
import com.dio.digitalInnovation.model.ClienteRepository;
import com.dio.digitalInnovation.model.Endereco;
import com.dio.digitalInnovation.model.EnderecoRepository;
import com.dio.digitalInnovation.services.ClienteService;
import com.dio.digitalInnovation.services.ViaCepService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ViaCepService viaCepService;

    public ClienteServiceImpl(ClienteRepository clienteRepository,
                              EnderecoRepository enderecoRepository,
                              ViaCepService viaCepService) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.viaCepService = viaCepService;
    }

    @Override
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Page<Cliente> listarPaginado(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
    }

    @Override
    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    public List<Cliente> buscarPorCidade(String cidade) {
        return clienteRepository.findByEndereco_LocalidadeIgnoreCase(cidade);
    }

    @Override
    public List<Cliente> buscarPorEstado(String estado) {
        return clienteRepository.findByEndereco_UfIgnoreCase(estado);
    }

    @Override
    public List<Cliente> buscarPorIdadeEntre(Integer min, Integer max) {
        return clienteRepository.findByIdadeBetween(min, max);
    }

    @Override
    public Cliente criar(Cliente cliente) {
        return salvarClienteComCep(cliente);
    }

    @Override
    public Cliente atualizar(Long id, Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado para atualização com ID: " + id);
        }
        cliente.setId(id);
        return salvarClienteComCep(cliente);
    }

    @Override
    public void remover(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado para remoção com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }

    private Cliente salvarClienteComCep(Cliente cliente) {
        String cepRaw = cliente.getEndereco().getCep();
        String cep = formataCep(cepRaw);

        Endereco endereco = enderecoRepository.findById(cep)
                .orElseGet(() -> {
                    Endereco novo = viaCepService.consultarCep(cep);
                    if (novo == null || novo.getCep() == null) {
                        throw new IllegalArgumentException("CEP inválido: " + cepRaw);
                    }
                    return enderecoRepository.save(novo);
                });

        cliente.setEndereco(endereco);
        return clienteRepository.save(cliente);
    }

    private String formataCep(String cep) {
        if (cep == null) {
            throw new IllegalArgumentException("CEP não pode ser nulo");
        }
        String digits = cep.replaceAll("\\D", "");
        if (digits.length() != 8) {
            throw new IllegalArgumentException("CEP inválido: " + cep);
        }
        return digits.substring(0, 5) + "-" + digits.substring(5);
    }
}
