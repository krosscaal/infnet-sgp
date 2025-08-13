/*
 * Author: Krossby Adhemar Camacho Alviz
 * owned by Krossft.
 */

package br.edu.infnet.krossbyapi.service;

import br.edu.infnet.krossbyapi.domain.entity.Condominio;
import br.edu.infnet.krossbyapi.domain.entity.CondominioFactory;
import br.edu.infnet.krossbyapi.domain.enumerator.EnumTipoSituacao;
import br.edu.infnet.krossbyapi.domain.record.CondominioRecord;
import br.edu.infnet.krossbyapi.exception.BusinessException;
import br.edu.infnet.krossbyapi.repository.CondominioRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CondominioService implements ServiceBase<CondominioRecord, Long>{
    private final CondominioRepository repository;
    private final Map<Long, Condominio> condominioMap = new ConcurrentHashMap<>();
    private final AtomicLong condominioId = new AtomicLong(10);

    public CondominioService(CondominioRepository repository) {
        this.repository = repository;
    }

    @Override
    public CondominioRecord buscarPorId(Long idObjeto) throws BusinessException {
        Condominio condominio = this.buscarCondominioPorId(idObjeto);
        return entityToRecord(condominio);
    }

    @Override
    public List<CondominioRecord> listarTodos() {
        List<CondominioRecord> listaCondominio = new ArrayList<>();

        List<Condominio> lista = repository.findAll();

        lista.forEach(condominio -> listaCondominio.add(this.entityToRecord(condominio)));

        return listaCondominio;
    }

    @Override
    public CondominioRecord incluir(CondominioRecord entidade) throws BusinessException {
        Condominio condominio = CondominioFactory.criarCondominio(entidade);

        Condominio condominioObjetoPersisitido = repository.save(condominio);

        return this.entityToRecord(condominioObjetoPersisitido);
    }

    @Override
    public CondominioRecord alterar(Long idObjeto, CondominioRecord entidade) throws BusinessException {
        Condominio condominioObj = this.buscarCondominioPorId(idObjeto);

        Condominio novoCondominio = CondominioFactory.criarCondominio(entidade);

        condominioObj.setNomeCondominio(novoCondominio.getNomeCondominio());
        condominioObj.setTipoCondominio(novoCondominio.getTipoCondominio());
        condominioObj.setCnpj(novoCondominio.getCnpj());
        condominioObj.setTelefoneContato1(novoCondominio.getTelefoneContato1());
        condominioObj.setTelefoneContato2(novoCondominio.getTelefoneContato2());
        condominioObj.setEndereco(novoCondominio.getEndereco());
        condominioObj.setNomeSindico(novoCondominio.getNomeSindico());
        condominioObj.setTelefoneSindico(novoCondominio.getTelefoneSindico());

        return entityToRecord(repository.save(condominioObj));
    }

    @Override
    public void excluir(Long idObjeto) throws BusinessException {
        Condominio condominio = this.buscarCondominioPorId(idObjeto);

        if (condominio != null) {
            throw new BusinessException("Condominio não pode ser Apagado somente é permitido fazer alterações");
        }
    }

    public CondominioRecord inativar(Long idCondominio) {
        Condominio condominioObj = this.buscarCondominioPorId(idCondominio);
        if (EnumTipoSituacao.INATIVO.equals(condominioObj.getSituacao())) {
            throw new BusinessException("Condominio já está inativo!");
        }
        condominioObj.setSituacao(EnumTipoSituacao.INATIVO);
        return entityToRecord(repository.save(condominioObj));
    }

    private Condominio buscarCondominioPorId(Long idCondominio) {
        return repository.findById(idCondominio).orElseThrow(() -> new BusinessException("Condominio não encontrado"));
    }

    private CondominioRecord entityToRecord(Condominio obj) {
        return new CondominioRecord(
                obj.getId(),
                obj.getNomeCondominio(),
                obj.getTipoCondominio(),
                obj.getTotalUnidades(),
                obj.getCnpj(),
                obj.getTelefoneContato1(),
                obj.getTelefoneContato2(),
                obj.getEndereco(),
                obj.getNomeSindico(),
                obj.getTelefoneSindico(),
                obj.getSituacao());
    }

    private void existeEmMap(long idCondominio) {
        if (!this.condominioMap.containsKey(idCondominio)) {
            throw new BusinessException("Condominio não encontrado!");
        }
    }

    public Condominio incluirMap(CondominioRecord condominioRecord) {
        Condominio condominio = CondominioFactory.criarCondominio(condominioRecord);

        condominio.setId(condominioId.getAndIncrement());

        condominioMap.put(condominio.getId(), condominio);

        return condominio;
    }

    public List<Condominio> listarTodosMap() {
        return new ArrayList<>(condominioMap.values());
    }

    public Condominio buscarPorIdMap(Long idObjeto) {
        this.existeEmMap(idObjeto);
        return this.condominioMap.get(idObjeto);
    }
    public Condominio alterarMap(Long idCondominio, CondominioRecord entidade) throws BusinessException {
        this.existeEmMap(idCondominio);
        Condominio condominioObj = this.condominioMap.get(idCondominio);
        condominioObj.setNomeCondominio(entidade.nomeCondominio());
        condominioObj.setTipoCondominio(entidade.tipoCondominio());
        condominioObj.setCnpj(entidade.cnpj());
        condominioObj.setTelefoneContato1(entidade.telefoneContato1());
        condominioObj.setTelefoneContato2(entidade.telefoneContato2());
        condominioObj.setEndereco(entidade.endereco());
        condominioObj.setNomeSindico(entidade.nomeSindico());
        condominioObj.setTelefoneSindico(entidade.telefoneSindico());
        this.condominioMap.put(idCondominio, condominioObj);
        return this.condominioMap.get(idCondominio);
    }

    public void excluirMap(Long idCondominio) {
        this.existeEmMap(idCondominio);
        this.condominioMap.remove(idCondominio);
    }
    public Condominio inativarMap(Long idCondominio) throws BusinessException {
        this.existeEmMap(idCondominio);
        Condominio condominioObj = this.condominioMap.get(idCondominio);
        if (EnumTipoSituacao.INATIVO.equals(condominioObj.getSituacao())) {
            throw new BusinessException("Condominio já está inativo!");
        }
        condominioObj.setSituacao(EnumTipoSituacao.INATIVO);
        this.condominioMap.put(idCondominio, condominioObj);
        return condominioObj;
    }

}
