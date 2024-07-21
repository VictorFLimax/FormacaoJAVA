package br.com.formacaoVictor.execacao;

public class ErroDeConversaoDeAno extends RuntimeException {
    private String mensagem;

    public ErroDeConversaoDeAno(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
