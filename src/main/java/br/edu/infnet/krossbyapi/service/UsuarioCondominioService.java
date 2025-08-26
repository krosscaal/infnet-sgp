/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.domain.entity.Usuario;
import br.edu.infnet.krossbyapi.domain.entity.UsuarioCondominio;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoResidente;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.exception.UsuarioException;
import br.edu.infnet.krossbyapi.repository.UsuarioCondominioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UsuarioCondominioService implements ServiceBase<UsuarioCondominio, Long> {
    private final UsuarioCondominioRepository repository;
    private final UsuarioService usuarioService;

    public UsuarioCondominioService(UsuarioCondominioRepository repository, UsuarioService usuarioService) {
        this.repository = repository;
        this.usuarioService = usuarioService;
    }

    @Override
    public UsuarioCondominio buscarPorId(Long idObjeto) throws BusinessException {
        try {
            return this.buscarUsuarioCondominioPorId(idObjeto);

        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public List<UsuarioCondominio> listarTodos() {
        return repository.findAll();
    }

    @Override
    public UsuarioCondominio incluir(UsuarioCondominio entidade) throws BusinessException {
        try {
            Usuario usuarioObj = usuarioService.buscarPorId(entidade.getUsuario().getId());
            entidade.setId(null);
            entidade.setSituacao(EnumTipoSituacao.ATIVO);
            entidade.setUsuario(usuarioObj);
            return repository.save(entidade);

        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public UsuarioCondominio alterar(Long idObjeto, UsuarioCondominio entidade) throws BusinessException {
        try {
            UsuarioCondominio usuarioCondominio = this.buscarUsuarioCondominioPorId(idObjeto);
            Usuario usuario = usuarioService.buscarPorId(entidade.getUsuario().getId());
            usuarioCondominio.setUsuario(usuario);
            usuarioCondominio.setEmail(entidade.getEmail());
            usuarioCondominio.setTipoResidente(entidade.getTipoResidente());
            usuarioCondominio.setSituacao(entidade.getSituacao());
            usuarioCondominio.setMoradias(entidade.getMoradias());
            return repository.save(usuarioCondominio);

        } catch (UsuarioException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public void excluir(Long idObjeto) throws NoSuchElementException {
        this.buscarUsuarioCondominioPorId(idObjeto);
        this.repository.deleteById(idObjeto);
    }

    private UsuarioCondominio buscarUsuarioCondominioPorId(Long idUsuario) throws NoSuchElementException {
        return repository.findById(idUsuario).orElseThrow(()-> new NoSuchElementException("Usuário Condominio não encontrado"));
    }
    public UsuarioCondominio inativar(Long idUsuario) throws BusinessException {

        UsuarioCondominio usuarioCondominioObj = this.buscarUsuarioCondominioPorId(idUsuario);
        if (EnumTipoSituacao.INATIVO.equals(usuarioCondominioObj.getSituacao())) {
            throw new BusinessException("Usuário Condominio Já está inativo.");
        }
        usuarioCondominioObj.setSituacao(EnumTipoSituacao.INATIVO);
        return repository.save(usuarioCondominioObj);
    }

    public List<Moradia> buscarMoradiasDoProprietario(Long idUsuario) throws NoSuchElementException {
        UsuarioCondominio usuarioCondominioObj = this.buscarUsuarioCondominioPorId(idUsuario);
        if (!usuarioCondominioObj.getTipoResidente().equals(EnumTipoResidente.PROPIETARIO)) {
            throw new BusinessException("Usuário informado não é propietario");
        }
        return repository.findMoradiasPorId(idUsuario);
    }
}
