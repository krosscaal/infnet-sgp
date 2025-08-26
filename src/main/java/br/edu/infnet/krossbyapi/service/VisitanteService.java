/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioCondominio;
import br.edu.infnet.krossbyapi.domain.entity.Visitante;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoAcesso;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.exception.UsuarioException;
import br.edu.infnet.krossbyapi.repository.VisitanteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VisitanteService implements ServiceBase<Visitante, Long> {

    private final VisitanteRepository visitanteRepository;
    private final UsuarioService usuarioService;
    private final MoradiaService moradiaService;
    private final UsuarioCondominioService usuarioCondominioService;

    public VisitanteService(VisitanteRepository visitanteRepository, UsuarioService usuarioService, MoradiaService moradiaService, UsuarioCondominioService usuarioCondominioService) {
        this.visitanteRepository = visitanteRepository;
        this.usuarioService = usuarioService;
        this.moradiaService = moradiaService;
        this.usuarioCondominioService = usuarioCondominioService;
    }

    @Override
    public Visitante buscarPorId(Long idObjeto) throws BusinessException {
        try {
            return this.buscarPorIdVisitante(idObjeto);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<Visitante> listarTodos() {
        return visitanteRepository.findAll();
    }

    @Override
    public Visitante incluir(Visitante entidade) {
        Usuario usuarioObj = usuarioService.buscarPorId(entidade.getUsuarioVisitante().getId());
        Moradia moradiaDestinoObj = moradiaService.buscarPorId(entidade.getMoradiaDestinoVisitante().getId());entidade.setId(null);
        UsuarioCondominio autorizadoObj = usuarioCondominioService.buscarPorId(entidade.getUsuarioAutorizacao().getId());
        entidade.setUsuarioVisitante(usuarioObj);
        entidade.setMoradiaDestinoVisitante(moradiaDestinoObj);
        entidade.setUsuarioAutorizacao(autorizadoObj);
        return visitanteRepository.save(entidade);
    }

    @Override
    public Visitante alterar(Long idObjeto, Visitante entidade) throws BusinessException {
        try {
            Usuario usuarioObj = usuarioService.buscarPorId(entidade.getUsuarioVisitante().getId());
            Moradia moradiaDestinoObj = moradiaService.buscarPorId(entidade.getMoradiaDestinoVisitante().getId());
            UsuarioCondominio autorizadoObj = usuarioCondominioService.buscarPorId(entidade.getUsuarioAutorizacao().getId());
            Visitante visitanteObj = this.buscarPorIdVisitante(idObjeto);

            visitanteObj.setUsuarioVisitante(usuarioObj);
            visitanteObj.setCartaoAcesso(entidade.getCartaoAcesso());
            visitanteObj.setTipoAcesso(entidade.getTipoAcesso());
            visitanteObj.setUsuarioAutorizacao(autorizadoObj);
            visitanteObj.setMoradiaDestinoVisitante(moradiaDestinoObj);
            visitanteObj.setObservacao(entidade.getObservacao());
            return visitanteRepository.save(visitanteObj);
        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void excluir(Long idObjeto) throws NoSuchElementException {
        this.buscarPorIdVisitante(idObjeto);
        this.visitanteRepository.deleteById(idObjeto);
    }

    private Visitante buscarPorIdVisitante(Long idVisitante) throws NoSuchElementException {
        return visitanteRepository.findById(idVisitante).orElseThrow(()-> new NoSuchElementException("Registro de Visitante não encontrado!"));
    }

    public List<Visitante> buscarVisitantesPorDataIngresso(String data) {

        String dataInicio = data + " 00:00:00";
        String dataFim = data + " 23:59:59";
        DateTimeFormatter formatar = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        LocalDateTime dataInicioBusca = LocalDateTime.parse(dataInicio, formatar);
        LocalDateTime dataFimBusca = LocalDateTime.parse(dataFim, formatar);
        return visitanteRepository.findAllByIngressoBetween(dataInicioBusca, dataFimBusca);
    }

    public List<Visitante> buscarPorCpf(String cpf) {
        return visitanteRepository.findByCpf(cpf, EnumTipoAcesso.VISITANTE);
    }
}
