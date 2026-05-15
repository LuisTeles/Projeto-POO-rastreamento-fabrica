package com.fabrica.model;

import java.util.List;

public interface Rastreavel {
    void registrarMovimentacao(String mensagem);

    List<String> getHistoricoMovimentacoes();
}
