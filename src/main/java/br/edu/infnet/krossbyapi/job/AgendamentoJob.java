package br.edu.infnet.krossbyapi.job;

import br.edu.infnet.krossbyapi.domain.clients.AgendaFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class AgendamentoJob {
    private static final Logger log = LoggerFactory.getLogger(AgendamentoJob.class);

    private final AgendaFeignClient agendaFeignClient;

    public AgendamentoJob(AgendaFeignClient agendaFeignClient) {
        this.agendaFeignClient = agendaFeignClient;
    }
    /* executa a cada 10 segundos */
    @Scheduled(fixedRate = 10000L)
    public void executarConsultaPorIntervalo() {
        String data = "29/09/2025";
        var agendamentos = agendaFeignClient.getAgendamento(data);
        log.info("lista de agendamentos (intervalo 10 segundos) carregada com sucesso fonte -> Sistema de Agendamento (data={})", data);
        agendamentos.forEach(agenda -> log.info(agenda.toString()));
    }

}
