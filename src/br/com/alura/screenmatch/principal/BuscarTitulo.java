package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.modelos.Titulo;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class BuscarTitulo {

    public static void main(String[] args) throws IOException, InterruptedException {


        //Estrutura dinamica para busca de titulos

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite um filme para buscar: ");
        var busca = sc.nextLine().toLowerCase();


        String endereco = "https://www.omdbapi.com/?t=" + busca + "&apikey=e1a1dcbe";


        // Estrutura de requisição e resposta da api
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());

        //Estrututa de dados gson para tranformar os dados json em objeto

        String json = response.body();

        Gson gson = new Gson();
        Titulo meuTitulo = gson.fromJson(json, Titulo.class); //transformando a resposta json na classe titulo

        System.out.println(meuTitulo);


    }
}
