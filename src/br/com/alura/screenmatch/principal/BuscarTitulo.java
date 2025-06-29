package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());


            System.out.println(response.body());

            //Estrututa de dados gson para tranformar os dados json em objeto

            String json = response.body();

            //FieldNamingPolicy define como os nomes dos campos java são mapeados para os nomes das propriedades JSON
            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .create();
            //Titulo meuTitulo = gson.fromJson(json, Titulo.class); //transformando a resposta json na classe titulo
            TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(meuTituloOmdb);

        //estrutura de tentativa e erro para identificar o erro e tratar

            Titulo meuTitulo = new Titulo(meuTituloOmdb);
            System.out.println("Titulo já convertido");
            System.out.println(meuTitulo);
        }catch (NumberFormatException e){
            System.out.println("Aconteceu um erro: ");
            System.out.println(e.getMessage());
        }catch (IllegalArgumentException e){
            System.out.println("Algum erro de argumento na busca, verifique o endereço.");
        }

        System.out.println("O programa finalizou corretamente!");

    }
}
