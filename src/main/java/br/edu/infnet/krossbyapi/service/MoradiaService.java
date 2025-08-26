/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Moradia;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.repository.MoradiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoradiaService implements ServiceBase<Moradia, Long> {
    private final MoradiaRepository moradiaRepository;

    public MoradiaService(MoradiaRepository moradiaRepository) {
        this.moradiaRepository = moradiaRepository;
    }


    @Override
    public Moradia buscarPorId(Long idObjeto) throws BusinessException {
        return this.buscarMoradiaPorId(idObjeto);
    }

    @Override
    public List<Moradia> listarTodos() {
        return moradiaRepository.findAll();
    }

    @Override
    public Moradia incluir(Moradia entidade) throws BusinessException {
        this.validarMoradia(entidade);
        entidade.setId(null);
        entidade.setSituacao(EnumTipoSituacao.ATIVO);
        return moradiaRepository.save(entidade);
    }

    @Override
    public Moradia alterar(Long idObjeto, Moradia entidade) throws BusinessException {
        this.validarMoradia(entidade);
        Moradia moradia = this.buscarPorId(idObjeto);
        moradia.setSituacao(entidade.getSituacao());
        moradia.setTipoMoradia(entidade.getTipoMoradia());
        moradia.setNumeroUnidade(entidade.getNumeroUnidade());
        moradia.setPropietario(entidade.getPropietario());
        moradia.setMorador(entidade.getMorador());
        moradia.setQuadraTorreBloco(entidade.getQuadraTorreBloco());
        moradia.setLote(entidade.getLote());
        return moradiaRepository.save(moradia);
    }

    @Override
    public void excluir(Long idObjeto) throws BusinessException {
        this.buscarMoradiaPorId(idObjeto);
        throw new BusinessException("ATENÇÃO NEMHUMA MORADIA PODE SER APAGADA, SOMENTE É PERMITIDO INATIVAR");
    }
    private Moradia buscarMoradiaPorId(Long idObjeto) throws BusinessException {
        return moradiaRepository.findById(idObjeto).orElseThrow(()-> new BusinessException("Moradia não encontrada"));
    }
    public Moradia inativar(Long idMoradia) throws BusinessException {
        Moradia moradia = this.buscarMoradiaPorId(idMoradia);
        if (EnumTipoSituacao.INATIVO.equals(moradia.getSituacao())) {
            throw new BusinessException("Moradia já está inativa!");
        }
        moradia.setSituacao(EnumTipoSituacao.INATIVO);
        return this.moradiaRepository.save(moradia);
    }
    private void validarMoradia(Moradia moradia) throws BusinessException {
        if (moradia.getTipoMoradia() == null) {
            throw new BusinessException("tipo de moradia é obrigatório");
        }
        if (moradia.getNumeroUnidade() == null || moradia.getNumeroUnidade().isEmpty()) {
            throw new BusinessException("número da unidade é obrigatório");
        }
    }
}
