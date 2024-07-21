package br.com.formacaoVictor.principal;

import br.com.formacaoVictor.modelos.Titulo;
import br.com.formacaoVictor.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import br.com.formacaoVictor.execacao.ErroDeConversaoDeAno;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalComBusca {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        String busca = "";
        List<Titulo> lista = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (!busca.equalsIgnoreCase("sair")) {

            System.out.println("Digite seu filme: ");
            busca = leitura.nextLine();

            if (busca.equalsIgnoreCase("sair")) {
                break;
            }
            String endereco = "https://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=9d1fced8";
            System.out.println(endereco);
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();
                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());
                String json = response.body();
                System.out.println(json);


                TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println(meuTituloOmdb);

                Titulo meuTitulo = new Titulo(meuTituloOmdb);
                System.out.println(meuTitulo);

                lista.add(meuTitulo);
            } catch (NumberFormatException e) {
                System.out.println("Ocorreu um erro");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Verifique o endereco");
            } catch (ErroDeConversaoDeAno e) {
                System.out.println(e.getMensagem());
            }
        }
        FileWriter escrita = new FileWriter("Filme.json");
        escrita.write(gson.toJson(lista));
        escrita.close();


        System.out.println(lista);
        System.out.println("Finalizou corretamente");

    }
}